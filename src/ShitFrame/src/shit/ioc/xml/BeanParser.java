package shit.ioc.xml;

import java.util.List;

import org.dom4j.Element;

import shit.ioc.bean.Bean;
import shit.ioc.exception.ShitIocConfigException;

/**
 * Bean字段解析器
 * @author GongTengPangYi
 *
 */
public class BeanParser implements ElementParser {

	private BeanPutter beanPutter;
	
	private ConstructorArgParser constructorArgParser;
	private PropertyParser propertyParser;
	
	public BeanParser(BeanPutter beanPutter) {
		super();
		this.beanPutter = beanPutter;
		constructorArgParser = new ConstructorArgParser();
		propertyParser = new PropertyParser();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void parse(Element element) throws ShitIocConfigException {
		String id = element.attributeValue("id");
		if (beanPutter.isBeanPut(id)) {
			return;
		}
		Bean bean = new Bean();
		bean.setId(id);
		bean.setClassName(element.attributeValue("class"));
		constructorArgParser.setBean(bean);
		propertyParser.setBean(bean);
		// 获取构造器参数的信息的DOM节点
		List<Element> constructorArgElements = element.elements("constructor-arg");
		if(constructorArgElements!=null && constructorArgElements.size()>0) {
			for (Element constructorArgElement : constructorArgElements) {
				/*
				 * 如果存在则遍历并且将其值添加入Constructor中
				 */
				constructorArgParser.parse(constructorArgElement);
			}
		}
		// 获取参数节点列表
		List<Element> propertyElements = element.elements("property");
		if(propertyElements!=null && propertyElements.size()>0) {
			for (Element propertyElement : propertyElements) {
				propertyParser.parse(propertyElement);
			}
		}
		this.beanPutter.putBean(id, bean);
	}

}
