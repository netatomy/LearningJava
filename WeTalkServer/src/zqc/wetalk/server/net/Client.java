package zqc.wetalk.server.net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import zqc.wetalk.exceptions.ApplicationException;

public class Client {

	private Socket s;
	private Scanner in;
	private PrintWriter out;

	public Client(Socket s) throws IOException {
		this.s = s;
		in = new Scanner(s.getInputStream());
		out = new PrintWriter(s.getOutputStream(), true);
	}

	// public boolean isValid(){
	// String identity = in.nextLine();
	// if ("wetalk".equalsIgnoreCase(identity))
	// return true;
	// return false;
	// }

	public void close() throws IOException {
		s.close();
	}

	public String readLine() {
		return in.nextLine();
	}

	public void writeLine(String message) {
		out.println(message);
	}

	public void checkConnection() {
		try {
			s.sendUrgentData(0xFF);
		} catch (IOException ex) {
			throw new ApplicationException(ex);
		}
	}

}
