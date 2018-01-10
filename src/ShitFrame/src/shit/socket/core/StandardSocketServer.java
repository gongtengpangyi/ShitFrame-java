package shit.socket.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import shit.socket.ShitSocketClient;
import shit.socket.ShitSocketServer;
import shit.socket.context.ShitSocketClientContext;
import shit.socket.pack.PackParser;

public class StandardSocketServer extends ShitSocketServer {
	
	private ServerSocket serverSocket;
	
	private int port;

	public StandardSocketServer(ShitSocketClientContext shitSocketClientContext, String charset, int port, PackParser parser) {
		super(shitSocketClientContext, charset, parser);
		this.port = port;
	}

	@Override
	protected void startInternal() {
		try {
			serverSocket = new ServerSocket(port);
			shitSocketClientContext.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void runInternal() {
		try {
			Socket clientSocket = serverSocket.accept();
			ShitSocketClient client = new StandardSocketClient(clientSocket, this);
			client.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void initInternal() {
		
		
	}

}
