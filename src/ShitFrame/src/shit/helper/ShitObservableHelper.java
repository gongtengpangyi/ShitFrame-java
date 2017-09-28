package shit.helper;

import shit.helper.observer.ShitObservableImpl;
import shit.helper.observer.ShitSubscriber;
import shit.helper.observer.ShitObservable;
import shit.helper.observer.ShitObservable.OnSubscriber;

/**
 * 观察者工具类
 * 
 * @author GongTengPangYi
 *
 */
public class ShitObservableHelper {
	/**
	 * 默认使用的类
	 */
	private static Class<?> clazz;

	/**
	 * 设置使用的观察者类
	 * 
	 * @param observableClass
	 * @throws ShitNotImplException
	 */
	public static void setObservableClass(Class<?> observableClass) throws ShitNotImplException {
		if (ShitObservable.class.isAssignableFrom(observableClass)) {
			clazz = observableClass;
		} else {
			throw new ShitNotImplException(observableClass.getName() + " is not implements from ShitObservable");
		}
	}

	/**
	 * 恢复默认使用的观察者类
	 */
	public static void setDefaultObservable() {
		clazz = null;
	}

	/**
	 * 创建一个观察者 默认是创建一个ShitObservableImpl，但是后续也可以使用
	 * 
	 * @param onSubscriber
	 *            被观察者
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> ShitObservable<T> create(OnSubscriber<T> onSubscriber) {
		if (clazz == null) {
			return ShitObservableImpl.create(onSubscriber);
		} else {
			System.out.println("xxx");
			Class<?>[] parameterTypes = { OnSubscriber.class };
			try {
				return (ShitObservable<T>) ShitReflectHelper.invokeStaticMethodByName(clazz, "create", parameterTypes,
						false, onSubscriber);
			} catch (ShitReflectException e) {
				System.out.println(clazz + "非Observerable实现类");
				e.printStackTrace();
			}
			return ShitObservableImpl.create(onSubscriber);
		}
	}

	/**
	 * 列表发射
	 * 
	 * @param list
	 *            列表
	 * @return
	 */
	@SafeVarargs
	public static <T> ShitObservable<T> just(T... list) {
		return create(new OnSubscriber<T>() {

			@Override
			public void call(ShitSubscriber<T> subscriber) {
				for (T t : list) {
					subscriber.onNext(t);
				}
			}
		});
	}

}
