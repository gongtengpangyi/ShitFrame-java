package shit.ioc.bean;

/**
 * value标签
 * 
 * @author GongTengPangYi
 *
 */
public class Value extends Document {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4912549096000599880L;

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public Object getRealValue() {
		//TODO:
		return value;
	}
}
