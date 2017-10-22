package test.db;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import shit.db.exception.ShitDBResultException;
import shit.db.exception.ShitDBTranslateException;
import shit.db.exception.ShitDBWrongControlException;
import shit.db.execute.ShitDBDaoDelete;
import shit.db.execute.ShitDBDaoSave;
import shit.db.execute.ShitDBDaoUpdate;
import shit.db.query.ShitDBPager;
import shit.db.query.ShitDBQuery;
import shit.db.query.ShitDBQueryBasic;
import test.db.model.Trade;
import test.db.model.User;

public class DaoTest {
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
	public void testQuery() {
		String shitQL = "select * from " + Trade.class.getName() + 
				" o where o.name=:name order by o.id";		
		Map<String, Serializable> params = new HashMap<>(1);
		params.put("name", "xxxx");
		ShitDBPager pager = new ShitDBPager(1, 1);
		try {
			ShitDBQuery query = new ShitDBQueryBasic(getConn(), Trade.class, shitQL, pager, params);
			List<Serializable> list = query.query();
			for (Serializable model : list) {
				System.out.println(model);
			}
		} catch (ShitDBTranslateException e) {
			e.printStackTrace();
		} catch (ShitDBExecuteException e) {
			e.printStackTrace();
		} catch (ShitDBResultException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ShitDBWrongControlException e) {
			e.printStackTrace();
		} catch (ShitDBConfigureException e) {
			e.printStackTrace();
		} 
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
		ShitDBDaoSave save = new ShitDBDaoSave(getConn(), trade);
		try {
			save.excute();
		} catch (ShitDBExecuteException e) {
			e.printStackTrace();
		} catch (ShitDBTranslateException e) {
			e.printStackTrace();
		}
		System.out.println(trade);
		System.out.println(trade.getId());
	}
	
	@Test
	public void testUpdate() {
		Trade trade = new Trade();
		trade.setCount(3);
		trade.setId((long) 36);
		ShitDBDaoUpdate update = new ShitDBDaoUpdate(getConn(), trade);
		try {
			System.out.println(update.excute());
			System.out.println(trade);
			System.out.println(trade.getId());
		} catch (ShitDBExecuteException e) {
			e.printStackTrace();
		} catch (ShitDBTranslateException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDelete() {
		Trade trade = new Trade();
		trade.setCount(3);
		trade.setId((long) 36);
		ShitDBDaoDelete update = new ShitDBDaoDelete(getConn(), trade);
		try {
			update.excute();
		} catch (ShitDBExecuteException e) {
			e.printStackTrace();
		} catch (ShitDBTranslateException e) {
			e.printStackTrace();
		}
	}
}
