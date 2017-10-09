package shit.db;

/**
 * 数据库连接会话工厂
 * 
 * @author GongTengPangYi
 *
 */
public interface ShitDBSessionFactory {

	/**
	 * 创建连接会话
	 * 
	 * @return 获取连接会话
	 */
	public ShitDBSession getSession();
}
