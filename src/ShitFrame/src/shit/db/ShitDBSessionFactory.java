package shit.db;

import shit.db.cfg.ShitDBDataSource;
import shit.db.exception.ShitDBConfigureException;
import shit.db.exception.ShitDBConnectException;

/**
 * 数据库连接会话工厂
 * 
 * @author GongTengPangYi
 *
 */
public interface ShitDBSessionFactory {

	/**
	 * 设置数据源
	 * 
	 * @param dataSource
	 *            数据源
	 */
	public void setDataSource(ShitDBDataSource dataSource);

	/**
	 * 创建连接会话
	 * 
	 * @return 获取连接会话
	 * @throws ShitDBConnectException 
	 * @throws ShitDBConfigureException 
	 */
	public ShitDBSession getSession() throws ShitDBConnectException, ShitDBConfigureException;
}
