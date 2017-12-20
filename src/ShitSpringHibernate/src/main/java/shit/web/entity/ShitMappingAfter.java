package shit.web.entity;

import java.lang.annotation.*;

/**
 * 注解映射请求过程中对应action的后续处理
 * 方法参数只能是request、response和Object（对应返回操作的参数）
 * 
 * @author GongTengPangYi
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ShitMappingAfter {

	/**
	 * URL段，
	 * 
	 * @return
	 */
	String url() default "";

}
