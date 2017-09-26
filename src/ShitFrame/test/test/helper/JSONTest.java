package test.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import net.sf.json.JSONObject;
import shit.helper.ShitJSONHelper;
import shit.helper.ShitReflectHelper;
import shit.helper.json.ShitJSONArray;
import shit.helper.json.ShitJSONObject;

public class JSONTest {
	@Test
	public void test1() {
		Class<?>[] types = {Date.class};
		TestModel model = (TestModel) ShitReflectHelper.newInstance(TestModel.class, types, new Date());
		ShitReflectHelper.setValue(model, "name", "xxx", true);
		ShitReflectHelper.setValue(model, "param1", "yyy", true);
		ShitReflectHelper.setValue(model, "time", "1995-02-03 01:01:01", true);
		System.out.println(model);
		ShitJSONHelper.init("shit.helper.json.netsf.ShitJSONInitByNetSf");
		ShitJSONObject<?> json = ShitJSONHelper.fromObject(model, "* !time");
		System.out.println(json.toString());
		List<TestModel> models = new ArrayList<>();
		models.add(model);
		models.add(model);
		ShitJSONArray<?> array = ShitJSONHelper.fromList(models, "* !time");
		System.out.println(array.toString());
	}
}
