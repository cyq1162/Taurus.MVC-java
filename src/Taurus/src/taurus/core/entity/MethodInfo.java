package taurus.core.entity;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class MethodInfo {

	private Method _Method;
	private AnnotationInfo _AnnotationInfo;
	private Parameter[] _Parameters;
	public MethodInfo(Method method,AnnotationInfo annotation) {
		this._Method=method;
		this._Parameters=method.getParameters();
		this._AnnotationInfo=annotation;
		
		// TODO Auto-generated constructor stub
	}
	public Method getMethod() {
		return _Method;
	}
	public AnnotationInfo getAnnotationInfo() {
		return _AnnotationInfo;
	}
	public Parameter[] getParameters() {
		return _Parameters;
	}
}
