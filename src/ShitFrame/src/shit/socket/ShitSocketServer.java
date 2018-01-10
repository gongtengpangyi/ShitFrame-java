package shit.socket;

import shit.socket.context.ShitSocketClientContext;
import shit.socket.pack.PackParser;

/**
 * Socket总服务线程类的抽象类，声明了Socket服务线程类需要实现的操作
 * 
 * @author GongTengPangYi
 *
 */
public abstract class ShitSocketServer extends RunnableLifeCycle {

	/**
	 * SocketClient总容器
	 */
	protected ShitSocketClientContext shitSocketClientContext;

	/**
	 * 编码格式
	 */
	private String charset;
	
	/**
	 * 解析器
	 */
	protected PackParser parser;

	public ShitSocketServer(ShitSocketClientContext shitSocketClientContext, String charset, PackParser parser) {
		super();
		this.shitSocketClientContext = shitSocketClientContext;
		this.charset = charset;
		this.parser = parser;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public ShitSocketClientContext getShitSocketContext() {
		return shitSocketClientContext;
	}

	public void setShitSocketContext(ShitSocketClientContext shitSocketClientContext) {
		this.shitSocketClientContext = shitSocketClientContext;
	}

	public PackParser getParser() {
		return parser;
	}

	public void setParser(PackParser parser) {
		this.parser = parser;
	}

	@Override
	protected void stopInternal() {
		shitSocketClientContext.stop();
	}

	@Override
	protected void closeInternal() {
		shitSocketClientContext.close();
	}

	
}
