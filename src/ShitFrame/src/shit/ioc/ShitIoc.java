package shit.ioc;

import shit.helper.ShitReflectException;
import shit.ioc.exception.ShitIocClassNotFoundException;
import shit.ioc.exception.ShitIocConfigException;

/**
 * IOC容器的使用类，可以在这个容器中获取IOC容器的内容，IOC容器的配置文件比较类似于Spring，
 * 其内部所有的元素均在配置文件中以bean标签定义，bean需要设置id和class，在获取时候根据id获取； 此外可以用set函数和构造函数来注入元素：
 * set函数对应property标签，且必须定义name，与set函数对应的参数名一致，用于注入bean；
 * 构造函数注入，必须在bean标签的子标签设置constructor-arg标签，顺序必须和构造函数参数顺序一致，且需要设置type和子标签value；
 * 
 * @author GongTengPangYi
 *
 */
public abstract class ShitIoc {

	/**
	 * 配置文件路径
	 */
	protected String xmlFilePath;

	/**
	 * 根据配置文件的路径的构造函数
	 * 
	 * @param xmlFilePath
	 *            配置文件路径
	 * @throws ShitIocConfigException 
	 */
	public ShitIoc(String xmlFilePath) throws ShitIocConfigException {
		this.xmlFilePath = xmlFilePath;
		this.parseXml();
	}

	/**
	 * 解析xml内容
	 * @throws ShitIocConfigException 
	 */
	protected abstract void parseXml() throws ShitIocConfigException;

	/**
	 * 获取容器中的bean对应的对象
	 * 
	 * @param id
	 *            bean的id
	 * @return 容器中的对象
	 * @throws ShitReflectException 
	 * @throws ShitIocClassNotFoundException 
	 */
	public abstract Object getBean(String id) throws ShitIocClassNotFoundException, ShitReflectException;
}
