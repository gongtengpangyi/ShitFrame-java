package shit.db;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import shit.db.query.ShitDBPager;

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
	 */
	public Serializable save(Serializable model);

	/**
	 * 更新model类对象
	 * 
	 * @param model
	 *            model对象，必须实现Serializable接口
	 * @return 更新后的主键
	 */
	public Serializable update(Serializable model);

	/**
	 * 存储或更新model类对象
	 * 
	 * @param model
	 *            model对象，必须实现Serializable接口
	 * @return 存储或更新后的主键
	 */
	public Serializable saveOrUpdate(Serializable model);

	/**
	 * 执行查询语句
	 * 
	 * @param shitQL
	 *            shitQL语句
	 * @return 查询结果
	 */
	public List<Serializable> query(String shitQL);

	/**
	 * 执行带占位符的查询语句
	 * 
	 * @param shitQL
	 *            shitQL语句
	 * @param params
	 *            占位符参数键值对
	 * @return 查询结果
	 */
	public List<Serializable> query(String shitQL, Map<String, Serializable> params);

	/**
	 * 分页执行查询语句
	 * 
	 * @param shitQL
	 *            shitQL语句
	 * @param pager
	 *            分页信息
	 * @return 查询结果
	 */
	public List<Serializable> query(String shitQL, ShitDBPager pager);

	/**
	 * 分页执行带占位符的查询
	 * 
	 * @param shitQL
	 *            shitQL语句
	 * @param params
	 *            占位符参数键值对
	 * @param pager
	 *            分页信息
	 * @return 查询结果
	 */
	public List<Serializable> query(String shitQL, Map<String, Serializable> params, ShitDBPager pager);

	/**
	 * 查询所有结果
	 * 
	 * @return 所有结果
	 */
	public List<Serializable> findAll();

	/**
	 * 根据主键查询
	 * 
	 * @param id
	 *            主键值
	 * @return 查询结果
	 */
	public Serializable findById(Serializable id);

	/**
	 * 根据条件语句删除
	 * 
	 * @param shitQL
	 *            shitQL语句
	 */
	public void delete(String shitQL);

	/**
	 * 删除某个model
	 * 
	 * @param model
	 *            model对象
	 */
	public void delete(Serializable model);

	/**
	 * 根据主键删除
	 * 
	 * @param id
	 *            主键
	 */
	public void deleteById(Serializable id);
}
