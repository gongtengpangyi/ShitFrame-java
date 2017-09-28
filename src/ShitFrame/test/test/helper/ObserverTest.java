package test.helper;

import shit.helper.ShitObservableHelper;
import shit.helper.ShitSchedulerHelper;
import shit.helper.observer.ShitObservable.OnSubscriber;
import shit.helper.observer.ShitObservable.Timer;
import shit.helper.observer.ShitObservableDataFilter;
import shit.helper.observer.ShitSubscriber;
import shit.helper.observer.ShitSubscriberList;

public class ObserverTest {
	
	public void test1() {
		ShitObservableHelper.create(new OnSubscriber<String>() {

			@Override
			public void call(ShitSubscriber<String> subscriber) {
				for (int i = 1; i > 0 ; i--) {
					System.out.println(i);
					subscriber.onNext("next" + 10/i);
				}
				subscriber.onComplete("complete");
				
			}
		}).subscribeOn(ShitSchedulerHelper.immediate())
		.observerOn(ShitSchedulerHelper.newThread())
		.timer(1000, Timer.MILL_SECOND)
		.filter(new ShitObservableDataFilter<String, Integer>() {

			@Override
			public Integer filterNext(String t) {
				System.out.println(t);
				return t.length();
			}

			@Override
			public Integer filterComplete(String t) {
				System.out.println(t);
				return t.length();
			}
			
		}).filter(new ShitObservableDataFilter<Integer, Integer>() {

			@Override
			public Integer filterNext(Integer t) {
				System.out.println(t);
				return t+1;
			}

			@Override
			public Integer filterComplete(Integer t) {
				System.out.println(t);
				return t+1;
			}
		}).subscribe(ShitSubscriberList.create(new ShitSubscriber<Integer>() {

			@Override
			public void onNext(Integer t) {
				System.out.println("1" + t);
				
			}

			@Override
			public void onComplete(Integer t) {
				System.out.println("1" + t);
				
			}

			@Override
			public void onError(Throwable e) {
				// TODO Auto-generated method stub
				
			}
		}).add(new ShitSubscriber<Integer>() {

			@Override
			public void onNext(Integer t) {
				System.out.println("2" + t);
				System.out.println("id:" + Thread.currentThread().getId());

			}

			@Override
			public void onComplete(Integer t) {
				System.out.println("2" + t);
				System.out.println("id:" + Thread.currentThread().getId());
			}

			@Override
			public void onError(Throwable e) {
				e.printStackTrace();
				System.out.println("xxxxxxxxxxxx" + Thread.currentThread().getId());
			}
		}));
	}
	
	
	public static void main(String args[]) {
		ObserverTest test = new ObserverTest();
		test.test1();
	}
	
	public void test2() {
//		try {
//			ShitObservableHelper.setObservableClass(ShitObservableDataFilter.class);
//		} catch (ShitNotImplException e) {
//			e.printStackTrace();
//		}
		ShitObservableHelper.just("next1", "next2")
		.subscribeOn(ShitSchedulerHelper.immediate())
		.observerOn(ShitSchedulerHelper.newThread())
		.filter(new ShitObservableDataFilter<String, Integer>() {

			@Override
			public Integer filterNext(String t) {
				System.out.println(t);
				System.out.println("id:" + Thread.currentThread().getId());
				return t.length();
			}

			@Override
			public Integer filterComplete(String t) {
				System.out.println(t);
				System.out.println("id:" + Thread.currentThread().getId());
				return t.length();
			}
			
		})
		.observerOn(ShitSchedulerHelper.newThread())
		.subscribe(new ShitSubscriber<Integer>() {

			@Override
			public void onNext(Integer t) {
				System.out.println(t);
				System.out.println("id:" + Thread.currentThread().getId());
				
			}

			@Override
			public void onComplete(Integer t) {
				System.out.println(t);
				System.out.println("id:" + Thread.currentThread().getId());
			
			}

			@Override
			public void onError(Throwable e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void test3() {
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {		
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getId() + ":" + i);
				}
				
			}
		};
		ShitSchedulerHelper.immediate().execute(runnable);
		ShitSchedulerHelper.newThread().execute(runnable);
		ShitSchedulerHelper.immediate().execute(runnable);
	}
}
