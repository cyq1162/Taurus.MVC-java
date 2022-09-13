package taurus.mvc.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import taurus.mvc.annotation.Ack;
import taurus.mvc.annotation.HttpDelete;
import taurus.mvc.annotation.HttpGet;
import taurus.mvc.annotation.HttpHead;
import taurus.mvc.annotation.HttpPost;
import taurus.mvc.annotation.HttpPut;
import taurus.mvc.annotation.IgnoreDefaultController;
import taurus.mvc.annotation.MicroService;
import taurus.mvc.annotation.Require;
import taurus.mvc.annotation.Token;
import taurus.mvc.entity.AnnotationInfo;
import taurus.mvc.entity.MethodInfo;

public class MethodCollector {

    static Map<String, Map<String, MethodInfo>> typeMethods = new HashMap<String, Map<String, MethodInfo>>();

    private static Lock lock = new ReentrantLock();
    static Map<String, MethodInfo> getMethods(Class<?> t)
    {
		if (!typeMethods.containsKey(t.getName())) {
			lock.lock();
			try {
				if (!typeMethods.containsKey(t.getName())) {
					addMethodInfo(t);
				}
			} finally {
				lock.unlock();
			}
		}
    	return typeMethods.get(t.getName());
    }
    
    

	static void addMethodInfo(Class<?> t) {

		Boolean hasToken = t.isAnnotationPresent(Token.class);
		Boolean hasAck = t.isAnnotationPresent(Ack.class);
		Boolean hasMicroService = t.isAnnotationPresent(MicroService.class);
		Boolean hasIgnoreDefaultController = t.isAnnotationPresent(IgnoreDefaultController.class);
		
		Method[] methods = t.getDeclaredMethods();
		Map<String, MethodInfo> dic = new HashMap<String, MethodInfo>();

		for (Method method : methods) {
			int mod = method.getModifiers();
			String name = method.getName().toLowerCase();
			if (!Modifier.isPublic(mod) || dic.containsKey(name)) {
				continue;
			}
			if (Modifier.isStatic(mod)) {
				if (t.getName().endsWith("DefaultController")) {
					name = "static." + name;// 存档全局的静态方法。
				} else {
					continue;
				}
			}
			if(dic.containsKey(name)){continue;}
			AnnotationInfo anno = new AnnotationInfo();
			anno.setHasToken(hasToken || method.isAnnotationPresent(Token.class));
			anno.setHasAck(hasAck || method.isAnnotationPresent(Ack.class));
			anno.setHasMicroService(hasMicroService || method.isAnnotationPresent(MicroService.class));
			anno.setHasIgnoreDefaultController(hasIgnoreDefaultController);
			
			anno.setHasHttpGet(method.isAnnotationPresent(HttpGet.class));
			anno.setHasHttpPost(method.isAnnotationPresent(HttpPost.class));
			anno.setHasHttpHead(method.isAnnotationPresent(HttpHead.class));
			anno.setHasHttpPut(method.isAnnotationPresent(HttpPut.class));
			anno.setHasHttpDelete(method.isAnnotationPresent(HttpDelete.class));
			Require[] requires = method.getAnnotationsByType(Require.class);
			if (requires != null && requires.length > 0) {
				anno.setHasRequire(true);
				anno.setRequireAttrs(requires);
			}
			dic.put(name, new MethodInfo(method, anno));

		}
		typeMethods.put(t.getName(), dic);

	}

	public static MethodInfo getMethod(Class<?> t, String methodName) {
		return getMethod(t, methodName, true);
	}

	public static MethodInfo getMethod(Class<?> t, String methodName, Boolean isReturnDefault) {
		Map<String, MethodInfo> methods = getMethods(t);
		if (methodName != null && methodName.length() > 0) {
			methodName = methodName.toLowerCase();
			if (methods.containsKey(methodName)) {
				return methods.get(methodName);
			}
		}
		if (isReturnDefault && methods.containsKey("default")) {
			return methods.get("default");
		}
		return null;
	}

	private static MethodInfo getGlobalMethod(String name) {
		Class<?> t = ControllerCollector.getController("default");
		if (t != null) {
			return getMethod(t, "static." + name, false);
		}
		return null;
	}
    /// <summary>
    /// 全局Default方法
    /// </summary>
    public static MethodInfo getGlobalDefault()
    {
       return getGlobalMethod("default");
    }

    /// <summary>
    /// 全局DefaultCheckAck方法
    /// </summary>
    public static MethodInfo getGlobalCheckAck()
    {
    	return getGlobalMethod("checkack");
    }
    /// <summary>
    /// 全局DefaultCheckMicroService方法
    /// </summary>
    public static MethodInfo getGlobalCheckMicroService()
    {
    	return getGlobalMethod("checkmicroservice");
    }

    /// <summary>
    /// 全局CheckToken方法
    /// </summary>
    public static MethodInfo getGlobalCheckToken()
    {
    	return getGlobalMethod("checktoken");
    }
    /// <summary>
    ///  全局BeforeInvoke方法
    /// </summary>
    public static MethodInfo getGlobalBeforeInvoke()
    {
    	return getGlobalMethod("beforeinvoke");
    }

    /// <summary>
    ///  全局BeforeInvoke方法
    /// </summary>
    public static MethodInfo getGlobalRouteMapInvoke()
    {
    	return getGlobalMethod("routemapinvoke");
    }

    /// <summary>
    ///  全局EndInvokeMethod方法
    /// </summary>
    public static MethodInfo getGlobalEndInvoke()
    {
    	return getGlobalMethod("endinvoke");
    }
    
    /// <summary>
    ///  全局EndInvokeMethod方法
    /// </summary>
    public static MethodInfo getGlobalOnError()
    {
    	return getGlobalMethod("onerror");
    }
}
