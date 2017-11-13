package shit.ioc.xml;

import shit.ioc.bean.Bean;

/**
 * bean的存放定义
 * 
 * @author GongTengPangYi
 *
 */
public interface BeanPutter {

	/**
	 * 是否已经存在这个bean了
	 * 
	 * @param id
	 *            bean的id
	 * @return
	 */
	public boolean isBeanPut(String id);

	/**
	 * 存放bean
	 * @param id
	 *            bean的id	 * 
	 * @param bean
	 */
	public void putBean(String id, Bean bean);
}
