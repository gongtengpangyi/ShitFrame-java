package shit.ioc.impl;

import shit.helper.ShitReflectException;
import shit.ioc.ShitIoc;
import shit.ioc.bean.Bean;
import shit.ioc.container.BeanContainer;
import shit.ioc.container.IocContainer;
import shit.ioc.exception.ShitIocClassNotFoundException;
import shit.ioc.exception.ShitIocConfigException;
import shit.ioc.xml.BeanPutter;
import shit.ioc.xml.XmlParser;
import shit.ioc.xml.XmlParserDom4j;

/**
 * 默认的IOC接口实现类
 * @author GongTengPangYi
 *
 */
public class ShitStandardIoc extends ShitIoc {
	/**
	 * xml解析器
	 */
	private XmlParser xmlParser;
	
	private IocContainer container;
	
	/**
	 * bean存放器
	 */
	private BeanPutter beanPutter = new BeanPutter() {
		
		@Override
		public void putBean(String id, Bean bean) {
			if (isBeanPut(id)) {
				return;
			}
			BeanContainer beanContainer = new BeanContainer(bean, container);
			container.putBeanContainer(id, beanContainer);
		}
		
		@Override
		public boolean isBeanPut(String id) {
			return container.getBeanContainer(id) != null;
		}
	};

	public ShitStandardIoc(String xmlFilePath) throws ShitIocConfigException {
		super(xmlFilePath);
		container = new IocContainer();
		xmlParser = new XmlParserDom4j(beanPutter);
	}

	@Override
	protected void parseXml() throws ShitIocConfigException {
		xmlParser.parse(xmlFilePath);
	}

	@Override
	public Object getBean(String id) throws ShitIocClassNotFoundException, ShitReflectException {
		BeanContainer beanContainer = container.getBeanContainer(id);
		return beanContainer == null ? null : beanContainer.getInstance();
	}

}
