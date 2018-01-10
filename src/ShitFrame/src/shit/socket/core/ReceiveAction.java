package shit.socket.core;


import java.lang.reflect.Method;

import shit.helper.ShitReflectException;
import shit.helper.ShitReflectHelper;
import shit.socket.ShitSocketClient;
import shit.socket.pack.PackParser;

/**
 * 接收到消息时候的操作
 * @author GongTengPangYi
 *
 */
public class ReceiveAction {

	/**
	 * 解析器
	 */
	private PackParser parser;

	public ReceiveAction(PackParser parser) {
		super();
		this.parser = parser;
	}
	
	public void parse(ShitSocketClient socketClient, String line) throws ShitReflectException {
		Class<?> packClass = parser.parseClass(line);
		if (packClass == null) {
			return;
		}
		Object packObject = parser.parseObject(packClass, line);
		Method[] methods = ShitReflectHelper.findMethods(packClass, 0);
		for (Method method : methods) {
			if (method.getAnnotation(shit.socket.pack.ReceiveAction.class) != null) {
				ShitReflectHelper.invokeMethod(packObject, method, socketClient);
			}
		}
	}
}
