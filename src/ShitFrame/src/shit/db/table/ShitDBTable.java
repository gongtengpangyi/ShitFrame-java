package shit.db.table;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据表的注解，用于注解model类
 * @author GongTengPangYi
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ShitDBTable {
	/**
	 * 表名
	 * @return
	 */
	public String name() default "";
	
	/**
	 * 主键
	 * @return
	 */
	public String primaryKey() default "";
}
