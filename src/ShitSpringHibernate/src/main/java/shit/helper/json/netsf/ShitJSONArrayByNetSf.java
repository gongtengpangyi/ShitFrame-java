package shit.helper.json.netsf;

import net.sf.json.JSONArray;
import shit.helper.json.ShitJSONArray;

/**
 * 更据net.sf.json包来完成JSONArray
 * 
 * @author GongTengPangYi 依赖jar包：commons-beanutils, commons-collections,
 *         commons-lang, commons-logging, json-lib-jdk, ezmorph
 */
public class ShitJSONArrayByNetSf extends ShitJSONArray<JSONArray> {

	public ShitJSONArrayByNetSf() {
		super();
		jsonArray = new JSONArray();
	}

	@Override
	public void add(Object value) {
		if (value instanceof ShitJSONObjectByNetSf) {
			jsonArray.add(((ShitJSONObjectByNetSf) value).getJSONObject());
		} else {
			jsonArray.add(value);
		}
	}

	@Override
	public String toString() {
		return jsonArray.toString();
	}

}
