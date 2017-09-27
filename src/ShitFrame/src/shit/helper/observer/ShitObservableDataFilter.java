package shit.helper.observer;

/**
 * 过滤观察者数据的接口
 * 
 * @author Fang
 *
 * @param <T>
 *            过滤前消息体
 * @param <E>
 *            过滤后消息体
 */
public interface ShitObservableDataFilter<T, E> {

	/**
	 * 过滤next
	 * 
	 * @param t
	 *            原数据
	 * @return 新数据
	 */
	public E filterNext(T t);

	/**
	 * 过滤complete
	 * 
	 * @param t
	 *            原数据
	 * @return 新数据
	 */
	public E filterComplete(T t);
}
