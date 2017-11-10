package shit.ioc.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 构造函数
 * 
 * @author GongTengPangYi
 *
 */
public class Constructor extends Document {

	/**
	 * 
	 */
	private static final long serialVersionUID = -774455428597592166L;

	/**
	 * 所在的bean对象
	 */
	private Bean belongBean;

	/**
	 * 拥有的构造函数参数
	 */
	private List<ConstructorArg> args;

	/**
	 * 基本构造器
	 * 
	 * @param belongBean
	 */
	public Constructor(Bean belongBean) {
		super();
		this.belongBean = belongBean;
		this.args = new ArrayList<ConstructorArg>();
	}

	public Bean getBelongBean() {
		return belongBean;
	}

	public void setBelongBean(Bean belongBean) {
		this.belongBean = belongBean;
	}

	public List<ConstructorArg> getArgs() {
		return args;
	}

	public void setArgs(List<ConstructorArg> args) {
		this.args = args;
	}

	public void addArg(ConstructorArg arg) {
		args.add(arg);
	}

}
