package shit.db.query;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import shit.db.exception.ShitDBJDBCException;
import shit.db.exception.ShitDBWrongControlException;

/**
 * 执行查询过程
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
	 * 无参数构造
	 */
	public ShitDBQuery() {
		super();
	}

	/**
	 * 根据ShitQL语句构造
	 * 
	 * @param shitQL
	 */
	public ShitDBQuery(String shitQL) {
		super();
		this.shitQL = shitQL;
	}

	/**
	 * 根据ShitQL语句和分页信息构造
	 * 
	 * @param shitQL
	 * @param pager
	 */
	public ShitDBQuery(String shitQL, ShitDBPager pager) {
		super();
		this.shitQL = shitQL;
		this.pager = pager;
	}

	/**
	 * 根据ShitQL语句、分页信息、占位符构造
	 * 
	 * @param shitQL
	 * @param pager
	 * @param params
	 */
	public ShitDBQuery(String shitQL, ShitDBPager pager, Map<String, Serializable> params) {
		super();
		this.shitQL = shitQL;
		this.pager = pager;
		this.params = params;
	}

	/**
	 * 根据ShitQL语句、占位符构造
	 * 
	 * @param shitQL
	 * @param params
	 */
	public ShitDBQuery(String shitQL, Map<String, Serializable> params) {
		super();
		this.shitQL = shitQL;
		this.params = params;
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
	public ShitDBPager getPager(Integer pagerSize, Integer pagerIndex) {
		this.setPager(new ShitDBPager(pagerSize, pagerIndex));
		pager.setQuery(this);
		return pager;
	}

	/**
	 * 执行查询
	 * 
	 * @return 查询结果
	 * @throws ShitDBJDBCException
	 *             数据库异常
	 * @throws ShitDBWrongControlException
	 *             框架错误操作异常
	 */
	public abstract List<Serializable> query() throws ShitDBJDBCException, ShitDBWrongControlException;

}
