package shit.ioc.xml;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import shit.ioc.exception.ShitIocConfigException;

public class XmlParserDom4j extends XmlParser {
	

	/**
	 * 节点解析器
	 */
	public static Map<String, ElementParser> elementParserMap = new HashMap<>();
		
	/**
	 * beans节点
	 */
	private Element beansElement;
	
	public XmlParserDom4j(BeanPutter beanPutter) {
		super(beanPutter);
		elementParserMap.put("import", new ImportParser(this));
		elementParserMap.put("bean", new BeanParser(beanPutter));
	}
	
	@Override
	public void parse(String xmlPath) throws ShitIocConfigException {
		/**
		 * 先读取XML，这个没啥好说的
		 */
		SAXReader reader = new SAXReader();
		URI filePath = null;
		try {
			filePath = this.getClass().getResource(xmlPath).toURI();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		File file = new File(filePath);
		Document document;
		try {
			document = reader.read(file);
			/**
			 * 到目前为止都是读取XML文件并转换为对应的DOM对象，然后开始解析
			 */
			// 获取根节点，即beans节点
			beansElement = document.getRootElement();
			// 获取节点列表
			@SuppressWarnings("unchecked")
			List<Element> elementList = beansElement.elements();
			for (Element element : elementList) {
				// 遍历并添加在所有的
				ElementParser parser = elementParserMap.get(element.getName());
				if (parser != null) {
					parser.parse(element);
				}
			}
		} catch(DocumentException e) {
			e.printStackTrace();
		}
	}
	
}
