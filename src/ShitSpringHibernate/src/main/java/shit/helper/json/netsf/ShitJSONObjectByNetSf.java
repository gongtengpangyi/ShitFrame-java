package shit.helper.json.netsf;

import net.sf.json.JSONObject;
import shit.helper.json.ShitJSONObject;

/**
 * 更据net.sf.json包来完成JSONObject
 * 
 * @author GongTengPangYi 依赖jar包：commons-beanutils, commons-collections,
 *         commons-lang, commons-logging, json-lib-jdk, ezmorph
 */
public class ShitJSONObjectByNetSf extends ShitJSONObject<JSONObject> {
	/*
	 * 不用JSONObject来from的属性类型
	 */
	private static final String TYPES = "java.lang.String; java.lang.Integer; "
			+ "java.lang.Long; java.lang.Float; java.lang.Boolean;";

	public ShitJSONObjectByNetSf() {
		super();
		jsonObject = new JSONObject();
	}

	@Override
	public void put(String key, Object obj) {
		try {
			if (TYPES.indexOf(obj.getClass().getName()) >= 0) {
				jsonObject.put(key, obj);
			} else {
				jsonObject.put(key, JSONObject.fromObject(obj));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object get(String key) {
		return jsonObject.get(key);
	}

	@Override
	public void remove(String key) {
		jsonObject.remove(key);
	}

	@Override
	public String toString() {
		return jsonObject.toString();
	}

	@Override
	public Object toBean(Class<?> beanClass) {
		return JSONObject.toBean(jsonObject, beanClass);
	}

	@Override
	protected void fromObject1(Object obj) {
		jsonObject = JSONObject.fromObject(obj);
	}

}
