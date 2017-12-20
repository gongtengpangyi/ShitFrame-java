package shit.helper.json;

/**
 * JSON的线性数组
 * 
 * @author GongTengPangYi
 *
 * @param <T>
 *            JSONArray
 */
public abstract class ShitJSONArray<T> {

	/**
	 * JSONArray类对象
	 */
	protected T jsonArray;

	/**
	 * 获取JSON数组对象
	 * 
	 * @return
	 */
	public T getJSONArray() {
		return jsonArray;
	}

	/**
	 * 设置JSON数组对象
	 * 
	 * @param jsonArray
	 */
	public void setJSONArray(T jsonArray) {
		this.jsonArray = jsonArray;
	}

	/**
	 * 添加数组的值
	 * 
	 * @param value
	 */
	public abstract void add(Object value);

	/**
	 * 获取对应字符串
	 */
	public abstract String toString();
}
