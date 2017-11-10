package shit.ioc.bean;


/**
 * 构造函数参数
 * @author GongTengPangYi
 *
 */
public class ConstructorArg extends Document{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8464694242556489746L;
	
	/**
	 * 所属的控制器
	 */
	private Constructor constructor;
	
	/**
	 * 参数类型
	 */
	private String type;
	
	/**
	 * 参数值
	 */
	private Value value;

	public Constructor getConstructor() {
		return constructor;
	}

	public void setConstructor(Constructor constructor) {
		this.constructor = constructor;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Value getValue() {
		return value;
	}

	public void setValue(Value value) {
		this.value = value;
	}
	
	

}
