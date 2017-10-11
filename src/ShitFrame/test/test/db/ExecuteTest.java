package test.db;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;

import shit.db.cfg.ShitDBC3P0DataSource;
import shit.db.cfg.ShitDBDataSource;
import shit.db.connection.ShitDBConnection;
import shit.db.connection.ShitDBConnectionC3P0;
import shit.db.exception.ShitDBConfigureException;
import shit.db.exception.ShitDBConnectException;
import shit.db.exception.ShitDBExecuteException;
import shit.db.exception.ShitDBTranslateException;
import shit.db.execute.ShitDBExecuteSQLNotQuery;
import shit.db.execute.ShitDBExecuteSQLQuery;
import shit.db.query.ShitDBPager;
import shit.db.sql.ShitQLBuilder;
import shit.db.sql.ShitQLDelete;
import shit.db.sql.ShitQLSave;
import shit.db.sql.ShitQLTranslator;
import shit.db.sql.ShitQLUpdate;
import test.db.model.Trade;
import test.db.model.User;

public class ExecuteTest {
	@Test
	public void test1() {
		Properties props = new Properties();
		props.setProperty("driverClass", "com.mysql.jdbc.Driver");
		props.setProperty("jdbcUrl", "jdbc:mysql://localhost:3306/superMap");
		props.setProperty("user", "root");
		props.setProperty("password", "wjfrz");
		ShitDBDataSource dataSource = new ShitDBC3P0DataSource();
		dataSource.setDataSourceByProperties(props);
		ShitDBConnection conn = new ShitDBConnectionC3P0();
		try {
			conn.setDataSource(dataSource);
			System.out.println(conn.getConnection());
		} catch (ShitDBConfigureException e) {
			e.printStackTrace();
		} catch (ShitDBConnectException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConn() {
		Properties props = new Properties();
		props.setProperty("driverClass", "com.mysql.jdbc.Driver");
		props.setProperty("jdbcUrl", "jdbc:mysql://localhost:3306/superMap");
		props.setProperty("user", "root");
		props.setProperty("password", "wjfrz");
		ShitDBDataSource dataSource = new ShitDBC3P0DataSource();
		dataSource.setDataSourceByProperties(props);
		ShitDBConnection conn = new ShitDBConnectionC3P0();
		try {
			conn.setDataSource(dataSource);
			return conn.getConnection();
		} catch (ShitDBConfigureException e) {
			e.printStackTrace();
		} catch (ShitDBConnectException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Test
	public void testSave() {
		Trade trade = new Trade();
		trade.setCount(1);
		trade.setCutTime(new Date());
		trade.setDiscount((float) 0.7);
		trade.setName("xxxx");
		User user = new User();
		user.setId((long) 1);
		trade.setUser(user);
		try {
			ShitQLBuilder save = new ShitQLSave(trade).buildShitQL();
			String shitQL = save.getShitQL();
			Map<String, Serializable> params = save.getParamMap();
			System.out.println(shitQL);
			System.out.println(params);
			ShitQLTranslator translator = new ShitQLTranslator("test.db.model", shitQL, params, null);
			ShitDBExecuteSQLNotQuery saveExcute = new ShitDBExecuteSQLNotQuery(getConn());
			saveExcute.execute(translator.getSql(), translator.getParamList());
			System.out.println(translator.getSql());
			System.out.println(translator.getParamList());
		} catch (ShitDBTranslateException e) {
			e.printStackTrace();
		} catch (ShitDBExecuteException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdate() {
		Trade trade = new Trade();
		trade.setCount(1);
		trade.setCutTime(new Date());
		trade.setDiscount((float) 0.8);
		trade.setName("sdsds");
		trade.setId((long) 18);
		User user = new User();
		user.setId((long) 1);
		trade.setUser(user);
		try {
			Map<String, Serializable> whereParam = new HashMap<>();
			whereParam.put("id", 1);
			ShitQLBuilder update = new ShitQLUpdate(trade).buildShitQL();
			String shitQL = update.getShitQL();
			Map<String, Serializable> params = update.getParamMap();
			System.out.println(shitQL);
			System.out.println(params);
			ShitQLTranslator translator = new ShitQLTranslator("test.db.model", shitQL, params, null);
			ShitDBExecuteSQLNotQuery saveExcute = new ShitDBExecuteSQLNotQuery(getConn());
			System.out.println(translator.getSql());
			System.out.println(translator.getParamList());
			saveExcute.execute(translator.getSql(), translator.getParamList());
		} catch (ShitDBTranslateException e) {
			e.printStackTrace();
		} catch (ShitDBExecuteException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testDelete() {
		Trade trade = new Trade();
		trade.setId((long) 18);
		try {
			Map<String, Serializable> whereParam = new HashMap<>();
			whereParam.put("name", "xxxx");
			ShitQLBuilder delete = new ShitQLDelete(trade).buildShitQL();
			String shitQL = delete.getShitQL();
			Map<String, Serializable> params = delete.getParamMap();
			System.out.println(shitQL);
			System.out.println(params);
			ShitQLTranslator translator = new ShitQLTranslator("test.db.model", shitQL, params, null);
			ShitDBExecuteSQLNotQuery saveExcute = new ShitDBExecuteSQLNotQuery(getConn());
			System.out.println(translator.getSql());
			System.out.println(translator.getParamList());
			saveExcute.execute(translator.getSql(), translator.getParamList());
		} catch (ShitDBTranslateException e) {
			e.printStackTrace();
		} catch (ShitDBExecuteException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testQuery() {
		String shitQL = "select * from " + Trade.class.getName() + 
				" o where o.name=:name and o.user.id=1 order by o.id";		
		Map<String, Serializable> params = new HashMap<>(1);
		params.put("name", "xxxx");
		ShitDBPager pager = new ShitDBPager(1, 1);
		try {
			ShitQLTranslator translator = new ShitQLTranslator("test.db.model", shitQL, params, pager);
			ShitDBExecuteSQLQuery query = new ShitDBExecuteSQLQuery(getConn());
			ResultSet rs = query.execute(translator.getSql(), translator.getParamList());
			while (rs.next()) {
				System.out.println(rs.getString("id"));
				System.out.println(rs.getString("name"));
				System.out.println(rs.getString("discount"));
				System.out.println(rs.getString("cutTime"));
			}
		} catch (ShitDBTranslateException e) {
			e.printStackTrace();
		} catch (ShitDBExecuteException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
