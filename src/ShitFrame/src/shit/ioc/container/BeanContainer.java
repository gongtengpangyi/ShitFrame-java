package shit.ioc.container;

import java.io.Serializable;
import java.util.List;

import shit.helper.ShitReflectException;
import shit.helper.ShitReflectHelper;
import shit.ioc.bean.Bean;
import shit.ioc.bean.Constructor;
import shit.ioc.bean.ConstructorArg;
import shit.ioc.bean.Properties;
import shit.ioc.bean.Property;
import shit.ioc.exception.ShitIocClassNotFoundException;
import shit.ioc.loader.ILoader;
import shit.ioc.loader.ReflectLoader;

/**
 * 单个bean的容器
 * 
 * @author GongTengPangYi
 *
 */
public class BeanContainer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6264125600594569300L;

	private IocContainer container;

	private ILoader loader = new ReflectLoader();

	private Bean bean;

	private Object instance;

	public BeanContainer(Bean bean, IocContainer container) {
		super();
		this.bean = bean;
		this.container = container;
	}

	/**
	 * 获取bean中的对象，对象单例化
	 * 
	 * @return
	 * @throws ShitIocClassNotFoundException
	 * @throws ShitReflectException
	 */
	public Object getInstance() throws ShitIocClassNotFoundException, ShitReflectException {
		if (instance == null) {
			synchronized (instance) {
				instance = instance();
			}
		}
		return instance;
	}

	/**
	 * 实现类的初始化
	 * 
	 * @return
	 * @throws ShitIocClassNotFoundException
	 * @throws ShitReflectException
	 */
	private Object instance() throws ShitIocClassNotFoundException, ShitReflectException {
		Object beanObj = null;

		/**
		 * 获取类
		 */
		Class<?> clazz = loader.load(bean.getClassName());
		Constructor constructor = bean.getConstructor();
		Class<?>[] parameterTypes = new Class<?>[0];
		Object[] params = new Object[0];
		if (constructor != null) {
			/**
			 * 根据构造函数信息获取对象
			 */
			List<ConstructorArg> args = constructor.getArgs();
			int length = args == null ? 0 : args.size();
			parameterTypes = new Class<?>[length];
			params = new Object[length];
			for (int i = 0; i < length; i++) {
				ConstructorArg arg = args.get(i);
				parameterTypes[i] = loader.load(arg.getType());
				params[i] = arg.getValue().getRealValue();
			}
		}
		beanObj = ShitReflectHelper.newInstance(clazz, parameterTypes, params);

		/**
		 * 设置注入
		 */
		Properties properties = bean.getProperies();
		List<Property> propertyList = properties == null ? null : properties.getProperties();
		if (propertyList != null) {
			for (Property property : propertyList) {
				Object propertyObj = container.getBeanContainer(property.getRefBeanId()).getInstance();
				ShitReflectHelper.setNotStrCastValue(beanObj, property.getName(), propertyObj, true);
			}
		}

		return beanObj;
	}

}
