package shit.helper.json;

import shit.helper.ShitJSONHelper;

/**
 * JSON单元素类
 * 
 * @author GongTengPangYi
 *
 * @param <T>
 *            基本JSON单元类
 */

public abstract class ShitJSONObject<T> {

	/**
	 * 内部实际操作的元素
	 */
	protected T jsonObject;

	public ShitJSONObject() {
		super();
	}

	/**
	 * 获取实际JSON单元素操作对象
	 * 
	 * @return
	 */
	public T getJSONObject() {
		return jsonObject;
	}

	/**
	 * 塞入实际JSON单元素操作对象
	 * 
	 * @param jsonObject
	 */
	public void setJSONObject(T jsonObject) {
		this.jsonObject = jsonObject;
	}

	/**
	 * 填放元素
	 * 
	 * @param key
	 *            键
	 * @param obj
	 *            值
	 */
	public abstract void put(String key, Object obj);

	/**
	 * 获取某个键值的值
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public abstract Object get(String key);

	/**
	 * 删除某个键的值
	 * 
	 * @param key
	 *            键
	 */
	public abstract void remove(String key);

	/**
	 * 获取其字符串的值
	 */
	public abstract String toString();

	/**
	 * 生成对应的Object
	 * 
	 * @param beanClass
	 *            生成的Object的类
	 * @return 生成的对象
	 */
	public abstract Object toBean(Class<?> beanClass);

	/**
	 * 对象转为JSON
	 * 
	 * @param obj
	 */
	protected abstract void fromObject1(Object obj);

	/**
	 * 对象转为JSON
	 * 
	 * @param object
	 * @return
	 */
	public static ShitJSONObject<?> fromObject(Object object) {
		ShitJSONObject<?> json = ShitJSONHelper.driver.initJSONObject();
		json.fromObject1(object);
		return json;
	}
}
