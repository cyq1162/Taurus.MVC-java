package taurus.mvc;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import taurus.microservice.config.MsConfig;
import taurus.mvc.config.MvcConfig;
import taurus.mvc.entity.AnnotationInfo;
import taurus.mvc.entity.MethodInfo;
import taurus.mvc.entity.RequireInfo;
import taurus.mvc.http.HttpInputStream;
import taurus.mvc.http.HttpPart;
import taurus.mvc.http.HttpRequest;
import taurus.mvc.http.HttpResponse;
import taurus.mvc.reflect.ControllerCollector;
import taurus.mvc.reflect.MethodCollector;
import taurus.mvc.tool.ConvertTool;
import taurus.mvc.tool.Debug;
import taurus.mvc.tool.string;

/**
 * 核心控制器基类：所有的控制器都应该继承自此基类 允许子类被二次或三次继承。
 * 
 * @author 路过秋天 ：教程博客：https://www.cnblogs.com/cyq1162
 * 					  开源地址：https://github.com/cyq1162/Taurus.MVC-java
 *
 */
public abstract class Controller {

	private Map<String, String> keyValueForPost;
	private StringBuilder apiResult = new StringBuilder();
	HttpRequest request;
	HttpResponse response;

	public void ProcessRequest(HttpRequest request, HttpResponse response) throws IOException {
		this.request = request;
		this.response = response;
		try {
			initNameFromUrl();
			MethodInfo methodInfo = MethodCollector.getMethod(_ControllerType, _MethodName);
			if (methodInfo == null) {

				// 检测全局Default方法
				Class<?> globalDefault = ControllerCollector.getController("default");
				if (globalDefault != null) {
					Controller o = (Controller) globalDefault.newInstance();// 实例化
					o.ProcessRequest(request, response);
				} else {
					response.setStatus(404);
					write("404 : Invalid method for url.", false);
					writeExeResult();
				}
				return;
			}
			if (checkMethodAnnotatedLimit(methodInfo)) {
				if (exeBeforeInvoke(methodInfo.getAnnotationInfo().getHasIgnoreDefaultController())) {
					if (exeMethodInvoke(methodInfo)) {
						exeEndInvoke(methodInfo.getAnnotationInfo().getHasIgnoreDefaultController());
					}
				}
			}
			writeExeResult();
		} catch (Exception err) {
			Debug.log(err,"Controller.ProcessRequest");
			if(err instanceof InvocationTargetException)
			{
				 err=(Exception)err.getCause();
			}
			try {
				request.getContext().log(err.getMessage());
				MethodInfo methodInfo = MethodCollector.getGlobalOnError();
				if (methodInfo != null) {
					methodInfo.getMethod().invoke(null, this, err);
					writeExeResult();
				} else {
					response.getWriter().write(err.getMessage());
				}

			} catch (Exception e) {
				Debug.log(e,"MethodCollector.getGlobalOnError");
			}
		}

	}

	private void initNameFromUrl() {
		_ControllerType = this.getClass();
		String[] names = _ControllerType.getName().split("\\.");
		_ControllerName = names[names.length - 1].replace("Controller", "");
		String localPath = request.getRequestURI();
		if (localPath.startsWith("/")) {
			localPath = localPath.substring(1);
		}
		String[] items = localPath.split("/");
		int paraStartIndex = MvcConfig.getRouteMode() + 1;
		String methodName = "";
		switch (MvcConfig.getRouteMode()) {
		case 0:
			methodName = items[0];
			break;
		case 1:
			if (items.length > 1) {
				if (items.length > 2 && items[0].toLowerCase() != _ControllerName
						&& items[1].toLowerCase() == _ControllerName
						&& items[0] == MsConfig.getClientName()) {
					paraStartIndex++;
					methodName = items[2];// 往后兼容一格。
				} else {
					methodName = items[1];
				}
			}
			break;
		case 2:
			_ModuleName = items[0];
			if (items.length > 2) {
				methodName = items[2];
			} else if (items.length > 1) {
				// 兼容【路由1=》（变更为）2】
				methodName = items[1];
			}
			break;
		}
		_MethodName = methodName;

		if (items.length > paraStartIndex) {
			_ParaItems = new String[items.length - paraStartIndex];
			for (int i = 0; i < _ParaItems.length; i++) {
				_ParaItems[i] = items[paraStartIndex + i];
			}
		} else {
			_ParaItems = new String[1];
			_ParaItems[0] = "";
		}

	}

