package shit.db.execute;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.util.List;

/**
 * 基本数据库操作执行体
 * 
 * @author GongTengPangYi
 *
 * @param <T>
 *            操作返回
 */
public abstract class ShitDBExecute<T> {
	/**
	 * 用于数据库操作的prepareSmatement
	 */
	protected PreparedStatement psmt;

	public ShitDBExecute(PreparedStatement psmt) {
		super();
		this.psmt = psmt;
	}

	public void setPsmt(PreparedStatement psmt) {
		this.psmt = psmt;
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
