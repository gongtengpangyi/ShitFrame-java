package shit.toolspring;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 数据库公共操作接口
 * 
 * @author GongTengPangYi
 *
 * @param <T>
 *            model类的泛型
 * @param <E>
 *            model类主键的泛型
 */
public interface BaseDao<T extends Serializable, E extends Serializable> {

	/**
	 * 存或更新
	 * 
	 * @param model
	 */
	void saveOrUpdate(T model);

	/**
	 * 存
	 * 
	 * @param model
	 */
	void save(T model);

	/**
	 * 更新
	 * 
	 * @param model
	 */
	void update(T model);

	/**
	 * 删除
	 * 
	 * @param model
	 */
	void delete(T model);

	/**
	 * 根据hql语句删除
	 * 
	 * @param hql
	 */
	void delete(String hql);

	/**
	 * 根据id查找
	 * 
	 * @param pk
	 * @return
	 */
	T findById(E pk);

	/**
	 * 查询所有
	 * 
	 * @return
	 */
	List<T> findAll();

	/**
	 * 根据hql语句查询
	 * 
	 * @param hql
	 * @return
	 */
	List<T> findByHQL(String hql);

	/**
	 * 分页查询
	 * 
	 * @param pager
	 * @param hql
	 * @return
	 */
	List<T> pageableFindByHQL(Pager pager, String hql);

	/**
	 * 占位符查询
	 * 
	 * @param hql
	 * @param queryParams
	 * @return
	 */
	List<T> findByHQL(String hql, Map<String, Object> queryParams);

	/**
	 * 占位符分页查询
	 * 
	 * @param pager
	 * @param hql
	 * @param queryParams
	 * @return
	 */
	List<T> pageableFindByHQL(Pager pager, String hql, Map<String, Object> queryParams);

	/**
	 * 通过一个实例来找
	 * 
	 * @param example
	 * @return
	 */
	List<T> findByExample(T example, Pager pager);

	Object uniqueQuery(String hql);

	int update(String queryString);

}
