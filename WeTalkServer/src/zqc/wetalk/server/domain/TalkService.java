package zqc.wetalk.server.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import zqc.wetalk.BroadcastInfo;
import zqc.wetalk.LogonInfo;
import zqc.wetalk.RegisterInfo;
import zqc.wetalk.exceptions.ApplicationException;
import zqc.wetalk.server.data.UserFinder;
import zqc.wetalk.server.data.UserGateway;
import zqc.wetalk.server.net.Client;
import zqc.wetalk.server.net.ClientHandler;
import zqc.wetalk.server.net.ServerListener;

public class TalkService {

    private AccountService    accountService;
    private ServerListener    serverListener;

    private List<UserGateway> onlineUsers = new ArrayList<>();
    private List<Client> aliveClients = new ArrayList<>();

    public TalkService(AccountService accountService, ServerListener serverListener) {

        this.accountService = accountService;
        this.serverListener = serverListener;
    }

    public void process() {

        Runnable serverListenerTask = new Runnable() {

            @Override
            public void run() {

                try {
                    serverListener.start(TalkService.this);
                }
                catch (IOException e) {
                    throw new ApplicationException(e);
                }

            }
        };

        new Thread(serverListenerTask).start();

    }

    public void receiveClient(Client client) {

        aliveClients.add(client);

        Runnable r = new ClientHandler(client, this);
        
        new Thread(r).start();
    }

    public void broadCast(BroadcastInfo broadcastInfo) {
        // TODO Auto-generated method stub
    }
}
