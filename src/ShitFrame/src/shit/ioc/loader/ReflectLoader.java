package shit.ioc.loader;

import shit.ioc.exception.ShitIocClassNotFoundException;

/**
 * 基本类加载器
 * @author GongTengPangYi
 *
 */
public class ReflectLoader implements ILoader {

	@Override
	public Class<?> load(String className) throws ShitIocClassNotFoundException {
		Class<?> clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new ShitIocClassNotFoundException("没有找到改类：" + className, e);
		}
		return clazz;
	}

}
