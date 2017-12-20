package shit.helper.observer;

/**
 * 订阅者接口
 * 
 * @author GongTengPangYi
 *
 * @param <T>
 *            订阅的消息体
 */
public interface ShitSubscriber<T> {

	/**
	 * 下一步操作
	 * 
	 * @param t
	 *            操作元素
	 */
	public void onNext(T t);

	/**
	 * 完成操作
	 * 
	 * @param t
	 *            操作元素
	 */
	public void onComplete(T t);

	/**
	 * 操作出错
	 * 
	 * @param e
	 *            错误信息
	 */
	public void onError(Throwable e);

}
