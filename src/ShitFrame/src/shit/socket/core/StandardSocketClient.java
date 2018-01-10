package shit.socket.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import shit.helper.ShitReflectException;
import shit.socket.ShitSocketClient;
import shit.socket.ShitSocketServer;

/**
 * 套接字的客户端的基本实现
 * @author GongTengPangYi
 *
 */
public class StandardSocketClient extends ShitSocketClient {
	
	/**
	 * 套接字
	 */
	private Socket socket;
	
	/**
	 * 输出流
	 */
	private OutputStream os;
	
	/**
	 * 输入流
	 */
	private BufferedReader br;
	
	private String line;
	
	private ReceiveAction receiveAction;
	
	public StandardSocketClient(Socket socket, ShitSocketServer server) {
		super(server);
		this.socket = socket;
		receiveAction = new ReceiveAction(server.getParser());
		try {
			br = new BufferedReader(new InputStreamReader(this.socket.getInputStream(), shitSocketServer.getCharset()));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void send(String message) {
		try {
			byte[] bytes = message.getBytes(shitSocketServer.getCharset());
			send(bytes);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 

	}
	
	/**
	 * 发送信息
	 * @param bytes
	 */
	private void send(byte[] bytes) {
		if (os != null) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						os.write(bytes);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
			}).start();
		}
	}

	@Override
	protected void startInternal() {
		try {
			os = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void stopInternal() {

	}

	@Override
	protected void closeInternal() {
		
	}

	@Override
	protected void initInternal() {

	}

	@Override
	protected void runInternal() {
		try {
			while((line = br.readLine()) != null) {				
				parseLine(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 解析
	 * @param line
	 */
	protected void parseLine(String line) {
		System.out.println(line);
		try {
			receiveAction.parse(this, line);
		} catch (ShitReflectException e) {
			e.printStackTrace();
		}
	}

}
