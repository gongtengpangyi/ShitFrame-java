package shit.helper.observer;

/**
 * 过滤订阅者操作的接口，用作订阅过程的中间操作，和DataFilter不同的是，这里定义的是订阅者直接的操作
 * 即包含了订阅者执行收到消息过程的一切处理行为，而DataFilter只是过滤订阅的数据
 * 
 * @author GongTengPangYi
 *
 * @param <T>
 *            过滤前消息体
 * @param <E>
 *            过滤后消息体
 */
public interface ShitSubscriberFilter<T, E> {

	/**
	 * 过滤next操作
	 * 
	 * @param t
	 *            原数据
	 * @param subscriber
	 *            订阅者
	 */
	public void filterNext(T t, ShitSubscriber<E> subscriber);

	/**
	 * 过滤complete操作
	 * 
	 * @param t
	 *            原数据
	 * @param subscriber
	 *            订阅者
	 */
	public void filterComplete(T t, ShitSubscriber<E> subscriber);
}
