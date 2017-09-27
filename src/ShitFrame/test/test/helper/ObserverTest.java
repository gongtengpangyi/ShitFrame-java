package test.helper;

import org.junit.Test;

import shit.helper.observer.ShitObservable;
import shit.helper.observer.ShitObservableDataFilter;
import shit.helper.observer.ShitSubscriber;

public class ObserverTest {
	
	@Test
	public void test1() {
		ShitObservable.create(new ShitObservable.OnSubscriber<String>() {

			@Override
			public void call(ShitSubscriber<String> subscriber) {
				subscriber.onNext("next");
				subscriber.onComplete("complete");
			}
			
		}).filter(new ShitObservableDataFilter<String, Integer>() {

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
		}).subscribe(new ShitSubscriber<Integer>() {
			

			@Override
			public void onNext(Integer t) {
				System.out.println(t);
				
			}

			@Override
			public void onComplete(Integer t) {
				System.out.println(t);
				
			}
		});
	}
	
	@Test
	public void test2() {
		
	}
}
