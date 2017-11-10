package shit.ioc.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * set注入的集合
 * 
 * @author GongTengPangYi
 *
 */
public class Properties extends Document {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8962032423155385870L;
	/**
	 * 所属的bean
	 */
	private Bean belongBean;

	private List<Property> properties;

	/**
	 * 基本构造器
	 * 
	 * @param belongBean
	 * @param propertys
	 */
	public Properties(Bean belongBean, List<Property> propertys) {
		super();
		this.belongBean = belongBean;
		this.properties = new ArrayList<>();
	}

	public Bean getBelongBean() {
		return belongBean;
	}

	public void setBelongBean(Bean belongBean) {
		this.belongBean = belongBean;
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

	public void addProperty(Property property) {
		this.properties.add(property);
	}
}
