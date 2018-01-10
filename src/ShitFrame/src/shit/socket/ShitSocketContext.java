package shit.socket;

/**
 * 容器类接口，整个Socket连接的套接字组需要存放在容器中
 * 
 * @author GongTengPangYi
 *
 */
public interface ShitSocketContext<T extends LifeCycle> extends LifeCycle {

	/**
	 * 根据参数键获取容器内存放的值
	 * 
	 * @param key
	 *            键
	 * @return
	 */
	public T get(String key);

	/**
	 * 根据参数键向容器中存放子节点
	 * 
	 * @param key
	 *            键
	 * @param value
	 */
	public void set(String key, T value);
}
