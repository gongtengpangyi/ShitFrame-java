package test.helper;

import java.util.Date;

import org.junit.Test;

import shit.helper.ShitReflectException;
import shit.helper.ShitReflectHelper;

public class ReflectTest {
	@Test
	public void test1() {
//		TestModel child = new TestModel();
		Class<?>[] types = {String.class};
		try {
			System.out.println(ShitReflectHelper.invokeMethodByName(null, TestModel.class, "testChildStatic", types, true, "xxx"));
		} catch (ShitReflectException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test2() {
		try {
			Class<?>[] types = {Date.class};
			TestModel model;
			model = (TestModel) ShitReflectHelper.newInstance(TestModel.class, types, new Date());
			System.out.println(model);
		} catch (ShitReflectException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test3() {
		Class<?>[] types = {Date.class};
		try {
			TestModel model = (TestModel) ShitReflectHelper.newInstance(TestModel.class, types, new Date());
			ShitReflectHelper.setValue(model, "name", "xxx", true);
			ShitReflectHelper.setValue(model, "param1", "yyy", true);
			ShitReflectHelper.setValue(model, "time", "1995-02-03 01:01:01", true);
			System.out.println(model);
			System.out.println(ShitReflectHelper.getValue(model, "time", true));
		} catch (ShitReflectException e) {
			e.printStackTrace();
		}
	}
}
