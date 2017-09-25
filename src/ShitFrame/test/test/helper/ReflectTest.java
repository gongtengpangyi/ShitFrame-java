package test.helper;

import java.util.Date;

import org.junit.Test;

import shit.helper.ShitReflectHelper;

public class ReflectTest {
	@Test
	public void test1() {
//		TestModel child = new TestModel();
		Class<?>[] types = {String.class};
		System.out.println(ShitReflectHelper.invokeMethodByName(null, TestModel.class, "testChildStatic", types, true, "xxx"));
	}
	
	@Test
	public void test2() {
		Class<?>[] types = {Date.class};
		TestModel model = (TestModel) ShitReflectHelper.newInstance(TestModel.class, types, new Date());
		System.out.println(model);
	}
	
	@Test
	public void test3() {
		Class<?>[] types = {Date.class};
		TestModel model = (TestModel) ShitReflectHelper.newInstance(TestModel.class, types, new Date());
		ShitReflectHelper.setValue(model, "name", "xxx", true);
		ShitReflectHelper.setValue(model, "param1", "yyy", true);
		ShitReflectHelper.setValue(model, "time", "1995-02-03 01:01:01", true);
		System.out.println(model);
		System.out.println(ShitReflectHelper.getValue(model, "time", true));
	}
}
