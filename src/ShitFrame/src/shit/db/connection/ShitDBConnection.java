package shit.db.connection;

import java.sql.Connection;

/**
 * 数据库连接接口
 * 
 * @author GongTengPangYi
 *
 */
public interface ShitDBConnection {
	/**
	 * 获取连接
	 * 
	 * @return 数据库连接
	 */
	public Connection getConnection();

	/**
	 * 关闭连接
	 * 
	 * @param connection
	 *            数据库连接
	 */
	public void close(Connection connection);
}
