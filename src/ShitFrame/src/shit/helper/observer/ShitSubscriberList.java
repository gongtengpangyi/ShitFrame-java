package shit.helper.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 订阅者集合类，用于当订阅者较多的时候使用
 * 
 * @author GongTengPangYi
 *
 * @param <T>
 *            数据类型
 */
public class ShitSubscriberList<T> implements ShitSubscriber<T> {

	/**
	 * 新建订阅者集合
	 * 
	 * @param subscribe
	 *            添加订阅者
	 * @return 订阅者集合
	 */
	public static <T> ShitSubscriberList<T> create(ShitSubscriber<T> subscribe) {
		return new ShitSubscriberList<T>().add(subscribe);
	}

	/**
	 * 订阅者集合
	 */
	List<ShitSubscriber<T>> subscribers;

	/**
	 * 构造函数
	 */
	public ShitSubscriberList() {
		super();
		subscribers = new ArrayList<>();
	}

	/**
	 * 添加订阅者
	 * 
	 * @param subscribe
	 *            添加订阅者
	 * @return 订阅者集合本身
	 */
	public ShitSubscriberList<T> add(ShitSubscriber<T> subscribe) {
		subscribers.add(subscribe);
		return this;
	}

	/**
	 * 批量添加订阅者
	 * 
	 * @param subscribes
	 *            添加订阅者
	 * @return 集合本身
	 */
	public ShitSubscriberList<T> addAll(List<ShitSubscriber<T>> subscribes) {
		this.subscribers.addAll(subscribes);
		return this;
	}

	@Override
	public void onNext(T t) {
		for (ShitSubscriber<T> subscriber : subscribers) {
			subscriber.onNext(t);
		}
	}

	@Override
	public void onComplete(T t) {
		for (ShitSubscriber<T> subscriber : subscribers) {
			subscriber.onComplete(t);
		}
	}

	@Override
	public void onError(Throwable e) {
		for (ShitSubscriber<T> subscriber : subscribers) {
			subscriber.onError(e);
		}
	}

}