	private boolean checkMethodAnnotatedLimit(MethodInfo methodInfo) throws Exception {
		Boolean isGoOn = true;
		AnnotationInfo attrFlags = methodInfo.getAnnotationInfo();
		if (!attrFlags.HasKey(request.getMethod()))// 配置了HttpGet或HttpPost
		{
			write("Http method not support " + request.getMethod(), false);
			return false;
		}
		if (attrFlags.getHasAck())// 有[Ack]
		{
			MethodInfo checkAck = MethodCollector.getMethod(_ControllerType, "checkAck", false);
			if (checkAck != null) {
				isGoOn = (Boolean) checkAck.getMethod().invoke(this);
			} else if(!attrFlags.getHasIgnoreDefaultController()) {
				checkAck = MethodCollector.getGlobalCheckAck();
				if (checkAck != null) {
					isGoOn = (Boolean) checkAck.getMethod().invoke(null, new Object[] { this });
				}
			}
			if (!isGoOn) {
				write("checkAck() : result is illegal.", false);
				return false;
			}
		}
		if (attrFlags.getHasToken())// 有[Token]
		{
			MethodInfo checkToken = MethodCollector.getMethod(_ControllerType, "checkToken", false);
			if (checkToken != null) {
				isGoOn = (Boolean) (checkToken.getMethod().invoke(this));
			} else  if(!attrFlags.getHasIgnoreDefaultController()){
				checkToken = MethodCollector.getGlobalCheckToken();
				if (checkToken != null) {
					isGoOn = (Boolean) checkToken.getMethod().invoke(null, new Object[] { this });
				}
			}
			if (!isGoOn) {
				write("checkToken() : result is illegal.", false);
				return false;
			}
		}
		if (attrFlags.getHasMicroService())// 有[MicroService]
		{
			// 【如果开启全局，即需要调整授权机制，则原有局部机制失效。】
			MethodInfo microServiceInfo = null;
			if (!attrFlags.getHasIgnoreDefaultController()) {
				microServiceInfo = MethodCollector.getGlobalCheckMicroService();
				if (microServiceInfo != null) {
					isGoOn = (Boolean) microServiceInfo.getMethod().invoke(null, new Object[] { this });
				}
			}
			if(isGoOn && microServiceInfo==null) {
				microServiceInfo = MethodCollector.getMethod(_ControllerType, "checkMicroService", false);
				if (microServiceInfo != null) {
					isGoOn = (Boolean) microServiceInfo.getMethod().invoke(this);
				}
			}
			if (!isGoOn) {
				write("checkMicroService() : result is illegal.", false);
				return false;
			}
		}

		if (attrFlags.getHasRequire()) {
			RequireInfo[] requireInfos = methodInfo.getAnnotationInfo().getRequireInfos();
			if (requireInfos != null) {
				for (RequireInfo valid : requireInfos) {
					if (valid.paraName.indexOf(',') > -1) {
						for (String name : valid.paraName.split(",")) {
							if (string.IsNullOrEmpty(query(name))) {
								write(string.Format(valid.emptyTip, name), false);
								return false;
							}
						}
					} else if (!requireValidate(valid, query(valid.paraName))) {
						return false;
					}
				}
			}
		}
		return true;

	}

	private boolean exeBeforeInvoke(boolean isIgnoreGlobal) throws Exception {
		boolean isGoOn = true;
		MethodInfo methodInfo = null;
		if (!isIgnoreGlobal) {
			methodInfo = MethodCollector.getGlobalBeforeInvoke();
			if (methodInfo != null)// 先调用全局
			{
				isGoOn = (Boolean) methodInfo.getMethod().invoke(null, new Object[] { this });
			}
		}
		if (isGoOn) {
			methodInfo = MethodCollector.getMethod(_ControllerType, "beforeInvoke", false);
			if (methodInfo != null) {
				isGoOn = (Boolean) methodInfo.getMethod().invoke(this, new Object[] { _MethodName });
			}
		}
		return isGoOn;
	}

	private void exeEndInvoke(boolean isIgnoreGlobal) throws Exception {
		MethodInfo methodInfo = MethodCollector.getMethod(_ControllerType, "endInvoke", false);
		if (methodInfo != null) {
			methodInfo.getMethod().invoke(this, new Object[] { _MethodName });
		}
		if (!isIgnoreGlobal) {
			methodInfo = MethodCollector.getGlobalEndInvoke();
			if (methodInfo != null) {
				methodInfo.getMethod().invoke(null, new Object[] { this });
			}
		}
	}

