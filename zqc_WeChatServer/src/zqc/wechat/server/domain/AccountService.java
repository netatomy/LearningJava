package zqc.wechat.server.domain;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import zqc.wechat.server.data.DataAccess;
import zqc.wechat.server.data.UserFinder;
import zqc.wetalk.LogonInfo;
import zqc.wetalk.RegisterInfo;
import zqc.wetalk.UserInfo;
import zqc.wetalk.UserInfoList;
import zqc.wetalk.exceptions.ApplicationException;

public class AccountService {

    private final Map<Integer, UserInfo>      onlineUsers = new HashMap<>();
    private final Map<Integer, Queue<String>> chatRecords = new HashMap<>();

    private AccountService() {

    }

    private static AccountService instance;

    public synchronized static AccountService getInstance() {

        if (instance == null)
            instance = new AccountService();
        return instance;
    }

    public synchronized void registerUser(RegisterInfo registerInfo) {

        if (!registerInfo.getPassword().equals(registerInfo.getPassword2()))
            throw new ApplicationException("两次密码不匹配");

        if (userNameExists(registerInfo.getUserName()))
            throw new ApplicationException("用户登录名已存在。");

        DataAccess db = new DataAccess();
        try {
            PreparedStatement stmt = db.prepare("INSERT INTO UserTable(UserName, Password, NickName) VALUES(?, ?, ?)");
            try {
                stmt.setString(1, registerInfo.getUserName());
                stmt.setString(2, registerInfo.getPassword());
                stmt.setString(3, registerInfo.getNickName());

                stmt.executeUpdate();
            }
            finally {
                db.cleanUp(stmt);
            }
        }
        catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    private boolean userNameExists(String userName) {

        UserFinder finder = new UserFinder();
        UserInfo user = finder.findByUserName(userName);
        return (user != null);
    }

    public UserInfo logOn(LogonInfo logonInfo) {

        if (logonInfo.getUserName().trim().length() == 0 || logonInfo.getPassword().trim().length() == 0) {
            throw new ApplicationException("用户名或密码为空");
        }

        if (!checkLogon(logonInfo))
            throw new ApplicationException("用户名或密码错误");

        UserFinder finder = new UserFinder();
        UserInfo user = finder.findByUserName(logonInfo.getUserName());

        onlineUsers.put(user.getID(), user);
        if (!chatRecords.containsKey(user.getID())) {
            chatRecords.put(user.getID(), new LinkedList<String>());
        }

        return user;
    }

    private boolean checkLogon(LogonInfo logonInfo) {

        UserFinder finder = new UserFinder();
        return finder.checkLogon(logonInfo);
    }

    public void logOut(UserInfo userInfo) {

        UserInfo user = onlineUsers.get(userInfo.getID());
        if (user == null)
            throw new ApplicationException("用户不在线");
        onlineUsers.remove(user);
    }

    public List<UserInfo> onlineUsers() {

        List<UserInfo> result = new ArrayList<>(onlineUsers.values());
        return Collections.unmodifiableList(result);
    }

    public UserInfo findOnlineUser(Integer id) {

        return onlineUsers.get(id);
    }

    public void addUserChatRecord(Integer userID, String chatRecord) {

        if (!chatRecords.containsKey(userID))
            chatRecords.put(userID, new LinkedList<String>());
        for (Queue<String> each : chatRecords.values())
            each.offer(userID + ": " + chatRecord);
    }

    public String getUserChatRecord(Integer userID) {

        Queue<String> queue = chatRecords.get(userID);
        StringBuilder chatList = new StringBuilder();
        String each = null;
        while ((each = queue.poll()) != null) {
            chatList.append(each).append("\n");
        }
        queue.clear();
        return chatList.toString();
    }

    public String onlineUsersMessage() {

        List<UserInfo> users = onlineUsers();
        StringBuilder sb = new StringBuilder();
        for (UserInfo each : users) {
            sb.append(each.toMessage()).append("\n");
        }
        return sb.toString();
    }
}
