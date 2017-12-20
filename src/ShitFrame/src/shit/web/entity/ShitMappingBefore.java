package shit.web.entity;

import java.lang.annotation.*;

/**
 * 注解映射请求过程中对应action的预处理
 * 方法参数只能包括request和response
 * 
 * @author GongTengPangYi
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ShitMappingBefore {

	/**
	 * URL段，
	 * 
	 * @return
	 */
	String url() default "";

}
