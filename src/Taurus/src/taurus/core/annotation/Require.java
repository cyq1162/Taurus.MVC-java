package taurus.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.RetentionPolicy;

@Repeatable(Requires.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD} )
@Inherited
public @interface Require {

	String paraName() default "";
	boolean isRequire() default true;
	String regex() default "";
	String cnParaName() default "";
	String emptyTip() default "";
	String regexTip() default "";
}
