package shit.helper.observer;

import shit.helper.ShitSchedulerHelper.Scheduler;

/**
 * 观察者接口
 * 这个类用来声明观察者，可以用此接口来联系观察者和订阅者
 * 
 * @author GongTengPangYi
 *
 * @param <T>
 *            订阅消息体
 */
public interface ShitObservable<T> {
	/**
	 * 执行订阅活动，即声明订阅者并触发被订阅者的发送过程
	 * 
	 * @param subscriber
	 *            订阅者
	 */
	public void subscribe(ShitSubscriber<T> subscriber);

	/**
	 * 过滤，用作订阅过程发送的数据的过滤
	 * 
	 * @param filter
	 *            数据过滤器
	 * @return 后续操作的Observable
	 */
	public <E> ShitObservable<E> filter(ShitObservableDataFilter<T, E> dataFilter);

	/**
	 * 过滤操作，用于定义订阅过程中中间执行的操作
	 * 
	 * @param shitFilterSubscriber
	 *            订阅者操作过滤器
	 * @return 新的Observable
	 */
	public <E> ShitObservable<E> filter(ShitSubscriberFilter<T, E> shitSubscriberFilter);

	/**
	 * 控制subscribe操作的所在线程
	 * 
	 * @param scheduler
	 *            线程调度
	 * @return 观察者自身
	 */
	public ShitObservable<T> subscribeOn(Scheduler scheduler);

	/**
	 * 控制观察订阅操作的所在线程
	 * 
	 * @param scheduler
	 *            线程调度
	 * @return 观察者
	 */
	public ShitObservable<T> observerOn(Scheduler scheduler);

	/**
	 * 延时过滤操作，延迟该操作之后的其他操作
	 * 
	 * @param num
	 *            时长
	 * @param type
	 *            计数单位
	 * @return 观察者
	 */
	public ShitObservable<T> timer(int num, int type);

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

	/**
	 * 时间控制
	 * 
	 * @author Fang
	 *
	 */
	public class Timer {
		public static final int MILL_SECOND = 1;
		public static final int SECOND = 1000 * MILL_SECOND;
		public static final int MILLION = 60 * SECOND;
		public static final int HOUR = 60 * MILLION;
		public static final int DAY = 24 * HOUR;
		public static final int MONTH = 30 * DAY;
		public static final int YEAR = 365 * DAY;
	}

}
