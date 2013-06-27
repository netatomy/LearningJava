package zqc.wechat.server.domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class User {

    private Integer       id;
    private String        userName;
    private String nickName;
    private Queue<String> talkRecords = new LinkedList<>();

    public User(Integer id, String userName) {

        this.id = id;
        this.userName = userName;
    }

    public Integer getID() {

        return id;
    }

    public void setID(Integer id) {

        this.id = id;
    }

    public String getUserName() {

        return userName;
    }

    public void setUserName(String userName) {

        this.userName = userName;
    }

    public synchronized void addTalkRecord(String record) {

        talkRecords.offer(record);
    }

    public synchronized String getTalkRecord() {

        return talkRecords.poll();
    }

}
