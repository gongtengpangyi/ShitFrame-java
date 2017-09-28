package test.helper;

import org.junit.Test;

import shit.helper.ShitObservableHelper;
import shit.helper.ShitSchedulerHelper;
import shit.helper.observer.ShitObservableDataFilter;
import shit.helper.observer.ShitSubscriber;
import shit.helper.observer.ShitSubscriberList;

public class ObserverTest {
	
	@Test
	public void test1() {
		String[] strs = {"next1", "next2"};
		ShitObservableHelper.just(strs).filter(new ShitObservableDataFilter<String, Integer>() {

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
		}).add(new ShitSubscriber<Integer>() {

			@Override
			public void onNext(Integer t) {
				System.out.println("2" + t);
				
			}

			@Override
			public void onComplete(Integer t) {
				System.out.println("2" + t);
				
			}
		}));
	}
	
	
	public static void main(String args[]) {
		ObserverTest test = new ObserverTest();
		test.test2();
	}
	
	public void test2() {
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