	private boolean exeMethodInvoke(MethodInfo methodInfo) throws Exception {

		Boolean isGoOn = false;

		// if (!CancelLoadHtml)
		// {
		// _View = ViewEngine.Create(t.Name, method.Name);
		// if (_View != null)
		// {
		// //追加几个全局标签变量
		// _View.KeyValue.Add("module", Module.ToLower());
		// _View.KeyValue.Add("controller", _ControllerName);
		// _View.KeyValue.Add("action", Action.ToLower());
		// _View.KeyValue.Add("para", Para.ToLower());
		// _View.KeyValue.Add("httphost", Request.Url.AbsoluteUri.Substring(0,
		// Request.Url.AbsoluteUri.Length - Request.Url.PathAndQuery.Length));
		// }
		// }

		Object[] paras = getInvokeParas(methodInfo);
		if (paras != null) {
			methodInfo.getMethod().invoke(this, paras);
			isGoOn = true;
		}
		// if (IsHttpPost && _View != null)
		// {
		// string name = GetBtnName();
		// if (!string.IsNullOrEmpty(name))
		// {
		// MethodInfo postBtnMethod = MethodCollector.GetMethod(t, name);
		// if (postBtnMethod != null && postBtnMethod.Name != Const.Default)
		// {
		// GetInvokeParas(postBtnMethod, out paras);
		// postBtnMethod.Invoke(this, paras);
		// }
		// }
		//
		// }
		// }
		return isGoOn;
	}

	private Object[] getInvokeParas(MethodInfo methodInfo) throws Exception {

		Object[] callParas;
		Parameter[] parameters = methodInfo.getParameters();

		if (parameters != null && parameters.length > 0) {
			callParas = new Object[parameters.length];

			for (int i = 0; i < parameters.length; i++) {
				Parameter parameter = parameters[i];
				String name = parameter.getName();
				Class<?> type = parameter.getType();
				if (HttpPart.class.equals(type)) {
					callParas[i] = request.getPart(name);
				} else {
					callParas[i] = ConvertTool.changeType(query(name), type);
				}
			}

		} else {
			callParas = new Object[0];
		}
		return callParas;
	}

	private Boolean requireValidate(RequireInfo valid, String paraValue) throws Exception {

		if (valid.isRequire && string.IsNullOrEmpty(paraValue)) {
			write(valid.emptyTip, false);
			return false;
		} else if (!string.IsNullOrEmpty(valid.regex) && !string.IsNullOrEmpty(paraValue)) {
			if (paraValue.indexOf('%') > -1) {
				paraValue = URLDecoder.decode(paraValue, getEncoding());
			}
			if (!paraValue.matches(valid.regex))// 如果格式错误
			{
				write(valid.regexTip, false);
				return false;
			}
		}

		return true;
	}

	private String getEncoding() {
		String encode = response.getCharacterEncoding();
		if (encode == null || encode.length() == 0 || encode.equals("ISO-8859-1")) {
			encode = "utf-8";
			response.setCharacterEncoding(encode);
		}
		return encode;
	}

	private void writeExeResult() throws Exception {
		if (apiResult.length() > 0) {
			String encode = getEncoding();
			String outResult = apiResult.toString();
			String contentType = response.getContentType();
			if (contentType == null || contentType.length() == 0) {
				contentType = "text/html;charset=" + encode;
				response.setContentType(contentType);
			}
			if (contentType.startsWith("text/html")) {
				if (apiResult.charAt(0) == '{' && apiResult.charAt(apiResult.length() - 1) == '}') {
					response.setContentType("application/json;charset=" + encode);
				} else if (outResult.startsWith("<?xml") && apiResult.charAt(apiResult.length() - 1) == '>') {
					response.setContentType("application/xml;charset=" + encode);
				}
			}

			response.getWriter().write(outResult);
			outResult = null;
			apiResult = null;
		}
	}

	public String getApiResult() {
		return apiResult.toString();
	}

	public void setQuery(String name, String value) {
		// TODO Auto-generated method stub

	}

	public HttpRequest getRequest() {
		// TODO Auto-generated method stub
		return this.request;
	}

	public HttpResponse getResponse() {
		// TODO Auto-generated method stub
		return this.response;
	}

