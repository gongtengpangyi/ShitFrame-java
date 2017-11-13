package shit.ioc.bean;

/**
 * IOC配置文件的Bean元素
 * 
 * @author GongTengPangYi
 *
 */
public class Bean extends Document{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1390894679238736016L;
	/**
	 * bean的id
	 */
	private String id;
	/**
	 * bean所对应的class的className
	 */
	private String className;
	/**
	 * 用于生成此bean的构造函数
	 */
	private Constructor constructor;
	/**
	 * 用于set注入的数据集合
	 */
	private Properties properies;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Constructor getConstructor() {
		return constructor;
	}

	public void setConstructor(Constructor constructor) {
		this.constructor = constructor;
	}

	public Properties getProperies() {
		return properies;
	}

	public void setProperies(Properties properies) {
		this.properies = properies;
	}

	public void addConstructorArg(ConstructorArg constructorArg) {
		if (constructor == null) {
			constructor = new Constructor(this);
		}
		constructorArg.setConstructor(constructor);
		constructor.addArg(constructorArg);
	}
	
	public void addProperty(Property property) {
		if (properies == null) {
			properies = new Properties(this);
		}
		properies.addProperty(property);
	}

}
