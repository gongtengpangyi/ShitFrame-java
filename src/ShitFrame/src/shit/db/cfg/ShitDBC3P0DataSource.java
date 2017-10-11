package shit.db.cfg;

import java.beans.PropertyVetoException;
import java.util.Properties;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * C3P0的数据源 基于C3P0、JDBC
 * 
 * @author GongTengPangYi
 *
 */
public class ShitDBC3P0DataSource extends ShitDBDataSource {
	/**
	 * 数据源
	 */
	ComboPooledDataSource cpds = new ComboPooledDataSource();

	/**
	 * 获取数据源
	 * 
	 * @return
	 */
	public ComboPooledDataSource getComboPooledDataSource() {
		return cpds;
	}

	/**
	 * 设置数据源
	 * 
	 * @param cpds
	 */
	public void setComboPooledDataSource(ComboPooledDataSource cpds) {
		this.cpds = cpds;
	}

	@Override
	public void setDataSourceByProperties(Properties props) {
		//TODO: ??
		try {
			cpds.setProperties(props);
			cpds.setDriverClass(props.getProperty("driverClass"));
			cpds.setJdbcUrl(props.getProperty("jdbcUrl"));
			cpds.setUser(props.getProperty("user"));
			cpds.setPassword(props.getProperty("password"));
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		
	}
}
