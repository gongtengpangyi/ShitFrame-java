package shit.ioc.xml;

import org.dom4j.Element;

import shit.ioc.bean.Bean;
import shit.ioc.bean.Property;

public class PropertyParser implements ElementParser {

	Bean bean;
	
	public void setBean(Bean bean) {
		this.bean = bean;
	}

	@Override
	public void parse(Element element) {
		if (bean == null) {
			return;
		}
		Property property = new Property(bean);
		property.setName(element.attributeValue("name"));
		property.setRefBeanId(element.attributeValue("ref"));
		bean.addProperty(property);
	}

}
