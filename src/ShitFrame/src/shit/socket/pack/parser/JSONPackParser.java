package shit.socket.pack.parser;

import java.util.List;

import shit.helper.ShitJSONHelper;
import shit.helper.ShitReflectHelper;
import shit.helper.json.ShitJSONObject;
import shit.helper.json.netsf.ShitJSONObjectByNetSf;
import shit.socket.pack.PackParser;
import shit.socket.pack.Receive;

public class JSONPackParser implements PackParser {
	
	static{
		ShitJSONHelper.init("shit.helper.json.netsf.ShitJSONInitByNetSf");
	}
	
	private String packageName;

	public JSONPackParser(String packageName) {
		super();
		this.packageName = packageName;
	}

	@Override
	public Class<?> parseClass(String message) {
		ShitJSONObject<?> jobj = parse(message);
		List<Class<?>> clazzs = ShitReflectHelper.getClasses(packageName);
		for (Class<?> clazz : clazzs) {
			Receive receive = clazz.getAnnotation(Receive.class);
			if (receive == null) {
				continue;
			}
			String objKey = receive.objKey();
			String objValue = receive.objValue();
			if (objValue.equals(jobj.get(objKey))) {
				return clazz;
			}
		}
		return null;
	}

	@Override
	public Object parseObject(Class<?> clazz, String message) {
		ShitJSONObject<?> jobj = parse(message);
		return jobj.toBean(clazz);
	}

	private ShitJSONObject<?> parse(String message) {
		return ShitJSONObjectByNetSf.fromObject(message);
	}

}
