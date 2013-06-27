package zqc.wechat.client.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.*;

import zqc.wechat.client.WeChatClientApp;
import zqc.wechat.client.net.ChatClient;
import zqc.wetalk.UserInfo;
import zqc.wetalk.ui.WeChatFrame;

public class ChattingFrame extends WeChatFrame {

    private JList          onlineUserList;
    private JTextArea      chatArea;
    private JTextField     messageField;
    private JButton        sendButton;
    private boolean        keepReceiving;
    private Timer          timer;
    private List<UserInfo> onlineUsers = new ArrayList<>();

    public ChattingFrame() {

        setTitle("微聊 - 聊天室");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        setSize(800, 600);

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                confirmClose();
            }

            @Override
            public void windowOpened(WindowEvent e) {

                startTimedReceiveChatRecord();
            }
        });

        initComponents();

        pack();

    }

    protected void startTimedReceiveChatRecord() {

        timer = new Timer(5000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                timerAction();
            }
        });

        timer.start();

    }

    private void timerAction() {

        try {
            ChatClient client = new ChatClient(WeChatClientApp.SERVER_NAME, WeChatClientApp.SERVER_PORT);
            try {
                client.connect();
                client.send("RECORD\n" + WeChatClientApp.getOnlineUser().getID() + "\n");
                String receive = client.receive();
                Scanner scanner = new Scanner(receive);
                String header = scanner.nextLine();
                while (scanner.hasNextLine()) {
                    chatArea.append(scanner.nextLine() + "\n");
                }
            }
            finally {
                client.close();
            }

            try {
                client.connect();
                client.send("ONLINEUSERS\n" + WeChatClientApp.getOnlineUser().getID() + "\n");
                String receive = client.receive();
                Scanner scanner = new Scanner(receive);
                String header = scanner.nextLine();
                onlineUsers.clear();
                DefaultListModel listModel = new DefaultListModel();
                while (scanner.hasNextLine()) {
                    UserInfo ui = UserInfo.parse(scanner.nextLine());
                    onlineUsers.add(ui);
                    listModel.addElement(ui.getNickName());
                }
                onlineUserList.setModel(listModel);

            }
            finally {
                client.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void confirmClose() {

        String receive = null;
        if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "你确定要退出聊天室吗？\n这将注销登录。", "退出确认",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)) {
            try {
                ChatClient client = new ChatClient(WeChatClientApp.SERVER_NAME, WeChatClientApp.SERVER_PORT);
                try {
                    client.connect();
                    client.send("LOGOUT\n" + WeChatClientApp.getOnlineUser().getID() + "\n");
                    receive = client.receive();
                }
                finally {
                    client.close();
                }

            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }

            Scanner scanner = new Scanner(receive);
            String header = scanner.nextLine();
            String content = scanner.nextLine();
            WeChatClientApp.setOnlineUser(null);
            timer.stop();
            dispose();
        }

    }

    private void initComponents() {

        Box onlineUsersBox = Box.createVerticalBox();
        onlineUsersBox.setBorder(BorderFactory.createEtchedBorder());
        onlineUsersBox.add(swingBuilder.createLabel("在线用户", 50));
        onlineUsersBox.add(Box.createVerticalStrut(5));
        onlineUserList = new JList(new String[] { "Jane", "zqc", "Obama", "Gates", "Jobs" });
        onlineUsersBox.add(new JScrollPane(onlineUserList));

        Box chattingBox = Box.createVerticalBox();
        Box horizontalBox = Box.createHorizontalBox();
        horizontalBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        horizontalBox.add(new JLabel("聊天记录"));
        horizontalBox.add(Box.createHorizontalGlue());
        chattingBox.add(horizontalBox);

        chatArea = new JTextArea(25, 50);
        chatArea.setLineWrap(true);
        chattingBox.add(new JScrollPane(chatArea));
        chattingBox.add(Box.createVerticalGlue());

        Box messageBox = Box.createHorizontalBox();
        messageField = new JTextField(20);
        messageField.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    sendMessage();
            }
        });
        messageBox.add(messageField);
        sendButton = new JButton("发送");
        sendButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                sendMessage();
            }
        });

        messageBox.add(sendButton);

        chattingBox.add(messageBox);

        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, chattingBox, onlineUsersBox);
        add(sp);
    }

    private void sendMessage() {

        try {
            ChatClient client = new ChatClient(WeChatClientApp.SERVER_NAME, WeChatClientApp.SERVER_PORT);
            try {
                client.connect();
                client.send("TALK\n" + WeChatClientApp.getOnlineUser().getID() + "\n" + messageField.getText() + "\n");
                String receive = client.receive();
                // JOptionPane.showMessageDialog(this, receive);
                messageField.requestFocus();
                messageField.setText(null);
            }
            finally {
                client.close();
            }
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    // public static void main(String[] args){
    // ChattingFrame f = new ChattingFrame();
    // f.setVisible(true);
    // }
}
