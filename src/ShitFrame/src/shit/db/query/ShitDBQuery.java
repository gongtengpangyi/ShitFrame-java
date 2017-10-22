package shit.db.query;

import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shit.db.exception.ShitDBConfigureException;
import shit.db.exception.ShitDBExecuteException;
import shit.db.exception.ShitDBJDBCException;
import shit.db.exception.ShitDBResultException;
import shit.db.exception.ShitDBTranslateException;
import shit.db.exception.ShitDBWrongControlException;
import shit.db.execute.ShitDBDaoQuery;
import shit.db.table.ShitDBTable;

/**
 * 执行查询过程的基类，对于查询操作，session实质调用了这个类
 * 
 * @author GongTengPangYi
 *
 */
public abstract class ShitDBQuery {
	/**
	 * 用model类的类名和变量名来替代数据库表名和字段名的SQL语句
	 */
	protected String shitQL;

	/**
	 * 分页参数
	 */
	protected ShitDBPager pager;

	/**
	 * 占位符参数
	 */
	protected Map<String, Serializable> params;

	/**
	 * 数据库连接
	 */
	protected Connection conn;

	/**
	 * 查询的实现器
	 */
	protected ShitDBDaoQuery executeQuery;

	/**
	 * model类
	 */
	protected Class<?> modelClass;
	
	/**
	 * 是否显示sql
	 */
	protected boolean showSql = false;

	/**
	 * 连接构造
	 * 
	 * @param conn
	 *            数据库连接
	 * @param modelClass
	 *            model类
	 */
	public ShitDBQuery(Connection conn, Class<?> modelClass) {
		super();
		this.conn = conn;
		this.modelClass = modelClass;
		this.executeQuery = new ShitDBDaoQuery(conn);
	}

	/**
	 * 根据ShitQL语句构造
	 * 
	 * @param conn
	 *            数据库连接
	 * @param modelClass
	 *            model类
	 * @param shitQL
	 */
	public ShitDBQuery(Connection conn, Class<?> modelClass, String shitQL) {
		this(conn, modelClass);
		this.shitQL = shitQL;
	}

	/**
	 * 根据ShitQL语句和分页信息构造
	 * 
	 * @param conn
	 *            数据库连接
	 * @param modelClass
	 *            model类
	 * @param shitQL
	 * @param pager
	 */
	public ShitDBQuery(Connection conn, Class<?> modelClass, String shitQL, ShitDBPager pager) {
		this(conn, modelClass);
		this.shitQL = shitQL;
		this.pager = pager;
	}

	/**
	 * 根据ShitQL语句、分页信息、占位符构造
	 * 
	 * @param conn
	 *            数据库连接
	 * @param modelClass
	 *            model类
	 * @param shitQL
	 * @param pager
	 * @param params
	 */
	public ShitDBQuery(Connection conn, Class<?> modelClass, String shitQL, ShitDBPager pager,
			Map<String, Serializable> params) {
		this(conn, modelClass);
		this.shitQL = shitQL;
		this.pager = pager;
		this.params = params;
	}

	/**
	 * 根据ShitQL语句、占位符构造
	 * 
	 * @param conn
	 *            数据库连接
	 * @param modelClass
	 *            model类
	 * @param shitQL
	 * @param params
	 */
	public ShitDBQuery(Connection conn, Class<?> modelClass, String shitQL, Map<String, Serializable> params) {
		this(conn, modelClass);
		this.shitQL = shitQL;
		this.params = params;
	}

	/**
	 * 设置需要查询的类
	 * 
	 * @param modelClass
	 */
	public void setModelClass(Class<?> modelClass) {
		this.modelClass = modelClass;
	}

	/**
	 * 设置ShitQL语句
	 * 
	 * @param shitQL
	 */
	public void setShitQL(String shitQL) {
		this.shitQL = shitQL;
	}

	/**
	 * 设置分页信息
	 * 
	 * @param pager
	 */
	public void setPager(ShitDBPager pager) {
		this.pager = pager;
	}

	/**
	 * 获取分页信息
	 * 
	 * @return 分页信息
	 */
	public ShitDBPager getPager() {
		return pager;
	}

	/**
	 * 设置占位符参数
	 * 
	 * @param params
	 *            参数
	 */
	public void setParams(Map<String, Serializable> params) {
		this.params = params;
	}

	/**
	 * 根据页面信息获取Pager对象
	 * 
	 * @param pagerSize
	 *            页长度
	 * @param pagerIndex
	 *            页码
	 * @return pager对象
	 */
	public void setPager(Integer pagerSize, Integer pagerIndex) {
		this.setPager(new ShitDBPager(pagerSize, pagerIndex));
	}

	/**
	 * 设置是否显示sql
	 * @param showSql
	 */
	public void setShowSql(boolean showSql) {
		this.showSql = showSql;
	}

	/**
	 * 执行查询
	 * 
	 * @return 查询结果
	 * @throws ShitDBJDBCException
	 *             数据库异常
	 * @throws ShitDBWrongControlException
	 *             框架错误操作异常
	 * @throws ShitDBTranslateException
	 *             shitQL翻译异常
	 * @throws ShitDBConfigureException
	 *             shitDB配置异常
	 */
	public abstract List<Serializable> query()
			throws ShitDBJDBCException, ShitDBWrongControlException, ShitDBConfigureException, ShitDBTranslateException;

	/**
	 * 根据id来查询
	 * 
	 * @param id
	 *            id
	 * @return 查询结果
	 * @throws ShitDBConfigureException
	 *             配置出错
	 * @throws ShitDBTranslateException
	 * @throws ShitDBExecuteException
	 * @throws ShitDBResultException
	 */
	public Serializable queryById(Serializable id)
			throws ShitDBConfigureException, ShitDBResultException, ShitDBExecuteException, ShitDBTranslateException {
		ShitDBTable dbTable = modelClass.getAnnotation(ShitDBTable.class);
		if (dbTable == null) {
			throw new ShitDBConfigureException("外键没有注释数据表");
		}
		shitQL = "select * from " + modelClass.getName() + " o where o." + dbTable.primaryKey() + "=:id";
		params = new HashMap<>(1);
		params.put("id", id);
		ShitDBResultModel result = initDBQuery();
		executeQuery.setShowSql(showSql);
		List<Serializable> list = result.analysis(executeQuery.excute());
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 初始化数据库查询
	 * 
	 * @return 结果处理器
	 */
	protected ShitDBResultModel initDBQuery() {
		executeQuery.setModelClazz(modelClass);
		executeQuery.setPager(pager);
		executeQuery.setShitQL(shitQL);
		executeQuery.setParamMap(params);
		return new ShitDBResultModel(modelClass, this);
	}
}
