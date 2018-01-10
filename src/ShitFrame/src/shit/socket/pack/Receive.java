package shit.socket.pack;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解接收到的数据包
 * 
 * @author GongTengPangYi
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Receive {

	/**
	 * 数据中用于指代对应数据包类的键名
	 * 
	 * @return
	 */
	public String objKey() default "type";

	/**
	 * 数据中用于指代对应数据包类的值
	 * 
	 * @return
	 */
	public String objValue() default "";
}
