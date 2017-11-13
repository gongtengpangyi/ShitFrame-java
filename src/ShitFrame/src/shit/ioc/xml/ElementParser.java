package shit.ioc.xml;

import org.dom4j.Element;

import shit.ioc.exception.ShitIocConfigException;

/**
 * 节点解析器
 * @author GongTengPangYi
 *
 */
public interface ElementParser {

	public void parse(Element element) throws ShitIocConfigException;
}
