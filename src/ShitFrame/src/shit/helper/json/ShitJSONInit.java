package shit.helper.json;

/**
 * JSON初始化，该类用作选择JSON元素类的具体实现，即驱动器
 * 
 * @author GongTengPangYi
 *
 */
public interface ShitJSONInit<T extends ShitJSONObject<?>, E extends ShitJSONArray<?>> {
	/**
	 * 初始化JSONObject
	 * 
	 * @return
	 */
	public T initJSONObject();

	/**
	 * 初始化JSONArray
	 * 
	 * @return
	 */
	public E initJSONArray();
}
