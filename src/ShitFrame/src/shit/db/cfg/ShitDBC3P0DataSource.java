package shit.db.cfg;

import java.util.Properties;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * C3P0的数据源
 * 基于C3P0、JDBC
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
	 * @return
	 */
	public ComboPooledDataSource getComboPooledDataSource() {
		return cpds;
	}

	/**
	 * 设置数据源
	 * @param cpds
	 */
	public void setComboPooledDataSource(ComboPooledDataSource cpds) {
		this.cpds = cpds;
	}
	
	/**
	 * 用配置文件的方式设置数据源
	 * @param props
	 */
	public void setComboPooledDataSource(Properties props) {
		cpds.setProperties(props);
	}
}
