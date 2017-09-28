package shit.helper.json;

/**
 * JSON初始化
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
