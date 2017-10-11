package shit.db.execute;

import shit.db.exception.ShitDBExecuteException;
import shit.db.exception.ShitDBTranslateException;
import shit.db.sql.ShitQLTranslator;

/**
 * ShitQL语句操作的执行体
 * 
 * @author GongTengPangYi
 * @param <T>
 *            对应的ShitDBExecuteSQL
 * @param <E>
 *            操作返回
 */
public abstract class ShitDBDao<T extends ShitDBExecuteSQL<?>, E> {

	/**
	 * SQL语句执行体
	 */
	T execute;

	/**
	 * 语言翻译器
	 */
	ShitQLTranslator translator;

	/**
	 * 设置翻译器
	 * 
	 * @throws ShitDBTranslateException
	 * 
	 */
	protected abstract void setTranslator() throws ShitDBTranslateException;

	/**
	 * 执行数据库操作
	 * 
	 * @return 返回值
	 * @throws ShitDBExecuteException
	 *             执行出错
	 * @throws ShitDBTranslateException 
	 */
	public abstract E excute() throws ShitDBExecuteException, ShitDBTranslateException;

}
