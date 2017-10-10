package shit.db.execute;

/**
 * ShitQL语句操作的执行体
 * @author GongTengPangYi
 * @param <T>
 *            对应的ShitDBExecuteSQL
 * @param <E>
 *            操作返回
 */
public abstract class ShitDBExecuteShitQL<T extends ShitDBExecuteSQL<E>, E> {
	/**
	 * SQL语句执行体
	 */
	T t;
	
	

}
