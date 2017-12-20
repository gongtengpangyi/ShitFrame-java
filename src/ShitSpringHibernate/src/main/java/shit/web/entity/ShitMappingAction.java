package shit.web.entity;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解映射请求过程中对应action
 * 
 * @author GongTengPangYi
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ShitMappingAction {

	/**
	 * action的URL段，即action名
	 * 
	 * @return
	 */
	String name() default "";

	/**
	 * 请求的方式
	 * 
	 * @return
	 */
	Type type() default Type.GET_AND_POST;

	/*
	 * 枚举几种常见的请求方式
	 */
	enum Type {
		/**
		 * get请求
		 */
		GET,
		/**
		 * post请求
		 */
		POST,
		/**
		 * 通用get和post请求
		 */
		GET_AND_POST
	}
}
