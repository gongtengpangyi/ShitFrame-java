package shit.toolspring;

import java.util.List;
import java.util.Map;

/**
 * biz层基类，将dao已经封装完善的公共方法进行声明实现
 * 
 * @author GongTengPangYi
 *
 * @param <T>
 *            model泛型
 */
public interface BaseBiz<T extends Entity> {

	public abstract void setDao(BaseDao<T, Long> dao);

	public abstract List<T> findAll();

	public abstract void update(T t);

	public abstract void save(T t);

	public abstract void delete(T model);

	public abstract void falseDelete(T model);

	public abstract List<T> query(T model, Pager pager);

	public abstract T findById(Long id);

	public abstract Boolean checkExist(String objectName, String name, Object value);

	public abstract Boolean checkExist(String objectName, String name1, Object value1, String name2, Object value2);

	public abstract int update(String queryString);

	public abstract List<T> query(String hql, Map<String, Object> params, Pager pager);

	public abstract void close();
}