	public Boolean getIsHttpGet() {
		// TODO Auto-generated method stub
		return request.getMethod().equals("GET");
	}

	public Boolean getIsHttpPost() {
		// TODO Auto-generated method stub
		return request.getMethod().equals("POST");
	}

	public Boolean getIsHttpHead() {
		// TODO Auto-generated method stub
		return request.getMethod().equals("Header");
	}

	public Boolean getIsHttpPut() {
		// TODO Auto-generated method stub
		return request.getMethod().equals("PUT");
	}

	public Boolean getIsHttpDelete() {
		// TODO Auto-generated method stub
		return request.getMethod().equals("DELETE");
	}

	public Class<?> _ControllerType;

	public Class<?> getControllerType() {
		return _ControllerType;
	}

	private String _ModuleName = "";

	public String getModuleName() {
		return _ModuleName;
	}

	private String _ControllerName = "";

	public String getControllerName() {
		return _ControllerName;
	}

	private String _MethodName = "";

	public String getMethodName() {
		return _MethodName;
	}

	private String _ParaName = "";

	public String getParaName() {
		return _ParaName;
	}

	private String[] _ParaItems;

	public String[] getParaItems() {
		return _ParaItems;
	}

	public void write(String msg) {
		apiResult.append(msg);

	}
	public void write(byte[] buf) throws IOException {
		response.getOutputStream().write(buf);

	}
	public void write(char[] buf) throws IOException {
		response.getWriter().write(buf);

	}
	public void write(String msg, Boolean isSuccess) {
		String json = "{\"success\":" + (isSuccess ? "true" : "false") + ",\"msg\":\"" + msg + "\"}";
		apiResult.append(json);
	}

	public Boolean beforeInvoke(String methodName) {
		return true;
	}

	public void endInvoke(String methodName) {

	}
	// region override

	// 小写是关键字，这个就用大写了。
	public void Default() {

	}

	/// <summary>
	/// if the result is false will stop invoke method
	/// <para>检测身份是否通过</para>
	/// </summary>
	/// <returns></returns>
	public Boolean checkToken() {
		return true;
	}

	/// <summary>
	/// if the result is false will stop invoke method
	/// <para>检测请求是否合法</para>
	/// </summary>
	/// <returns></returns>
	public Boolean checkAck() {
		return true;
	}

	/// <summary>
	/// if the result is false will stop invoke method
	/// <para>检测微服务间的请求是否合法</para>
	/// </summary>
	/// <returns></returns>
	public Boolean checkMicroService() {
		return true;
		// return MicroService.Config.ServerKey ==
		// Context.Request.Headers[MicroService.Const.HeaderKey];
	}
	// endregion

	public String query(String key) {
		String value = request.getParameter(key);
		if (value == null) {
			if (getIsHttpPost()) {
				String contentType = request.getContentType();
				if (contentType == null || !contentType.startsWith("multipart/form-data"))// 非文件上传
				{
					if (keyValueForPost == null) {
						initFormValueOnPost();
					}
					if (keyValueForPost.containsKey(key.toLowerCase())) {
						value = keyValueForPost.get(key.toLowerCase());
					}
				}
				else
				{
					HttpPart part=request.getPart(key);
					if(part!=null)
					{
						value=part.getSubmittedFileName();
					}
				}
			}
			if (value == null) {
				value = request.getHeader(key);
			}
		}
		return value;
	}

	public <T> T query(String key, Class<T> object) {
		return ConvertTool.tryChangeType(query(key), object);
	}

	private void initFormValueOnPost() {
		keyValueForPost = new HashMap<String, String>();
		try {
			HttpInputStream stream = (HttpInputStream) request.getInputStream();
			if (stream != null) {
				int len = request.getContentLength();
				if (len > 0) {
					byte[] bytes = new byte[len];
					stream.read(bytes, 0, len);
					String postData = new String(bytes, getEncoding());
					if (postData == null || postData.length() == 0) {
						return;
					}
					String[] items = postData.split("&");
					for (String item : items) {
						if (item == null || item.length() == 0) {
							continue;
						}
						int index = item.indexOf('=');
						if (index > -1) {
							keyValueForPost.put(item.substring(0, index).toLowerCase(), item.substring(index + 1));
						}
					}
				}
			}
		} catch (Exception err) {
			Debug.log(err,"Controller.initFormValueOnPost");
		}

	}
}
