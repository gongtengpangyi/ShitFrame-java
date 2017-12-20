package shit.web.loader;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import shit.helper.ShitFileHelper;
import shit.web.ShitController;
import shit.web.exception.ShitWebMappingConfigException;

/**
 * 查询配置文件配置文件支持json格式，以json格式配置的加载器，配置文件需要在classPath下
 * 
 * @author GongTengPangYi
 *
 */
public class JSONConfigWebControllerLoader implements WebControllerLoader {

	/**
	 * 默认的配置文件路径
	 */
	private static String configFilePath = "shitWebRequestMapping.json";

	/**
	 * 重新设置配置文件
	 * 
	 * @param filePath
	 */
	public static void setConfigFilePath(String filePath) {
		configFilePath = filePath;
	}

	/**
	 * 配置的JSON
	 */
	private JSONObject configJSON;

	/**
	 * 控制器存储表
	 */
	private Map<String, ShitController> controllerMap;

	/**
	 * 构造函数
	 */
	public JSONConfigWebControllerLoader() {
		super();
		controllerMap = new HashMap<>();
		parseJSON();
	}

	/**
	 * 解析JSON
	 */
	private void parseJSON() {
		String realPath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + configFilePath;
		String str = new String(ShitFileHelper.read(realPath));
		configJSON = JSONObject.fromObject(str);
	}

	@Override
	public ShitController load(String name) throws ShitWebMappingConfigException {
		ShitController controller = controllerMap.get(name);
		if (controller == null) {
			try {
				Class<?> controllerClass = Class.forName(configJSON.get(name).toString());
				controller = (ShitController) controllerClass.newInstance();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
				throw new ShitWebMappingConfigException("配置出错：" + name);
			}
		}
		return controller;
	}

}
