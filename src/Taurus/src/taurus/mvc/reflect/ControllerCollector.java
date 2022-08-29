package taurus.mvc.reflect;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import taurus.mvc.config.MvcConfig;
import taurus.mvc.http.HttpContext;

public class ControllerCollector {

	private static Map<String, Class<?>> _Lv1Controllers = new HashMap<String, Class<?>>();
	/// <summary>
	/// 存档二级名称的控制器[Module.Controller]
	/// </summary>
	private static Map<String, Class<?>> _Lv2Controllers = new HashMap<String, Class<?>>();
	private static Lock lock = new ReentrantLock();
    /**
     * 控制器收集初始化：由系统进行调用
     */
	public static void initController() {
    	if (_Lv1Controllers.size() == 0) {
			lock.lock();
			try {
				if (_Lv1Controllers.size() == 0) {
					String runPath = ControllerCollector.class.getResource("/").getPath();
					String jarNames = MvcConfig.getControllerJarNames();
					if (jarNames != null) {	
						String[] file = jarNames.split(",");
						for (String jar : file) {
							try {
								if (!jar.endsWith(".jar")) {
									jar=jar+".jar";
								}
								initControllersByJar(runPath.replace("/classes/", "/lib/") + jar);
							} catch (Exception e) {
								// TODO: handle exception
							}

						}
					}
					String rootPath = runPath.replace("/lib/", "/classes/");
					initControllersByProjectClass(rootPath, rootPath);
				}
			} finally {
				lock.unlock();
			}
		}
	}
	private static Map<String, Class<?>> getControllers(int level) {
		//initController();
		return level == 1 ? _Lv1Controllers : _Lv2Controllers;
	}

	
	private static void initControllersByProjectClass(String classPath, String rootPath) {
		File file = new File(classPath);
		if (file.isDirectory()) {
			for (String path : file.list()) {
				initControllersByProjectClass(file.getPath() + "/" + path, rootPath);
			}
		} else {
			String path = file.getAbsolutePath();
			if (path.endsWith(".class")) {
				String className = path.substring(rootPath.length()-1).replace(".class", "").replace("/", ".").replace("\\", ".");
				addController(className);
			}
		}
	}

	private static void addController(String className) {
		try {
			HttpContext.Current.log("Collect Controller : "+className);
			Class<?> classType = Class.forName(className);// test.xxx.yyy
			Class<?> super1 = classType.getSuperclass();
			Class<?> super2 = super1 == null ? null : super1.getSuperclass();
			Class<?> super3 = super2 == null ? null : super2.getSuperclass();
			if ((super1 != null && taurus.mvc.Controller.class.equals(super1))
					|| (super2 != null && taurus.mvc.Controller.class.equals(super2))
					|| (super3 != null && taurus.mvc.Controller.class.equals(super3))) {
				String lv1Name = getLevelName(className, 1);
				String lv2Name = getLevelName(className, 2);
				if (!_Lv1Controllers.containsKey(lv1Name)) {
					_Lv1Controllers.put(lv1Name, classType);
				} else {
					String fullName = _Lv1Controllers.get(lv1Name).getName();
					int value = lv2Name.compareTo(getLevelName(fullName, 2));
					if (value < 0) {
						_Lv1Controllers.put(lv1Name, classType);// 值小的优化。
					}
				}
				_Lv2Controllers.put(lv2Name, classType);
			}

		} catch (Exception e) {
//			String err = e.getMessage();
//			String bb = err;
			// TODO: handle exception
		}

	}

	private static String getLevelName(String fullName, int level) {
		String[] items = fullName.split("\\.");
		String lv1Name = items[items.length - 1].replace("Controller", "");
		if (level == 2) 
		{
			return (items[items.length - 2] + "." + lv1Name).toLowerCase();
		}
		return lv1Name.toLowerCase();
	}

	private static void initControllersByJar(String jarPath) throws IOException {
		if (!new File(jarPath).exists()) {
			return;
		}
		JarFile jarFile = null;
		try {
			jarFile = new JarFile(jarPath);
			Enumeration<JarEntry> e = jarFile.entries();

			JarEntry entry;
			while (e.hasMoreElements()) {
				entry = (JarEntry) e.nextElement();
				if (entry.getName().indexOf("META-INF") < 0 && entry.getName().indexOf(".class") >= 0) {
					String classFullName = entry.getName();
					// 去掉后缀.class
					String className = classFullName.substring(0, classFullName.length() - 6).replace("/", ".");
					addController(className);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			jarFile.close();
		}
	}

	public static Class<?> getController(String className) {
		Map<String, Class<?>> controllers = getControllers(1);
		if(controllers.size()==0)
		{
			return null;
		}
		if (className == null || className.length() == 0) {
			className = "default";
		}
		else
		{
			className=className.toLowerCase();
		}
		String[] names = className.split("\\.");// home/index
		if (MvcConfig.getRouteMode() == 1 || names.length < 2) {
			if (controllers.containsKey(names[0])) {
				return controllers.get(names[0]);
			}
			if (names.length > 1 && controllers.containsKey(names[1])) {
				return controllers.get(names[1]);
			}
		} else if (MvcConfig.getRouteMode() == 2) {
			Map<String, Class<?>> controllers2 = getControllers(2);
			if (controllers2.containsKey(className)) {
				return controllers2.get(className);
			}
			// 再查一级路径
			if (controllers.containsKey(names[1])) {
				return controllers.get(names[1]);
			}
			// 兼容【路由1=》（变更为）2】
			if (controllers.containsKey(names[0])) {
				return controllers.get(names[0]);
			}
		}

		if (controllers.containsKey("default")) {
			return controllers.get("default");
		}
		return null;
	}
}
