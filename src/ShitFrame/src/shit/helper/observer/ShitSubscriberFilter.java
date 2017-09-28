package shit.helper.observer;

/**
 * 过滤订阅者操作的接口
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
