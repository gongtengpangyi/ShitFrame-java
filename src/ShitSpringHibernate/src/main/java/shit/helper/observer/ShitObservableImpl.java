package shit.helper.observer;

import shit.helper.ShitSchedulerHelper;
import shit.helper.ShitSchedulerHelper.Scheduler;

/**
 * 观察者实现类
 * 
 * @author GongTengPangYi
 *
 * @param <T>
 *            订阅的消息体
 */
public class ShitObservableImpl<T> implements ShitObservable<T> {
	/**
	 * 创建一个观察者
	 * 
	 * @param onSubscriber
	 *            被观察者
	 * @return
	 */
	public static <T> ShitObservableImpl<T> create(OnSubscriber<T> onSubscriber) {
		return new ShitObservableImpl<>(onSubscriber);
	}

	/**
	 * 被订阅者，一个观察者只能对应一个被订阅者
	 */
	private OnSubscriber<T> onSubscriber;

	/**
	 * subscribe操作的所在线程
	 */
	private Scheduler subscribeScheduler;

	/**
	 * 受保护的构造函数
	 * 
	 * @param onSubscriber
	 *            被订阅者
	 */
	protected ShitObservableImpl(OnSubscriber<T> onSubscriber) {
		super();
		this.onSubscriber = onSubscriber;
		this.subscribeScheduler = ShitSchedulerHelper.immediate();
	}

	@Override
	public void subscribe(ShitSubscriber<T> subscriber) {
		subscribeScheduler.execute(new Runnable() {

			@Override
			public void run() {
				doSubscribe(subscriber);
			}
		});
	}

	/**
	 * 实际实现订阅活动的过程
	 * 
	 * @param subscriber
	 *            订阅者
	 */
	private void doSubscribe(ShitSubscriber<T> subscriber) {
		try {
			// 被订阅者主动取通知订阅者
			this.onSubscriber.call(subscriber);
		} catch (Exception e) {
			subscriber.onError(e);
		}
	}

	@Override
	public <E> ShitObservableImpl<E> filter(ShitObservableDataFilter<T, E> dataFilter) {
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

	@Override
	public <E> ShitObservableImpl<E> filter(ShitSubscriberFilter<T, E> shitSubscriberFilter) {
		return new ShitObservableImpl<E>(new OnSubscriber<E>() {

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

					@Override
					public void onError(Throwable e) {
						subscriber.onError(e);
					}
				});
			}
		});
	}

	@Override
	public ShitObservableImpl<T> subscribeOn(Scheduler scheduler) {
		this.subscribeScheduler = scheduler;
		return this;
	}

	@Override
	public ShitObservableImpl<T> observerOn(Scheduler scheduler) {
		return filter(new ShitSubscriberFilter<T, T>() {

			@Override
			public void filterNext(T t, ShitSubscriber<T> subscriber) {
				scheduler.execute(new Runnable() {

					@Override
					public void run() {
						subscriber.onNext(t);
					}
				});
			}

			@Override
			public void filterComplete(T t, ShitSubscriber<T> subscriber) {
				scheduler.execute(new Runnable() {

					@Override
					public void run() {
						subscriber.onComplete(t);
					}
				});
			}
		});
	}

	@Override
	public ShitObservableImpl<T> timer(int num, int type) {
		return filter(new ShitSubscriberFilter<T, T>() {

			@Override
			public void filterNext(T t, ShitSubscriber<T> subscriber) {
				try {
					Thread.sleep(num * type);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				subscriber.onNext(t);
			}

			@Override
			public void filterComplete(T t, ShitSubscriber<T> subscriber) {
				try {
					Thread.sleep(num * type);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				subscriber.onComplete(t);
			}
		});
	}

}
