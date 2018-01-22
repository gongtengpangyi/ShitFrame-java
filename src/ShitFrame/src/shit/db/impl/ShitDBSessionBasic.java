package shit.db.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.sql.Connection;

import shit.db.ShitDBSession;
import shit.db.exception.ShitDBConfigureException;
import shit.db.exception.ShitDBExecuteException;
import shit.db.exception.ShitDBJDBCException;
import shit.db.exception.ShitDBTranslateException;
import shit.db.exception.ShitDBWrongControlException;
import shit.db.execute.ShitDBDaoDelete;
import shit.db.execute.ShitDBDaoSave;
import shit.db.execute.ShitDBDaoUpdate;
import shit.db.query.ShitDBPager;
import shit.db.table.ShitDBTable;
import shit.helper.ShitReflectException;
import shit.helper.ShitReflectHelper;

/**
 * Session的基本实现类，实现了除了query外的基本操作，是大多数Session的基类
 * 
 * @author GongTengPangYi
 *
 */
public abstract class ShitDBSessionBasic implements ShitDBSession {

	protected Connection conn;

	protected boolean showSql;

	public ShitDBSessionBasic(Connection conn) {
		this(conn, false);
	}

	public ShitDBSessionBasic(Connection conn, boolean showSql) {
		super();
		this.setConn(conn);
		this.showSql = showSql;
	}

	public void setShowSql(boolean showSql) {
		this.showSql = showSql;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Serializable save(Serializable model) throws ShitDBExecuteException, ShitDBConfigureException {
		ShitDBDaoSave save = new ShitDBDaoSave(conn, model);
		save.setShowSql(showSql);
		try {
			return save.excute();
		} catch (ShitDBTranslateException e) {
			e.printStackTrace();
			throw new ShitDBConfigureException("model类" + model.getClass().getName() + "配置异常");
		}
	}

	@Override
	public Serializable update(Serializable model) throws ShitDBExecuteException, ShitDBConfigureException {
		ShitDBDaoUpdate update = new ShitDBDaoUpdate(conn, model);
		update.setShowSql(showSql);
		try {
			return update.excute();
		} catch (ShitDBTranslateException e) {
			e.printStackTrace();
			throw new ShitDBConfigureException("model类" + model.getClass().getName() + "配置异常");
		}
	}

	@Override
	public Serializable saveOrUpdate(Serializable model) throws ShitDBConfigureException, ShitDBExecuteException {
		Class<?> clazz = model.getClass();
		ShitDBTable dbTable = clazz.getAnnotation(ShitDBTable.class);
		if (dbTable == null) {
			throw new ShitDBConfigureException("该类没有配置数据库");
		}
		String primaryKey = dbTable.primaryKey();
		try {
			if (ShitReflectHelper.getValue(model, primaryKey, true) != null) {
				return this.update(model);
			} else {
				return this.save(model);
			}
		} catch (ShitReflectException e) {
			e.printStackTrace();
			throw new ShitDBConfigureException("model类" + model.getClass().getName() + "配置异常");
		}
	}

	@Override
	public List<? extends Serializable> query(Class<? extends Serializable> clazz, String shitQL) throws ShitDBJDBCException,
			ShitDBWrongControlException, ShitDBConfigureException, ShitDBTranslateException {
		return query(clazz, shitQL, null, null);
	}

	@Override
	public List<? extends Serializable> query(Class<? extends Serializable> clazz, String shitQL,
			Map<String, Serializable> params) throws ShitDBJDBCException, ShitDBWrongControlException,
					ShitDBConfigureException, ShitDBTranslateException {
		return query(clazz, shitQL, params, null);
	}

	@Override
	public List<? extends Serializable> query(Class<? extends Serializable> clazz, String shitQL, ShitDBPager pager)
			throws ShitDBJDBCException, ShitDBWrongControlException, ShitDBConfigureException,
			ShitDBTranslateException {
		return query(clazz, shitQL, null, pager);
	}

	@Override
	public List<? extends Serializable> findAll(Class<? extends Serializable> clazz) throws ShitDBJDBCException,
			ShitDBWrongControlException, ShitDBConfigureException, ShitDBTranslateException {
		String shitQL = "select * from " + clazz.getName();
		return query(clazz, shitQL, null, null);
	}

	@Override
	public Serializable findById(Class<? extends Serializable> clazz, Serializable id)
			throws ShitDBConfigureException, ShitDBJDBCException, ShitDBWrongControlException {
		ShitDBTable dbTable = clazz.getAnnotation(ShitDBTable.class);
		if (dbTable == null) {
			throw new ShitDBConfigureException("该类没有配置数据库");
		}
		String primaryKey = dbTable.primaryKey();
		String shitQL = "select * from " + clazz.getName() + " o where o." + primaryKey + "=:" + primaryKey;
		Map<String, Serializable> params = new HashMap<>(1);
		params.put(primaryKey, id);
		List<? extends Serializable> list;
		try {
			list = query(clazz, shitQL, params);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
		} catch (ShitDBTranslateException e) {
			e.printStackTrace();
			throw new ShitDBConfigureException("model类" + clazz.getName() + "配置异常");
		}
		return null;
	}

	@Override
	public void delete(Serializable model) throws ShitDBExecuteException, ShitDBConfigureException {
		ShitDBDaoDelete delete = new ShitDBDaoDelete(conn, model);
		delete.setShowSql(showSql);
		try {
			delete.excute();
		} catch (ShitDBTranslateException e) {
			e.printStackTrace();
			throw new ShitDBConfigureException("model类" + model.getClass().getName() + "配置异常");
		}

	}

}
