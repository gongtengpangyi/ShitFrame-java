package shit.db.execute;

import java.io.Serializable;
import java.sql.Connection;

import shit.db.exception.ShitDBExecuteException;
import shit.db.exception.ShitDBTranslateException;
import shit.db.sql.ShitQLBuilder;
import shit.db.sql.ShitQLTranslator;

/**
 * 非查询实现操作的抽象类，返回值为处理后的model
 * 
 * @author GongTengPangYi
 *
 */
public abstract class ShitDBDaoNotQuery extends ShitDBDao<ShitDBExecuteSQLNotQuery, Serializable> {
	/**
	 * model对象
	 */
	protected Serializable model;
	/**
	 * shitSQL的构造器
	 */
	protected ShitQLBuilder shitQLBuilder;
	/**
	 * 数据库连接
	 */
	protected Connection conn;

	/**
	 * 
	 * @param conn
	 */
	public ShitDBDaoNotQuery(Connection conn) {
		super();
		this.conn = conn;
		execute = new ShitDBExecuteSQLNotQuery(conn);
	}

	/**
	 * 
	 * @param conn
	 * @param model
	 */
	public ShitDBDaoNotQuery(Connection conn, Serializable model) {
		this(conn);
		this.model = model;

	}

	/**
	 * 设置操作的model
	 * 
	 * @param model
	 */
	public void setModel(Serializable model) {
		this.model = model;
	}

	@Override
	protected void setTranslator() throws ShitDBTranslateException {
		String className = model.getClass().getName();
		String packageName = className.substring(0, className.lastIndexOf("."));
		this.initShitQLBuilder();
		translator = new ShitQLTranslator(packageName, shitQLBuilder.getShitQL(), shitQLBuilder.getParamMap(), null);
	}

	@Override
	public Serializable excute() throws ShitDBExecuteException, ShitDBTranslateException {
		setTranslator();
		String sql = translator.getSql();
		if (showSql) {
			System.out.println(sql);
		}
		execute.execute(sql, translator.getParamList());
		return handleAfterExcute();
	}

	/**
	 * 初始化ShitQLBuilder
	 * 
	 * @throws ShitDBTranslateException
	 */
	protected abstract void initShitQLBuilder() throws ShitDBTranslateException;

	/**
	 * 执行完毕操作后的处理
	 * 
	 * @throws ShitDBTranslateException
	 * @throws ShitDBExecuteException
	 */
	protected abstract Serializable handleAfterExcute() throws ShitDBTranslateException, ShitDBExecuteException;

}
