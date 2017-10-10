package shit.db.execute;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

/**
 * 基本数据库操作执行体
 * 
 * @author GongTengPangYi
 *
 * @param <T>
 *            操作返回
 */
public abstract class ShitDBExecuteSQL<T> {
	/**
	 * 数据库连接
	 */
	protected Connection conn;

	public ShitDBExecuteSQL(Connection conn) {
		super();
		this.conn = conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	/**
	 * 执行带占位符数据库操作
	 * 
	 * @param sql
	 *            占位符
	 * @param params
	 *            占位符参数
	 * @return 处理的返回结果
	 */
	public abstract T execute(String sql, List<Serializable> params);
}
