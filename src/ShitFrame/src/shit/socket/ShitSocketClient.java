package shit.socket;

import shit.socket.core.SendHelper;

public abstract class ShitSocketClient extends RunnableLifeCycle {

	/**
	 * SocketClient总容器
	 */
	protected ShitSocketServer shitSocketServer;

	public ShitSocketClient(ShitSocketServer shitSocketServer) {
		super();
		this.shitSocketServer = shitSocketServer;
	}

	/**
	 * 给当前客户发送信息
	 * 
	 * @param message
	 *            待发送的信息
	 */
	public abstract void send(String message);

	/**
	 * 向同服务的其他客户连接发送信息
	 * 
	 * @param key
	 *            其他客户的键
	 * @param message
	 *            发送的信息
	 */
	public void send(String key, String message) {
		ShitSocketClient client = shitSocketServer.getShitSocketContext().get(key);
		if (client != null) {
			client.send(message);
		}
	}

	/**
	 * 给当前客户发送数据包
	 * 
	 * @param pack
	 *            待发送的数据包
	 */
	public void sendPack(Object pack) {
		send(SendHelper.getString(pack));
	}

	/**
	 * 向同服务的其他客户连接发送数据包
	 * 
	 * @param key
	 *            其他客户的键
	 * @param pack
	 *            发送的数据包
	 */
	public void sendPack(String key, Object pack) {
		send(key, SendHelper.getString(pack));
	}

}
