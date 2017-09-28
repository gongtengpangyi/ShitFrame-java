package shit.helper.observer;

import shit.helper.ShitSchedulerHelper.Scheduler;

/**
 * 观察者接口
 * @author Fang
 *
 * @param <T> 订阅消息体
 */
public interface ShitObservable<T> {
	/**
	 * 执行订阅活动
	 * 
	 * @param subscriber
	 *            订阅者
	 */
	public void subscribe(ShitSubscriber<T> subscriber);
	
	/**
	 * 过滤
	 * 
	 * @param filter
	 *            数据过滤器
	 * @return 后续操作的Observable
	 */
	public <E> ShitObservableImpl<E> filter(ShitObservableDataFilter<T, E> dataFilter);
	
	/**
	 * 过滤操作
	 * 
	 * @param shitFilterSubscriber
	 *            订阅者操作过滤器
	 * @return 新的Observable
	 */
	public <E> ShitObservableImpl<E> filter(ShitSubscriberFilter<T, E> shitSubscriberFilter);
	
	/**
	 * 控制subscribe操作的所在线程
	 * 
	 * @param scheduler
	 *            线程调度
	 * @return 观察者自身
	 */
	public ShitObservableImpl<T> subscribeOn(Scheduler scheduler);
	
	/**
	 * 控制观察订阅操作的所在线程
	 * 
	 * @param scheduler
	 *            线程调度
	 * @return 观察者
	 */
	public ShitObservableImpl<T> observerOn(Scheduler scheduler);
	
	
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
