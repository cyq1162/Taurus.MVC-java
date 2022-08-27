package taurus.core.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import taurus.core.annotation.Ack;
import taurus.core.annotation.HttpDelete;
import taurus.core.annotation.HttpGet;
import taurus.core.annotation.HttpHead;
import taurus.core.annotation.HttpPost;
import taurus.core.annotation.HttpPut;
import taurus.core.annotation.MicroService;
import taurus.core.annotation.Require;
import taurus.core.annotation.Token;
import taurus.core.entity.AnnotationInfo;
import taurus.core.entity.MethodInfo;

public class MethodCollector {

    static Map<String, Map<String, MethodInfo>> typeMethods = new HashMap<String, Map<String, MethodInfo>>();

    private static Lock lock = new ReentrantLock();
    static Map<String, MethodInfo> getMethods(Class<?> t)
    {
		if (!typeMethods.containsKey(t.getName())) {
			lock.lock();
			try {
				if (!typeMethods.containsKey(t.getName())) {
					AddMethodInfo(t);
				}
			} finally {
				lock.unlock();
			}
		}
    	return typeMethods.get(t.getName());
    }
    
    

	static void AddMethodInfo(Class<?> t) {

		Boolean hasToken = t.isAnnotationPresent(Token.class);
		Boolean hasAck = t.isAnnotationPresent(Ack.class);
		Boolean hasMicroService = t.isAnnotationPresent(MicroService.class);

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
					name = "static." + name;// �浵ȫ�ֵľ�̬������
				} else {
					continue;
				}
			}
			AnnotationInfo anno = new AnnotationInfo();
			anno.setHasToken(hasToken || method.isAnnotationPresent(Token.class));
			anno.setHasAck(hasAck || method.isAnnotationPresent(Ack.class));
			anno.setHasMicroService(hasMicroService || method.isAnnotationPresent(MicroService.class));

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
    /// ȫ��Default����
    /// </summary>
    public static MethodInfo getGlobalDefault()
    {
       return getGlobalMethod("default");
    }

    /// <summary>
    /// ȫ��DefaultCheckAck����
    /// </summary>
    public static MethodInfo getGlobalCheckAck()
    {
    	return getGlobalMethod("checkack");
    }
    /// <summary>
    /// ȫ��DefaultCheckMicroService����
    /// </summary>
    public static MethodInfo getGlobalCheckMicroService()
    {
    	return getGlobalMethod("checkmicroservice");
    }

    /// <summary>
    /// ȫ��CheckToken����
    /// </summary>
    public static MethodInfo getGlobalCheckToken()
    {
    	return getGlobalMethod("checktoken");
    }
    /// <summary>
    ///  ȫ��BeforeInvoke����
    /// </summary>
    public static MethodInfo getGlobalBeforeInvoke()
    {
    	return getGlobalMethod("beforeinvoke");
    }

    /// <summary>
    ///  ȫ��BeforeInvoke����
    /// </summary>
    public static MethodInfo getGlobalRouteMapInvoke()
    {
    	return getGlobalMethod("routemapinvoke");
    }

    /// <summary>
    ///  ȫ��EndInvokeMethod����
    /// </summary>
    public static MethodInfo getGlobalEndInvoke()
    {
    	return getGlobalMethod("endinvoke");
    }
}
