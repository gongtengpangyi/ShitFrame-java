package shit.socket.pack;

public interface PackParser {

	/**
	 * 解析接收到的消息获取数据包类
	 * @param message
	 * @return class
	 */
	public Class<?> parseClass(String message);
	
	/**
	 * 将数据解析成对象
	 * @param clazz
	 * @param message
	 * @return
	 */
	public Object parseObject(Class<?> clazz, String message);
}
