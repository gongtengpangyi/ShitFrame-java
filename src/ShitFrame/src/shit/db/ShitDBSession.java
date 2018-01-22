package shit.db;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import shit.db.exception.ShitDBConfigureException;
import shit.db.exception.ShitDBExecuteException;
import shit.db.exception.ShitDBJDBCException;
import shit.db.exception.ShitDBTranslateException;
import shit.db.exception.ShitDBWrongControlException;
import shit.db.query.ShitDBPager;
import shit.db.query.ShitDBQuery;

/**
 * 用于处理数据库操作的会话接口
 * 
 * @author GongTengPangYi
 *
 */
public interface ShitDBSession {

	/**
	 * 存储model类对象
	 * 
	 * @param model
	 *            model对象，必须实现Serializable接口
	 * @return 存储后的主键
	 * @throws ShitDBTranslateException
	 * @throws ShitDBConfigureException
	 */
	public Serializable save(Serializable model) throws ShitDBExecuteException, ShitDBConfigureException;

	/**
	 * 更新model类对象
	 * 
	 * @param model
	 *            model对象，必须实现Serializable接口
	 * @return 更新后的主键
	 * @throws ShitDBTranslateException
	 * @throws ShitDBConfigureException
	 */
	public Serializable update(Serializable model) throws ShitDBExecuteException, ShitDBConfigureException;

	/**
	 * 存储或更新model类对象
	 * 
	 * @param model
	 *            model对象，必须实现Serializable接口
	 * @return 存储或更新后的主键
	 * @throws ShitDBConfigureException
	 * @throws ShitDBExecuteException
	 */
	public Serializable saveOrUpdate(Serializable model) throws ShitDBConfigureException, ShitDBExecuteException;

	/**
	 * 执行查询语句
	 * 
	 * @param clazz
	 *            查询的结果类
	 * @param shitQL
	 *            shitQL语句
	 * @return 查询结果
	 * @throws ShitDBTranslateException
	 * @throws ShitDBConfigureException
	 * @throws ShitDBWrongControlException
	 * @throws ShitDBJDBCException
	 */
	public List<? extends Serializable> query(Class<? extends Serializable> clazz, String shitQL)
			throws ShitDBJDBCException, ShitDBWrongControlException, ShitDBConfigureException, ShitDBTranslateException;

	/**
	 * 执行带占位符的查询语句
	 * 
	 * @param clazz
	 *            查询的结果类
	 * @param shitQL
	 *            shitQL语句
	 * @param params
	 *            占位符参数键值对
	 * @return 查询结果
	 * @throws ShitDBTranslateException
	 * @throws ShitDBConfigureException
	 * @throws ShitDBWrongControlException
	 * @throws ShitDBJDBCException
	 */
	public List<? extends Serializable> query(Class<? extends Serializable> clazz, String shitQL,
			Map<String, Serializable> params) throws ShitDBJDBCException, ShitDBWrongControlException,
					ShitDBConfigureException, ShitDBTranslateException;

	/**
	 * 分页执行查询语句
	 * 
	 * @param clazz
	 *            查询的结果类
	 * @param shitQL
	 *            shitQL语句
	 * @param pager
	 *            分页信息
	 * @return 查询结果
	 * @throws ShitDBTranslateException
	 * @throws ShitDBConfigureException
	 * @throws ShitDBWrongControlException
	 * @throws ShitDBJDBCException
	 */
	public List<? extends Serializable> query(Class<? extends Serializable> clazz, String shitQL, ShitDBPager pager)
			throws ShitDBJDBCException, ShitDBWrongControlException, ShitDBConfigureException, ShitDBTranslateException;

	/**
	 * 分页执行带占位符的查询
	 * 
	 * @param clazz
	 *            查询的结果类
	 * @param shitQL
	 *            shitQL语句
	 * @param params
	 *            占位符参数键值对
	 * @param pager
	 *            分页信息
	 * @return 查询结果
	 */
	public List<? extends Serializable> query(Class<? extends Serializable> clazz, String shitQL,
			Map<String, Serializable> params, ShitDBPager pager) throws ShitDBJDBCException,
					ShitDBWrongControlException, ShitDBConfigureException, ShitDBTranslateException;

	/**
	 * 获取查询体
	 * 
	 * @param clazz
	 *            查询的结果类
	 * @return 获取查询体
	 */
	public ShitDBQuery query(Class<? extends Serializable> clazz);

	/**
	 * 查询所有结果
	 * 
	 * @param clazz
	 *            查询的结果类
	 * 
	 * @return 所有结果
	 * @throws ShitDBTranslateException
	 * @throws ShitDBConfigureException
	 * @throws ShitDBWrongControlException
	 * @throws ShitDBJDBCException
	 */
	public List<? extends Serializable> findAll(Class<? extends Serializable> clazz)
			throws ShitDBJDBCException, ShitDBWrongControlException, ShitDBConfigureException, ShitDBTranslateException;

	/**
	 * 根据主键查询
	 * 
	 * @param clazz
	 *            查询的结果类
	 * @param id
	 *            主键值
	 * @return 查询结果
	 * @throws ShitDBConfigureException
	 * @throws ShitDBWrongControlException
	 * @throws ShitDBJDBCException
	 */
	public Serializable findById(Class<? extends Serializable> clazz, Serializable id)
			throws ShitDBConfigureException, ShitDBJDBCException, ShitDBWrongControlException;

	/**
	 * 删除某个model
	 * 
	 * @param model
	 *            model对象
	 * @throws ShitDBExecuteException
	 * @throws ShitDBConfigureException
	 */
	public void delete(Serializable model) throws ShitDBExecuteException, ShitDBConfigureException;

}
