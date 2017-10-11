package shit.db.query;

import java.sql.ResultSet;

import shit.db.exception.ShitDBConfigureException;
import shit.db.exception.ShitDBResultException;
import shit.db.exception.ShitDBTranslateException;

/**
 * 处理数据库查询结果的接口
 * 
 * @author GongTengPangYi
 *
 */
public interface ShitDBResult<T> {

	/**
	 * 解析结果集
	 * 
	 * @param resultSet
	 *            结果集
	 * @return 处理完的结果
	 * @throws ShitDBResultException
	 *             结果处理异常
	 * @throws ShitDBConfigureException 
	 * 			      配置异常
	 * @throws ShitDBTranslateException 
	 */
	public T analysis(ResultSet resultSet) throws ShitDBResultException, ShitDBConfigureException, ShitDBTranslateException;
}
