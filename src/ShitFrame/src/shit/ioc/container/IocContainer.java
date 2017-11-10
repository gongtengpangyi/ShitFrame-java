package shit.ioc.container;

import java.util.HashMap;
import java.util.Map;

/**
 * 核心容器
 * @author GongTengPangYi
 *
 */
public class IocContainer {
	
	/**
	 * id和Bean容器的键值对
	 */
	private Map<String, BeanContainer> container;

	public IocContainer() {
		super();
		container = new HashMap<>();
	}
	
	public BeanContainer getBeanContainer(String id) {
		return this.container.get(id);
	}
	
	public void putBeanContainer(String id, BeanContainer beanContainer) {
		container.put(id, beanContainer);
	}
}
