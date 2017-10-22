package shit.db.execute;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Map;

import shit.db.exception.ShitDBExecuteException;
import shit.db.exception.ShitDBTranslateException;
import shit.db.query.ShitDBPager;
import shit.db.sql.ShitQLTranslator;

/**
 * 查询语句的执行器，这个类比较简单，只是单纯调用了翻译器和执行器，对于处理结果是在query中再去调用结果处理器的
 * 
 * @author GongTengPangYi
 *
 */
public class ShitDBDaoQuery extends ShitDBDao<ShitDBExecuteSQLQuery, ResultSet> {
	/**
	 * 结果要生成的类
	 */
	private Class<?> modelClazz;
	/**
	 * 
	 */
	private String shitQL;
	/**
	 * 
	 */
	private Map<String, Serializable> paramMap;
	/**
	 * 
	 */
	private ShitDBPager pager;

	/**
	 * 全参数的构造器
	 * 
	 * @param conn
	 *            数据库连接
	 * @param modelClazz
	 *            model类
	 * @param shitQL
	 *            shitQL语句
	 * @param paramMap
	 *            参数键值对
	 * @param pager
	 *            分页控制器
	 * @throws ShitDBTranslateException
	 *             翻译报错
	 */
	public ShitDBDaoQuery(Connection conn, Class<?> modelClazz, String shitQL, Map<String, Serializable> paramMap,
			ShitDBPager pager) {
		super();
		this.modelClazz = modelClazz;
		this.execute = new ShitDBExecuteSQLQuery(conn);
		this.shitQL = shitQL;
		this.paramMap = paramMap;
		this.pager = pager;
	}
	
	/**
	 * 双参数的构造器
	 * @param conn
	 *            数据库连接
	 */
	public ShitDBDaoQuery(Connection conn) {
		this.execute = new ShitDBExecuteSQLQuery(conn);
	}
	
	/**
	 * 设置model类
	 * @param modelClazz
	 */
	public void setModelClazz(Class<?> modelClazz) {
		this.modelClazz = modelClazz;
	}

	/**
	 * 设置shitQL语句
	 * @param shitQL
	 */
	public void setShitQL(String shitQL) {
		this.shitQL = shitQL;
	}

	/**
	 * 设置参数键值对
	 * @param paramMap
	 */
	public void setParamMap(Map<String, Serializable> paramMap) {
		this.paramMap = paramMap;
	}

	/**
	 * 设置分页控制器
	 * @param pager
	 */
	public void setPager(ShitDBPager pager) {
		this.pager = pager;
	}

	@Override
	protected void setTranslator() throws ShitDBTranslateException {
		String className = modelClazz.getName();
		String packageName = className.substring(0, className.lastIndexOf("."));
		translator = new ShitQLTranslator(packageName, shitQL, paramMap, pager);
	}

	@Override
	public ResultSet excute() throws ShitDBExecuteException, ShitDBTranslateException {
		setTranslator();
		String sql = translator.getSql();
		if (showSql) {
			System.out.println(sql);
		}
		return execute.execute(sql, translator.getParamList());
	}

}
