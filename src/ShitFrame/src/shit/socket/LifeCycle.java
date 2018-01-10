package shit.socket;

/**
 * 代表了整个生命周期，是大部分组件类的共同接口
 * 
 * @author GongTengPangYi
 *
 */
public interface LifeCycle {

	/**
	 * 关闭
	 */
	public void close();

	/**
	 * 开始
	 */
	public void start();
	
	/**
	 * 初始化
	 */
	public void init();
	
	/**
	 * 暂停
	 */
	public void stop();
}
