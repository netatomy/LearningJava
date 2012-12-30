package zqc.wetalk.server.net;

import zqc.wetalk.BroadcastInfo;
import zqc.wetalk.LogonInfo;
import zqc.wetalk.RegisterInfo;
import zqc.wetalk.server.data.UserFinder;
import zqc.wetalk.server.data.UserGateway;
import zqc.wetalk.server.domain.AccountService;

public class AcceptMessageHandler implements AcceptMessageObserver {

    private TcpClient client;

    public AcceptMessageHandler(TcpClient client) {

        this.client = client;
    }

    @Override
    public void accept(String head, String body) {

        if ("REGISTER".equals(head)) {
            
            RegisterInfo userInfo = RegisterInfo.parse(body);
            AccountService.getInstance().registerUser(userInfo);
            client.writeLine("OK");
            client.writeLine("用户注册成功");
        }
        else if ("LOGON".equals(head)) {
            LogonInfo logonInfo = LogonInfo.parse(body);
            UserGateway user = AccountService.getInstance().logOn(logonInfo);
            if (user != null) {
                // user.setClient(client);
                client.writeLine("OK");
                client.writeLine(user.toUserInfo().toMessage());
            }
            else {
                client.writeLine("ERROR");
                client.writeLine("User logged on failed.");
            }

        }
        else if ("ONLINEUSERS".equals(head)) {
            Integer userId = Integer.valueOf(body);
            client.writeLine("OK");
            client.writeLine(AccountService.getInstance().getOnlineUserInfoList().toMessage());
        }
        else if ("LOGOUT".equals(head)) {
            Integer userId = Integer.valueOf(body);
            AccountService.getInstance().logOut(userId);
            client.writeLine("OK");
            client.writeLine("User logged client.");
            client.close();
        }
        else if ("TALK".equals(head)) {
            BroadcastInfo broadcastInfo = BroadcastInfo.parse(body);
            Integer userId = Integer.valueOf(broadcastInfo.getUserID());
            UserGateway user = new UserFinder().find(userId);
            if (!AccountService.getInstance().onlineUsers().contains(user)) {
                client.writeLine("ERROR");
                client.writeLine("Please logon.");
            }
            else {
                client.writeLine("OK");
                client.writeLine("Ready to broadcast");
                for (TcpClient eachClient : client.getServer().aliveClients()){
                    eachClient.writeLine("TALK");
                    eachClient.writeLine(broadcastInfo.toMessage());
                }
            }
        }
        else {
            client.writeLine("ERROR");
            client.writeLine("Invalid Message.");
        }

    }

}
