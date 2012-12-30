package zqc.wechat.client.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

public class ChatClient {

	private static final int BUFFER_SIZE = 1024 * 1024;
	private Socket socket;
	private BufferedInputStream in;
	private BufferedOutputStream out;
	private InetAddress hostAddress;
	private int port;
	private static ChatClient client;
	
	public ChatClient(String host, int port) throws UnknownHostException {
		hostAddress = InetAddress.getByName(host);
		this.port = port;
	}

	private byte[] receiveBytes() throws IOException {

		byte[] data = new byte[BUFFER_SIZE];
		int count = 0;
		count = in.read(data);
		return Arrays.copyOf(data, count);
	}

	public String receive() throws IOException {

		return new String(receiveBytes());
	}

	public void close() throws IOException, InterruptedException {

		out.close();
		in.close();
		socket.close();
		socket = null;
	}

	public void send(byte[] data) throws IOException {

		out.write(data);
		out.flush();
	}
	
	public void send(String message) throws IOException {
		send(message.getBytes());
	}

	public void connect() throws UnknownHostException, IOException,
			InterruptedException {

		if (null != socket && socket.isConnected())
			close();

		socket = new Socket();
		socket.connect(new InetSocketAddress(hostAddress, port));
		socket.setSoTimeout(5000);
		if (socket.isConnected()) {
			in = new BufferedInputStream(socket.getInputStream());
			out = new BufferedOutputStream(socket.getOutputStream());
		}
	}

//	public String talk(String message) throws UnknownHostException,
//			IOException, InterruptedException {
//
//		connect();
//		try {
//			send(message.getBytes());
//			return receive();
//		} finally {
//			close();
//		}
//	}
}
