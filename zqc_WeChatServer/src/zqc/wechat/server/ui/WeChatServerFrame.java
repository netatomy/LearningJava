package zqc.wechat.server.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import zqc.wechat.server.net.ChatServer;
import zqc.wetalk.ui.SwingComponentBuilder;
import zqc.wetalk.ui.WeChatFrame;

public class WeChatServerFrame extends WeChatFrame {

    private SwingComponentBuilder swingBuilder = new SwingComponentBuilder();
    private ChatServer            server;

    private JButton               startButton;
    private JButton               stopButton;
    private JTextField            portField;

    public WeChatServerFrame() {

        setTitle("微聊服务器 1.0");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(250, 120);
        setResizable(false);

        initComponents();

        pack();
        setScreenCenter();
    }

    private void initComponents() {

        Box mainBox = Box.createVerticalBox();

        JPanel portPanel = new JPanel();
        portPanel.add(new JLabel("端口："));
        portField = swingBuilder.createTextField(5, "5600", true);
        portField.setHorizontalAlignment(JTextField.RIGHT);
        portPanel.add(portField);

        mainBox.add(portPanel);

        JPanel controlPanel = new JPanel();
        controlPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("控制面板"),
                        BorderFactory.createEmptyBorder(10, 20, 10, 20)));

        startButton = swingBuilder.createButton("启动(S)", 'S');
        controlPanel.add(startButton);
        stopButton = swingBuilder.createButton("停止(P)", 'P');
        stopButton.setEnabled(false);
        controlPanel.add(stopButton);

        mainBox.add(controlPanel);

        add(mainBox, BorderLayout.CENTER);

        startButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                startServer();
            }
        });

        stopButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                stopServer();
            }
        });

    }

    private void stopServer() {

        server.stop();
        enableButtons(false);
    }

    private void startServer() {

        try {
            server = new ChatServer(Integer.valueOf(portField.getText()));
            server.start();
            enableButtons(true);
        }
        catch (IOException ex) {
            ex.printStackTrace();
            enableButtons(false);
            JOptionPane.showMessageDialog(this, ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void enableButtons(boolean serverStarted) {

        startButton.setEnabled(!serverStarted);
        stopButton.setEnabled(serverStarted);
        portField.setEditable(!serverStarted);
    }

}
