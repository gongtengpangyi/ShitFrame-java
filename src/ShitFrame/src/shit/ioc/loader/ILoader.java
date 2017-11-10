package shit.ioc.loader;

import shit.ioc.exception.ShitIocClassNotFoundException;

/**
 * 类加载器
 * @author GongTengPangYi
 *
 */
public interface ILoader {
	
	/**
	 * 加载类
	 * @param className 类名
	 * @return 类
	 * @throws ShitIocClassNotFoundException 
	 */
	public Class<?> load(String className) throws ShitIocClassNotFoundException;
}
