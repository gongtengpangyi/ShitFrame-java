package shit.ioc.xml;

import org.dom4j.Element;

import shit.ioc.exception.ShitIocConfigException;

public class ImportParser implements ElementParser {

	XmlParser xmlParser;
	
	public ImportParser(XmlParser xmlParser) {
		super();
		this.xmlParser = xmlParser;
	}

	@Override
	public void parse(Element element) throws ShitIocConfigException {
		xmlParser.parse(element.attributeValue("src"));
	}

}
