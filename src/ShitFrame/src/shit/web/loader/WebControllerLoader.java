package shit.web.loader;

import shit.web.ShitController;
import shit.web.exception.ShitWebMappingConfigException;

/**
 * web控制器的加载器接口，用于在接收请求的时候映射相应控制器，可以随意扩展加载器的实现方式，以达到用不同手段实现加载的效果，并在web.
 * xml配置中加上参数： <context-param> <param-name>shitWebControllerLoaderClass</param-name>
 * <param-value>shit.web.loader.JSONConfigWebControllerLoader</param-value>
 * </context-param> 参数的意义在于声明实现加载的类，如果不声明则使用默认加载器加载
 * 
 * @author GongTengPangYi
 *
 */
public interface WebControllerLoader {

	/**
	 * 加载对应name的controller
	 * 
	 * @param name
	 *            controller的name
	 * @return controller
	 * @throws ShitWebMappingConfigException 
	 */
	public ShitController load(String name) throws ShitWebMappingConfigException;
}
