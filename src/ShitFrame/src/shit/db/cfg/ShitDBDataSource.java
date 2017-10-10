package shit.db.cfg;

/**
 * 数据库数据源
 * 
 * @author GongTengPangYi
 *
 */
public class ShitDBDataSource {
	/**
	 * 驱动器
	 */
	private String driverClass;
	/**
	 * 数据库连接url
	 */
	private String jdbcUrl;
	/**
	 * 用户名
	 */
	private String user;
	/**
	 * 密码
	 */
	private String password;

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
