package taurus.core;

import java.io.IOException;
import java.lang.reflect.Parameter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import taurus.core.config.MicroServiceConfig;
import taurus.core.config.MvcConfig;
import taurus.core.entity.AnnotationInfo;
import taurus.core.entity.MethodInfo;
import taurus.core.entity.RequireInfo;
import taurus.core.http.HttpInputStream;
import taurus.core.http.HttpPart;
import taurus.core.http.HttpRequest;
import taurus.core.http.HttpResponse;
import taurus.core.reflect.ControllerCollector;
import taurus.core.reflect.MethodCollector;
import taurus.core.tool.ConvertTool;
import taurus.core.tool.string;

/**
 * ���Ŀ��������ࣺ���еĿ�������Ӧ�ü̳��Դ˻���
 * �������౻���λ����μ̳С�
 * @author ·������
 *
 */
public abstract class Controller {

	private Map<String, String> keyValueForPost;
	private StringBuilder apiResult = new StringBuilder();
	HttpRequest request;
	HttpResponse response;

	public	void ProcessRequest(HttpRequest request, HttpResponse response) throws IOException {
		this.request = request;
		this.response =response;
		try {
			initNameFromUrl();
			MethodInfo methodInfo = MethodCollector.getMethod(_ControllerType, _MethodName);
			if (methodInfo == null) {

				// ���ȫ��Default����
				Class<?> globalDefault = ControllerCollector.getController("default");
				if (globalDefault != null) {
					Controller o = (Controller) globalDefault.newInstance();// ʵ����
					o.ProcessRequest(request, response);
				}
				else
				{
					response.setStatus(404);
					write("404 : Invalid method for url.",false);
					writeExeResult();
				}
				return;
			}
			if (checkMethodAnnotatedLimit(methodInfo)) {
				if (exeBeforeInvoke()) {
					if (exeMethodInvoke(methodInfo)) {
						exeEndInvoke();
					}
				}
			}
			writeExeResult();
		} catch (Exception e) {

			// TODO: handle exception
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
						&& items[0] == MicroServiceConfig.getClientName()) {
					paraStartIndex++;
					methodName = items[2];// �������һ��
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
				// ���ݡ�·��1=�������Ϊ��2��
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
		if (!attrFlags.HasKey(request.getMethod()))// ������HttpGet��HttpPost
		{
			write("Http method not support " + request.getMethod(), false);
			return false;
		}
		if (attrFlags.getHasAck())// ��[Ack]
		{
			MethodInfo checkAck = MethodCollector.getMethod(_ControllerType, "checkAck", false);
			if (checkAck != null) {
				isGoOn = (Boolean) checkAck.getMethod().invoke(this);
			} else {
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
		if (attrFlags.getHasMicroService())// ��[MicroService]
		{
			// ���������ȫ�֣�����Ҫ������Ȩ���ƣ���ԭ�оֲ�����ʧЧ����
			MethodInfo microServiceInfo = MethodCollector.getGlobalCheckMicroService();
			if (microServiceInfo != null) {
				isGoOn = (Boolean) microServiceInfo.getMethod().invoke(null, new Object[] { this });
			} else {
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
		if (attrFlags.getHasToken())// ��[Token]
		{
			MethodInfo checkToken = MethodCollector.getMethod(_ControllerType, "checkToken", false);
			if (checkToken != null) {
				isGoOn = (Boolean) (checkToken.getMethod().invoke(this));
			} else {
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
		if(attrFlags.getHasRequire())
		{
			RequireInfo[] requireInfos=methodInfo.getAnnotationInfo().getRequireInfos();
			if(requireInfos!=null)
			{
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

	private boolean exeBeforeInvoke() throws Exception {
		boolean isGoOn = true;
		MethodInfo methodInfo = MethodCollector.getGlobalBeforeInvoke();
		if (methodInfo != null)// �ȵ���ȫ��
		{
			isGoOn = (Boolean) methodInfo.getMethod().invoke(null, new Object[] { this });
		}
		if (isGoOn) {
			methodInfo = MethodCollector.getMethod(_ControllerType, "beforeInvoke", false);
			if (methodInfo != null) {
				isGoOn = (Boolean) methodInfo.getMethod().invoke(this, new Object[] { _MethodName });
			}
		}
		return isGoOn;
	}

	private void exeEndInvoke() throws Exception {
		MethodInfo methodInfo = MethodCollector.getMethod(_ControllerType, "endInvoke", false);
		if (methodInfo != null) {
			methodInfo.getMethod().invoke(this, new Object[] { _MethodName });
		}
		methodInfo = MethodCollector.getGlobalEndInvoke();
		if (methodInfo != null) {
			methodInfo.getMethod().invoke(null, new Object[] { this });
		}
	}

	private boolean exeMethodInvoke(MethodInfo methodInfo) throws Exception  {

		Boolean isGoOn = false;

		// if (!CancelLoadHtml)
		// {
		// _View = ViewEngine.Create(t.Name, method.Name);
		// if (_View != null)
		// {
		// //׷�Ӽ���ȫ�ֱ�ǩ����
		// _View.KeyValue.Add("module", Module.ToLower());
		// _View.KeyValue.Add("controller", _ControllerName);
		// _View.KeyValue.Add("action", Action.ToLower());
		// _View.KeyValue.Add("para", Para.ToLower());
		// _View.KeyValue.Add("httphost", Request.Url.AbsoluteUri.Substring(0,
		// Request.Url.AbsoluteUri.Length - Request.Url.PathAndQuery.Length));
		// }
		// }

		 Object[] paras=getInvokeParas(methodInfo);
		 if (paras!=null)
		 {
			 methodInfo.getMethod().invoke(this, paras);
			 isGoOn=true;
		 }
//		 if (IsHttpPost && _View != null)
//		 {
//		 string name = GetBtnName();
//		 if (!string.IsNullOrEmpty(name))
//		 {
//		 MethodInfo postBtnMethod = MethodCollector.GetMethod(t, name);
//		 if (postBtnMethod != null && postBtnMethod.Name != Const.Default)
//		 {
//		 GetInvokeParas(postBtnMethod, out paras);
//		 postBtnMethod.Invoke(this, paras);
//		 }
//		 }
//		
//		 }
//		 }
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
				String value = query(name);
				Class<?> type = parameter.getType();
				if (HttpPart.class.equals(type)) {
					callParas[i] = request.getPart(name);
				} else {
					callParas[i] = ConvertTool.changeType(value, type);
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
			if (!paraValue.matches(valid.regex))// �����ʽ����
			{
				write(valid.regexTip, false);
				return false;
			}
		}

		return true;
    }
	
	
	
	
	private String getEncoding() {
		String encode=response.getCharacterEncoding();
		if (encode ==null || encode.length()==0 || encode.equals("ISO-8859-1")) {
			encode="utf-8";
			response.setCharacterEncoding(encode);
		}
		return encode;
	}
	private void writeExeResult() throws Exception {
		String encode=getEncoding();
		// if (View != null)
		// {
		// context.Response.Write(View.OutXml);
		// View = null;
		// }
		if (apiResult.length() > 0) {
			String outResult = apiResult.toString();
			String contentType=response.getContentType();
			if (contentType==null || contentType.length() == 0) {
				contentType="text/html;charset=" + encode;
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

	public void write(String msg, Boolean isSuccess) {
		String json="{\"success\":"+(isSuccess?"true":"false")+",\"msg\":\""+msg+"\"}";
		apiResult.append(json);
	}

	public Boolean beforeInvoke(String methodName)
    {
        return true;
    }
    public void endInvoke(String methodName)
    {

    }
    //region override
    
    //Сд�ǹؼ��֣�������ô�д�ˡ�
    public void Default()
    {

    }
    /// <summary>
    /// if the result is false will stop invoke method
    /// <para>�������Ƿ�ͨ��</para>
    /// </summary>
    /// <returns></returns>
    public Boolean checkToken()
    {
        return true;
    }
    /// <summary>
    /// if the result is false will stop invoke method
    /// <para>��������Ƿ�Ϸ�</para>
    /// </summary>
    /// <returns></returns>
    public Boolean checkAck()
    {
        return true;
    }

    /// <summary>
    /// if the result is false will stop invoke method
    /// <para>���΢�����������Ƿ�Ϸ�</para>
    /// </summary>
    /// <returns></returns>
    public Boolean checkMicroService()
    {
    	return true;
        //return MicroService.Config.ServerKey == Context.Request.Headers[MicroService.Const.HeaderKey];
    }
    //endregion

    public String query(String key) {
    	String value=request.getParameter(key);
    	if(value==null)
    	{
    		if(getIsHttpPost())
    		{
    			String contentType=request.getContentType();
    			if(contentType==null || !contentType.startsWith("multipart/form-data"))//���ļ��ϴ�
    			{
		    		if(keyValueForPost==null)
		    		{
		    			initFormValueOnPost();
		    		}
		    		if(keyValueForPost.containsKey(key.toLowerCase()))
		    		{
		    			value=keyValueForPost.get(key.toLowerCase());
		    		}
    			}
    		}
    		if(value==null)
    		{
    			value=request.getHeader(key);
    		}
    	}
    	return value;
	}

	public <T> T query(String key,Class<T> object)
    {
    	return ConvertTool.tryChangeType(query(key), object);
    }
    

    private void initFormValueOnPost() {
    	keyValueForPost=new HashMap<String, String>();
		try {
			HttpInputStream stream = (HttpInputStream)request.getInputStream();
	        if (stream != null)
	        {
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
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
