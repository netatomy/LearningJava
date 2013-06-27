package zqc.wetalk.server.net;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import zqc.wetalk.BroadcastInfo;
import zqc.wetalk.exceptions.ApplicationException;
import zqc.wetalk.server.data.UserGateway;
import zqc.wetalk.server.domain.AccountService;

public class ServerHandler {
	
	private Map<Integer, Client> clients = new HashMap<>();

	private ServerListener serverListener;

	public void start() {

		serverListener = new ServerListener();

		Runnable r = new Runnable() {

			@Override
			public void run() {
				try {
					serverListener.start();
					while (true) {
						final Client c = serverListener.receive();
						String identity = c.readLine();
						if (!isValid(identity)) {
							c.writeLine("bye");
							c.close();
							continue;
						}
						c.writeLine("wetalk");
						
						Runnable r = new Runnable() {
							
							@Override
							public void run() {
								new ClientHandler(c, ServerHandler.this).start();
							}
						};

					}

				} catch (IOException ex) {
					throw new ApplicationException(ex);
				}

			}
		};

		new Thread(r).start();

	}

	protected boolean isValid(String identity) {

		return "wetalk".equalsIgnoreCase(identity);
	}

	public void broadCast(BroadcastInfo broadcastInfo) {
		for (UserGateway user : AccountService.getInstance().onlineUsers()){
			user.getClient().writeLine("BROADCAST");
			user.getClient().writeLine(broadcastInfo.toMessage());
		}
	}

}
