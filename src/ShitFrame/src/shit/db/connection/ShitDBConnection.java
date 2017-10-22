package shit.db.connection;

import java.sql.Connection;

import shit.db.cfg.ShitDBDataSource;
import shit.db.exception.ShitDBConfigureException;
import shit.db.exception.ShitDBConnectException;

/**
 * 数据库连接接口，用于完成数据库连接，并为其他操作提供获取数据库连接的接口
 * 
 * @author GongTengPangYi
 *
 */
public interface ShitDBConnection {

	/**
	 * 设置数据源
	 * 
	 * @param shitDataSource
	 *            数据源
	 * @throws ShitDBConfigureException
	 *             数据配置异常
	 */
	public void setDataSource(ShitDBDataSource shitDataSource) throws ShitDBConfigureException;

	/**
	 * 获取连接
	 * 
	 * @return 数据库连接
	 * @throws ShitDBConnectException
	 *             连接异常
	 */
	public Connection getConnection() throws ShitDBConnectException;

	/**
	 * 关闭连接
	 * 
	 * @param connection
	 *            数据库连接
	 * @throws ShitDBConnectException
	 *             连接异常
	 */
	public void close(Connection connection) throws ShitDBConnectException;
}
