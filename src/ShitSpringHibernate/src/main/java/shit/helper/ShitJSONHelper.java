package shit.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shit.helper.json.*;

/**
 * JSON的工具类，便利处理JSON相关的内容
 * 
 * @author GongTengPangYi
 *
 */
public class ShitJSONHelper {

	/**
	 * JSON单元驱动器
	 */
	public static ShitJSONInit<?, ?> driver;
	
	/**
	 * 不能被用来当分隔符号的特殊符号
	 */
	public static final String UN_USE_CUTS = "[a-zA-Z]|[}]|[{]|[.]|[*]|[!]";
	
	/**
	 * 初始化
	 * @param className 类名全称
	 */
	public static void init(String className) {
		try {
			Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据参数来获取一个类的JSON对象
	 * 
	 * @param object
	 *            类
	 * @param selector
	 *            这是一个自定义的选择器，选择的标准如下：
	 *            "all"表示当前类的所有参数，其他情况下则按照当前参数来传，
	 *            所有的参数需要有对应的get函数 参数之间用空格隔开，如
	 *            "name password"则表示取到当前类getName和getPassword的值，
	 *            并且值在JSON文件中存放的键名为"name"和"password"，此外如果参数有逗号隔开，则表示当前参数为一个类
	 *            如"user.name"则表示取到getUser方法的返回值（User类对象）的getName方法返回值 例如
	 *            "params user.name user.test.param1 user.test.param2"
	 *            当层级"."较多的时候，为了方便起见，我们还提供了根据快捷的方式，可以在.后的参数统一打上"{"和"}"，
	 *            并且括号内的第一个字符为分隔符如"{,params,user.name,user.test.param1,user.test.param2}"
	 *            但是这个分隔符必须不能是"."、"{"、"}"、"*"和26字母,也不能是上一级目录，否则就会报错
	 *            可以把上面那个多层次的JSON匹配符简单写成
	 *            "{ params user.{,name,test.{:param1:param2}}}"
	 * @return JSON对象
	 */
	public static ShitJSONObject<?> fromObject(Object object, String selector) {
		// 定义JSONObject对象
		ShitJSONObject<?> json = driver.initJSONObject();
		// 定义分割符号，默认为空格
		String cut = " ";
		try {
			if (selector.startsWith("{") && selector.endsWith("}")) {
				// 判断是否是由{}包起来的，如果是的话说明需要替换分隔符，这个主要是为了子变量的取值递归用的
				cut = selector.substring(1, 2);
				if (cut.matches(UN_USE_CUTS)) {
					// 分隔符不能是特定符号
					throw new Exception("wrong cut char : " + cut);
				}
				// 选择器是除了分隔符以外的部分
				selector = selector.substring(2, selector.length() - 1);
			}
			// 根据分隔符分割字符串
			String[] strs = selector.split(cut);
			// 存放子节点
			Map<String, String> parms = new HashMap<String, String>();
			for (String str : strs) {
				/**
				 * 遍历查询器的每个元素
				 */
				if (str == null) {
					// 为空就跳过
					continue;
				} else if (str.equals("*")) {
					// 为*就获取整个JSON
					json = ShitJSONObject.fromObject(object);
				} else if (str.indexOf(".") < 0) {
					// 如果不包含‘.’，根据是否为‘!’开头判断是移除还是添加
					if (str.startsWith("!")) {
						String key = str.substring(1);
						if (json.get(key) == null) {
							continue;
						}
						json.remove(key);
					} else {
						// 如果是添加我们用ModelUtil来获取对应的值，这里用到了putObject
						Object obj = ShitReflectHelper.getValue(object, str, true);
						json.put(str, obj);
					}
				} else {
					// 如果带‘.’符号则需要进入下一个递归，
					// 如果‘.’后的内容是单独的就将其拼接，否则就直接获取，然后塞入map中
					int i = str.indexOf(".");
					String key = str.substring(0, i);
					String value = str.substring(i + 1);
					if (parms.get(key) != null) {
						value = parms.get(key) + " " + value;
					}
					parms.put(key, value);
				}
			}
			// 循环下一层
			for (String key : parms.keySet()) {
				json.put(key, fromObject(ShitReflectHelper.getValue(object, key, true), parms.get(key)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 根据参数来获取一个集合的JSONArray
	 * @param list 集合
	 * @param selector 这是一个自定义的选择器，选择的标准如下：
	 *  "all"表示当前类的所有参数，其他情况下则按照当前参数来传，所有的参数需要有对应的get函数
	 *  参数之间用空格隔开，如"name password"则表示取到当前类getName和getPassword的值，
	 *  并且值在JSON文件中存放的键名为"name"和"password"，此外如果参数有点隔开，则表示当前参数为一个类
	 *  如"user.name"则表示取到getUser方法的返回值（User类对象）的getName方法返回值
	 *  例如 "params user.name user.test.param1 user.test.param2"
	 *  当层级"."较多的时候，为了方便起见，我们还提供了根据快捷的方式，可以在.后的参数统一打上"{"和"}"，
	 *  并且括号内的第一个字符为分隔符如"{,params,user.name,user.test.param1,user.test.param2}"
	 *  但是这个分隔符必须不能是"."、"{"、"}"、"*"和26字母,也不能是上一级目录，否则就会报错
	 *  可以把上面那个多层次的JSON匹配符简单写成"{ params user.{,name,test.{:param1:param2}}}"
	 * @return JSON文件
	 */
	public static ShitJSONArray<?> fromList(List<?> list, String selector) {
		ShitJSONArray<?> array = driver.initJSONArray();
		if (list != null) {
			for (Object object : list) {
				array.add(fromObject(object, selector));
			}
		}
		return array;
	}

}
