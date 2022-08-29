package taurus.mvc.annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD} )
@Inherited
public @interface HttpHead {

}
