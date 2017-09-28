package test.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import shit.helper.ShitJSONHelper;
import shit.helper.ShitReflectException;
import shit.helper.ShitReflectHelper;
import shit.helper.json.ShitJSONArray;
import shit.helper.json.ShitJSONObject;

/**
* 创建时间：${date} ${time}
* 项目名称：${project_name} 
* @author GongTengPangYi
* @version 1.0 
* @since JDK 1.8.0
* 文件名称：${file_name}  
* 类说明：  
**/
public class JSONTest {
	@Test
	public void test1() {
		Class<?>[] types = {Date.class};
		try {
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
		} catch (ShitReflectException e) {
			e.printStackTrace();
		}
	}
}
