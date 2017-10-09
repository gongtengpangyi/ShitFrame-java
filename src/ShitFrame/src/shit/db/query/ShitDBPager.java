package shit.db.query;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import shit.db.exception.ShitDBJDBCException;
import shit.db.exception.ShitDBWrongControlException;

/**
 * 分页信息
 * 
 * @author GongTengPangYi
 *
 */
public class ShitDBPager {
	/**
	 * 页长度
	 */
	private Integer pagerSize;

	/**
	 * 查询页码
	 */
	private Integer pagerIndex;

	/**
	 * 查询体
	 */
	private ShitDBQuery query;

	/**
	 * 设置查询体
	 * 
	 * @param query
	 *            查询
	 */
	protected void setQuery(ShitDBQuery query) {
		this.query = query;
	}

	/**
	 * 基本构造函数
	 */
	public ShitDBPager() {
		super();
	}

	/**
	 * 带参数构造函数
	 * 
	 * @param pagerSize
	 *            页长度
	 * @param pagerIndex
	 *            页码
	 */
	public ShitDBPager(Integer pagerSize, Integer pagerIndex) {
		super();
		this.pagerSize = pagerSize;
		this.pagerIndex = pagerIndex;
	}

	/**
	 * 获取页长度
	 * 
	 * @return
	 */
	public Integer getPagerSize() {
		return pagerSize != null ? pagerSize : 0;
	}

	/**
	 * 设置页长度,从1开始计数
	 * 
	 * @param pagerSize
	 */
	public void setPagerSize(Integer pagerSize) {
		this.pagerSize = pagerSize;
	}

	/**
	 * 获取页码
	 * 
	 * @return
	 */
	public Integer getPagerIndex() {
		return pagerIndex != null ? pagerIndex : 1;
	}

	/**
	 * 设置页码
	 * 
	 * @param pagerIndex
	 */
	public void setPagerIndex(Integer pagerIndex) {
		this.pagerIndex = pagerIndex;
	}

	/**
	 * 查询
	 * 
	 * @return 查询结果
	 * @throws ShitDBWrongControlException
	 *             框架错误操作异常
	 * @throws ShitDBJDBCException
	 *             数据库异常
	 */
	public List<Serializable> query() throws ShitDBWrongControlException, ShitDBJDBCException {
		if (query != null) {
			return query.query();
		} else {
			throw new ShitDBWrongControlException("分页操作没有可执行的query");
		}
	}

	/**
	 * 根据shitQL语句查询
	 * 
	 * @param shitQL
	 *            shitQL语句
	 * @return 查询结果
	 * @throws ShitDBWrongControlException
	 *             框架错误操作异常
	 * @throws ShitDBJDBCException
	 *             数据库异常
	 */
	public List<Serializable> query(String shitQL) throws ShitDBJDBCException, ShitDBWrongControlException {
		if (query != null) {
			query.setShitQL(shitQL);
			return query.query();
		} else {
			throw new ShitDBWrongControlException("分页操作没有可执行的query");
		}
	}

	/**
	 * 根据shitQL语句查询
	 * 
	 * @param shitQL
	 *            shitQL语句
	 * @param params
	 *            占位参数
	 * @return 查询结果
	 * @throws ShitDBWrongControlException
	 *             框架错误操作异常
	 * @throws ShitDBJDBCException
	 *             数据库异常
	 */
	public List<Serializable> query(String shitQL, Map<String, Serializable> params)
			throws ShitDBJDBCException, ShitDBWrongControlException {
		if (query != null) {
			query.setShitQL(shitQL);
			query.setParams(params);
			return query.query();
		} else {
			throw new ShitDBWrongControlException("分页操作没有可执行的query");
		}
	}

}
