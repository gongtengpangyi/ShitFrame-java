package shit.ioc.bean;

/**
 * set函数注入
 * 
 * @author GongTengPangYi
 *
 */
public class Property extends Document {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8367000822046594009L;

	/**
	 * 所属的bean
	 */
	private Bean belongBean;
	/**
	 * 注入的name
	 */
	private String name;
	/**
	 * ref标签对应的bean的id
	 */
	private String refBeanId;

	public Property(Bean belongBean) {
		super();
		this.belongBean = belongBean;
	}

	public Bean getBelongBean() {
		return belongBean;
	}

	public void setBelongBean(Bean belongBean) {
		this.belongBean = belongBean;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRefBeanId() {
		return refBeanId;
	}

	public void setRefBeanId(String refBeanId) {
		this.refBeanId = refBeanId;
	}

}
