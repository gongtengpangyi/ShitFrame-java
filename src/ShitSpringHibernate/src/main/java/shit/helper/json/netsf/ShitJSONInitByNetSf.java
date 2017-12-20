package shit.helper.json.netsf;

import shit.helper.ShitJSONHelper;
import shit.helper.json.ShitJSONInit;

/**
 * 更据net.sf.json包来完成驱动
 * 
 * @author GongTengPangYi 依赖jar包：commons-beanutils, commons-collections,
 *         commons-lang, commons-logging, json-lib-jdk, ezmorph
 */
public class ShitJSONInitByNetSf implements ShitJSONInit<ShitJSONObjectByNetSf, ShitJSONArrayByNetSf> {

	static {
		ShitJSONHelper.driver = new ShitJSONInitByNetSf();
	}

	@Override
	public ShitJSONObjectByNetSf initJSONObject() {
		return new ShitJSONObjectByNetSf();
	}

	@Override
	public ShitJSONArrayByNetSf initJSONArray() {
		return new ShitJSONArrayByNetSf();
	}

}
