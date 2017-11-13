package shit.ioc.xml;

import shit.ioc.exception.ShitIocConfigException;

public abstract class XmlParser {
	
	protected BeanPutter beanPutter;

	public XmlParser(BeanPutter beanPutter) {
		super();
		this.beanPutter = beanPutter;
	}

	/**
	 * 解析XML
	 * @param xmlPath
	 * @throws ShitIocConfigException 
	 */
	public abstract void parse(String xmlPath) throws ShitIocConfigException;
}
