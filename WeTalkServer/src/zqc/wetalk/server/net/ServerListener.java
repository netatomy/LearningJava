package zqc.wetalk.server.net;

import java.io.IOException;
import java.net.ServerSocket;

import zqc.wetalk.exceptions.ApplicationException;

public class ServerListener {

	private ServerSocket serverSocket;
	private boolean running = false;

	public void start() {
		try {
			serverSocket = new ServerSocket(6600);
		} catch (IOException ex) {
			throw new ApplicationException(ex);
		}
	}

	public Client receive() {
		try {
			return new Client(serverSocket.accept());
		} catch (IOException ex) {
			throw new ApplicationException(ex);
		}
	}

	public void stop() {
		try {
			if (!serverSocket.isClosed())
				serverSocket.close();
		} catch (IOException ex) {
			throw new ApplicationException(ex);
		}
	}
}
