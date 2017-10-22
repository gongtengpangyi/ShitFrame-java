package shit.db.impl;

import shit.db.ShitDBSession;
import shit.db.ShitDBSessionFactory;
import shit.db.cfg.ShitDBC3P0DataSource;
import shit.db.cfg.ShitDBDataSource;
import shit.db.connection.ShitDBConnection;
import shit.db.connection.ShitDBConnectionC3P0;
import shit.db.exception.ShitDBConfigureException;
import shit.db.exception.ShitDBConnectException;

/**
 * 生成SessionJDBC的工厂类
 * 
 * @author GongTengPangYi
 *
 */
public class ShitDBSessionJDBCFactory implements ShitDBSessionFactory {

	private ShitDBDataSource dataSource;

	private ShitDBConnection conn;

	public ShitDBSessionJDBCFactory() {
		super();
	}

	@Override
	public void setDataSource(ShitDBDataSource dataSource) {
		this.dataSource = dataSource;
		if (dataSource instanceof ShitDBC3P0DataSource) {
			conn = new ShitDBConnectionC3P0();
			try {
				conn.setDataSource(dataSource);
			} catch (ShitDBConfigureException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public ShitDBSession getSession() throws ShitDBConnectException {
		return new ShitDBSessionJDBC(conn.getConnection(), dataSource.isShowSql());
	}

}
