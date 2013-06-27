package zqc.wetalk.client.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class WeTalkClientFrame extends JFrame {

    public WeTalkClientFrame() throws HeadlessException {

        setTitle("WeTalk Client (DEMO)");
        setSize(600, 480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initComponents();
    }

    private void initComponents() {

        JMenuBar menuBar = buildMenuBar();
        setJMenuBar(menuBar);
        JPanel messagePanel = buildMessagePanel();
        JPanel onlineUsersPanel = buildOnlineUsersPanel();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, messagePanel, onlineUsersPanel);
        splitPane.setResizeWeight(0.8);
        add(splitPane, BorderLayout.CENTER);
    }

    private JPanel buildOnlineUsersPanel() {
        JPanel panel = new JPanel(new BorderLayout(2, 1));
        
        JLabel label = new JLabel("Online Users:");
        JList<String> onlineUsersList = new JList<>(new String[]{"Black Obama", "Bill Gates", "Mark Zackberger", "Kent Beck"});
        
        panel.add(label, BorderLayout.NORTH);
        panel.add(new JScrollPane(onlineUsersList));
        
        return panel;
    }

    private JPanel buildMessagePanel() {

        JPanel panel = new JPanel(new BorderLayout());
        JTextArea messagesTextArea = new JTextArea(20, 25);
        messagesTextArea.setEditable(false);
        panel.add(new JScrollPane(messagesTextArea), BorderLayout.CENTER);

        JPanel sendPanel = new JPanel();
        JTextField messageTextField = new JTextField(35);
        messageTextField.setToolTipText("Type message here");
        sendPanel.add(messageTextField);
        JButton sendButton = new JButton("Send"); 
        sendButton.setToolTipText("Send message immeidately");
        sendPanel.add(sendButton);
        panel.add(sendPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    /**
     * 构建客户端界面菜单
     */
    private JMenuBar buildMenuBar() {

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = createMenu("File", KeyEvent.VK_F, null);
        JMenu editMenu = createMenu("Edit", KeyEvent.VK_E, null);
        JMenu viewMenu = createMenu("View", KeyEvent.VK_V, null);
        JMenu setupMenu = createMenu("Setup", KeyEvent.VK_S, null);
        JMenu helpMenu = createMenu("Help", KeyEvent.VK_H, null);
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);
        menuBar.add(setupMenu);
        menuBar.add(helpMenu);

        // File Menu
        JMenuItem logonMenuItem = createMenuItem("Logon...", KeyEvent.VK_L, "Logon WeTalk Server",
                        KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_MASK));
        logonMenuItem.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
            
                switch (LogonDialog.showDialog(null)){
                case OK:
                    JOptionPane.showMessageDialog(null, "Authenticating...");
                    break;
                case CANCEL:
                    dispose();
                    break;
                }
            }
        });
        fileMenu.add(logonMenuItem);
        JMenuItem logoutMenuItem = createMenuItem("Logout", KeyEvent.VK_O, "Logout WeTalk Server",
                        KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
        fileMenu.add(logoutMenuItem);
        fileMenu.addSeparator();
        JMenuItem registerMenuItem = createMenuItem("Register...", KeyEvent.VK_R, "Register a new account",
                        KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_MASK));
        fileMenu.add(registerMenuItem);

        fileMenu.addSeparator();
        JMenuItem exitMenuItem = createMenuItem("Exit", KeyEvent.VK_X, "Exit WeTalk Client",
                        KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.ALT_MASK));
        fileMenu.add(exitMenuItem);

        // Edit Menu
        JMenuItem cutMenuItem = createMenuItem("Cut", KeyEvent.VK_T, "Cut text",
                        KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_MASK));
        JMenuItem copyMenuItem = createMenuItem("Copy", KeyEvent.VK_C, "Copy text",
                        KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK));
        JMenuItem pasteMenuItem = createMenuItem("Paste", KeyEvent.VK_P, "Paste text",
                        KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK));
        JMenuItem selectAllMenuItem = createMenuItem("Select All", KeyEvent.VK_A, "Select all text",
                        KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_MASK));

        editMenu.add(cutMenuItem);
        editMenu.add(copyMenuItem);
        editMenu.add(pasteMenuItem);
        editMenu.add(selectAllMenuItem);

        // View Menu

        // Setup Menu

        // Help Menu
        JMenuItem aboutMenuItem = createMenuItem("About", KeyEvent.VK_A, "About WeTalk Client", null);
        helpMenu.add(aboutMenuItem);

        return menuBar;
    }

    private JMenu createMenu(String text, int mnemonic, String toolTipText) {

        JMenu menu = new JMenu(text);
        menu.setMnemonic(mnemonic);
        menu.setToolTipText(toolTipText);
        return menu;
    }

    private JMenuItem createMenuItem(String text, int mnemonic, String toolTipText, KeyStroke keyStroke) {

        JMenuItem menuItem = new JMenuItem(text, mnemonic);
        menuItem.setToolTipText(toolTipText);
        if (keyStroke != null)
            menuItem.setAccelerator(keyStroke);
        return menuItem;
    }

}
