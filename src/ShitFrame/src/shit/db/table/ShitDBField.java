package shit.db.table;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据库字段注解，用于注解model类的内部变量
 * @author GongTengPangYi
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ShitDBField {
	/**
	 * 数据库字段名
	 * @return
	 */
	public String name() default "";
	
	/**
	 * 是否非空（默认否）
	 * @return
	 */
	public boolean notNull() default false;
	
	/**
	 * 长度(默认不做限制)
	 * @return
	 */
	public int length() default -1;
	
	/**
	 * 外键链接的表对应的model类全名
	 * @return
	 */
	public String foreignClass() default "";
}
