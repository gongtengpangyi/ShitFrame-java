package shit.helper.observer;

/**
 * 观察者
 * 
 * @author Fang
 *
 * @param <T>
 *            订阅的消息体
 */
public class ShitObservable<T> {
	/**
	 * 创建一个观察者
	 * 
	 * @param onSubscriber
	 *            被观察者
	 * @return
	 */
	public static <T> ShitObservable<T> create(OnSubscriber<T> onSubscriber) {
		return new ShitObservable<>(onSubscriber);
	}

	/**
	 * 被订阅者，一个观察者只能对应一个被订阅者
	 */
	private OnSubscriber<T> onSubscriber;

	/**
	 * 受保护的构造函数
	 * 
	 * @param onSubscriber
	 *            被订阅者
	 */
	protected ShitObservable(OnSubscriber<T> onSubscriber) {
		super();
		this.onSubscriber = onSubscriber;
	}

	/**
	 * 执行订阅活动
	 * 
	 * @param subscriber
	 *            订阅者
	 */
	public void subscribe(ShitSubscriber<T> subscriber) {
		// 被订阅者主动取通知订阅者
		this.onSubscriber.call(subscriber);
	}

	/**
	 * 过滤
	 * 
	 * @param filter
	 *            数据过滤器
	 * @return 后续操作的Observable
	 */
	public <E> ShitObservable<E> filter(ShitObservableDataFilter<T, E> dataFilter) {
		return filter(new ShitSubscriberFilter<T, E>() {

			@Override
			public void filterNext(T t, ShitSubscriber<E> subscriber) {
				subscriber.onNext(dataFilter.filterNext(t));
			}

			@Override
			public void filterComplete(T t, ShitSubscriber<E> subscriber) {
				subscriber.onComplete(dataFilter.filterComplete(t));
			}
		});
	}

	/**
	 * 过滤操作
	 * 
	 * @param shitFilterSubscriber
	 *            订阅者操作过滤器
	 * @return 新的Observable
	 */
	public <E> ShitObservable<E> filter(ShitSubscriberFilter<T, E> shitSubscriberFilter) {
		return new ShitObservable<E>(new OnSubscriber<E>() {

			@Override
			public void call(ShitSubscriber<E> subscriber) {

				subscribe(new ShitSubscriber<T>() {

					@Override
					public void onNext(T t) {
						shitSubscriberFilter.filterNext(t, subscriber);
					}

					@Override
					public void onComplete(T t) {
						shitSubscriberFilter.filterComplete(t, subscriber);
					}
				});
			}
		});
	}
	
	

	/***
	 * 被观察者接口
	 * 
	 * @author Fang
	 *
	 */
	public interface OnSubscriber<T> {

		/**
		 * 执行被观察者的动作
		 * 
		 * @param subscriber
		 *            订阅者
		 */
		void call(ShitSubscriber<T> subscriber);
	}
}
