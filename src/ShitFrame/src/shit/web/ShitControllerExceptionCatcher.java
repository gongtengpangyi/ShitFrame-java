package shit.web;

/**
 * 处理映射过程中的报错
 * @author GongTengPangYi
 *
 */
public interface ShitControllerExceptionCatcher {

	/**
	 * 处理报错
	 * @param e
	 */
	public void catching(Throwable e);
	
}
