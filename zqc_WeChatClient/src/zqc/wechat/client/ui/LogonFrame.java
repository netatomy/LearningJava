package zqc.wechat.client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import zqc.wechat.client.WeChatClientApp;
import zqc.wechat.client.net.ChatClient;
import zqc.wetalk.LogonInfo;
import zqc.wetalk.UserInfo;
import zqc.wetalk.ui.SwingComponentBuilder;
import zqc.wetalk.ui.WeChatFrame;

public class LogonFrame extends WeChatFrame {

    private JTextField            userNameField;
    private JPasswordField        passwordField;
    private JButton               logonButton;
    private JButton               exitButton;
    private JButton               registerButton;

    public LogonFrame() {

        setTitle("微聊 1.0");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // setSize(400, 320);
        initializeComponents();

        pack();
        setResizable(false);
    }

    private void initializeComponents() {

        Box logonBox = Box.createVerticalBox();
        logonBox.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(10, 10, 10, 10),
                        BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(),
                                        BorderFactory.createEmptyBorder(10, 10, 10, 10))));

        Box userNameBox = Box.createHorizontalBox();
        userNameBox.add(swingBuilder.createLabel("用户名：", 60));
        userNameField = swingBuilder.createTextField(15, 200);
        userNameBox.add(userNameField);
        logonBox.add(userNameBox);

        logonBox.add(Box.createVerticalStrut(10));

        Box passwordBox = Box.createHorizontalBox();
        passwordBox.add(swingBuilder.createLabel("密码：", 60));
        passwordField = swingBuilder.createPasswordField(15, 200);
        passwordBox.add(passwordField);
        logonBox.add(passwordBox);

        Box buttonBox = Box.createHorizontalBox();
        buttonBox.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        registerButton = new JButton("注册新用户");
        registerButton.setForeground(Color.RED);
        buttonBox.add(registerButton);
        buttonBox.add(Box.createHorizontalGlue());
        logonButton = new JButton("登录");
        buttonBox.add(logonButton);
        buttonBox.add(Box.createHorizontalStrut(5));
        exitButton = new JButton("退出");
        buttonBox.add(exitButton);

        registerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                register();
            }
        });
        logonButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                logon();
            }
        });

        exitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();
            }
        });

        add(logonBox, BorderLayout.CENTER);
        add(buttonBox, BorderLayout.SOUTH);

    }

    protected void register() {

        RegisterDialog regDialog = new RegisterDialog(this);
        // setVisible(false);
        // setExtendedState(ICONIFIED);
        String title = getTitle();
        setTitle(regDialog.getTitle());
        regDialog.showDialog();
        regDialog.dispose();
        // setVisible(true);
        // setExtendedState(NORMAL);
        setTitle(title);
    }

    protected void exit() {

        dispose();
    }

    protected void logon() {

        LogonInfo logonInfo = new LogonInfo(userNameField.getText().trim(), new String(passwordField.getPassword()));
        String receive = null;
        try {
            ChatClient client = new ChatClient(WeChatClientApp.SERVER_NAME, WeChatClientApp.SERVER_PORT);
            try {
                client.connect();
                client.send("LOGON\n" + logonInfo.toMessage() + "\n");
                receive = client.receive();
                // JOptionPane.showMessageDialog(this, receive);
            }
            finally {
                client.close();
            }

        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }

        Scanner scanner = new Scanner(receive);
        String header = scanner.nextLine();
        String content = scanner.nextLine();
        if ("FAIL".equals(header)) {
            JOptionPane.showMessageDialog(this, content);
            return;
        }
        WeChatClientApp.setOnlineUser(UserInfo.parse(content));
        ChattingFrame f = new ChattingFrame();
        f.setVisible(true);
        setVisible(false);
        f.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {

                setVisible(true);
            }
        });
    }

}
