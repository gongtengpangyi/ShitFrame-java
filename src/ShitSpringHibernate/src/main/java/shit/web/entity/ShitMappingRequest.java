package shit.web.entity;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/**
 * 映射参数，用于注解映射的action方法的方法参数时候的特殊参数HttpServletRequest
 * 
 * @author GongTengPangYi
 *
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ShitMappingRequest {
	
}
