package shit.ioc.xml;

import org.dom4j.Element;

import shit.ioc.bean.Bean;
import shit.ioc.bean.ConstructorArg;
import shit.ioc.bean.Value;
import shit.ioc.exception.ShitIocConfigException;

public class ConstructorArgParser implements ElementParser {

	Bean bean;

	public void setBean(Bean bean) {
		this.bean = bean;
	}

	@Override
	public void parse(Element element) throws ShitIocConfigException {
		if (bean == null) {
			return;
		}
		ConstructorArg constructorArg = new ConstructorArg();
		Element valueElement = element.element("value");
		if (valueElement == null) {
			throw new ShitIocConfigException("ConstructorArg必须配置value子标签");
		}
		Value value = new Value();
		value.setValue(valueElement.getText());
		constructorArg.setValue(value);
		constructorArg.setType(element.attributeValue("type"));
		bean.addConstructorArg(constructorArg);
	}

}
