package zqc.wetalk.server.ui;

import javax.swing.*;
import javax.swing.text.JTextComponent.KeyBinding;

import java.awt.*;
import java.awt.event.*;

public class WeTalkServerFrame extends JFrame {

    private static final String ImageIconFileName = "wetalk.jpg";

    private JMenuBar            menuBar;
    private JMenu               fileMenu;
    private JMenu               setupMenu;
    private JMenuItem           exitMenuItem;
    private JMenuItem           startMenuItem;
    private JMenuItem           stopMenuItem;

    private JSplitPane          splitPane;

    private JTabbedPane         controlTabbedPane;
    private JPanel              controlPanel;
    private JPanel              logPanel;

    private JButton             startButton;
    private JButton             stopButton;
    private JButton             exitButton;

    private JPanel              onlineUsersPanel;
    private JLabel              onlineUsersLabel;
    private JList<String>       onlineUsersList;

    private ButtonAction        exitAction;
    private ButtonAction        fileAction;
    private ButtonAction        controlAction;
    private ButtonAction        startAction;
    private ButtonAction        stopAction;

    public WeTalkServerFrame() {

        setTitle("WeTalk Server (DEMO)");
        setIconImage(Toolkit.getDefaultToolkit().getImage(ImageIconFileName));
        setSize(new Dimension(600, 480));
        setMinimumSize(new Dimension(480, 400));
        //setMaximumSize(new Dimension(800, 600));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        initCompononents();
    }

    private void initCompononents() {

        buildActions();
        buildContainers();
        buildMenuAndToolBar();
        // buildStatusBar();
        buildControlTabbedPane();
        buildOnlineList();
    }

    private void buildActions() {

        fileAction = new ButtonAction("File", "File Menu", KeyEvent.VK_F);
        exitAction = new ButtonAction("Exit", "Exit WeTalk Server", KeyEvent.VK_X) {

            @Override
            public void actionPerformed(ActionEvent e) {

                exit();
            }
        };

        controlAction = new MenuItemAction("Control", "Control Menu", KeyEvent.VK_C);
        startAction = new MenuItemAction("Start", "Start WeTalk Server", KeyEvent.VK_S);
        stopAction = new MenuItemAction("Stop", "Stop WeTalk Server", KeyEvent.VK_P);
    }

    private void buildContainers() {

        controlTabbedPane = new JTabbedPane();
        onlineUsersPanel = new JPanel(new BorderLayout());
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, controlTabbedPane, onlineUsersPanel);
        splitPane.setResizeWeight(0.8);
        add(splitPane, BorderLayout.CENTER);
    }

    private void buildControlTabbedPane() {

        controlPanel = new JPanel(new GridLayout(2, 1, 20, 20));
        startButton = new JButton("Start");
        controlPanel.add(startButton);
        stopButton = new JButton("Stop");
        controlPanel.add(stopButton);
        controlTabbedPane.add("Control", controlPanel);

        logPanel = new JPanel(new BorderLayout());
        controlTabbedPane.add("Log", logPanel);
    }

    private void buildOnlineList() {

        onlineUsersLabel = new JLabel("Online Users");
        onlineUsersPanel.add(onlineUsersLabel, BorderLayout.NORTH);

        onlineUsersList = new JList<String>(new String[] { "Balack Obama", "Bill Gates", "James Gosling",
                        "Anders Hijsberg", "Larry Page", "Steve Jobs", "Kent Beck", "Martin Fowler" });

        onlineUsersPanel.add(new JScrollPane(onlineUsersList));
    }

    private void buildMenuAndToolBar() {

        menuBar = new JMenuBar();

        fileMenu = new JMenu(fileAction);
        exitMenuItem = new JMenuItem(new MenuItemAction(exitAction, KeyStroke.getKeyStroke(KeyEvent.VK_X,
                        KeyEvent.ALT_MASK)));

        fileMenu.add(exitAction);
        menuBar.add(fileMenu);

        JMenu controlMenu = new JMenu(controlAction);
        JMenuItem startMenuItem = new JMenuItem(startAction);
        controlMenu.add(startMenuItem);
        JMenuItem stopMenuItem = new JMenuItem(stopAction);
        controlMenu.add(stopMenuItem);

        menuBar.add(controlMenu);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutMenuItem = new JMenuItem("About WeTalk Server");
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        this.setJMenuBar(menuBar);

        JToolBar toolBar = new JToolBar("ToolBar");

        JButton startButton = new JButton(startAction);
        toolBar.add(startButton);

        JButton stopButton = new JButton(stopAction);
        toolBar.add(stopButton);
        toolBar.addSeparator();
        JButton exitButton = new JButton(exitAction);
        toolBar.add(exitAction);

        add(toolBar, BorderLayout.PAGE_START);
    }

    private void exit() {

        this.dispose();
        // JOptionPane.showMessageDialog(null, "ExitAction");
    }

    class ButtonAction extends AbstractAction {

        // private Action innerAction;

        protected ButtonAction(ButtonAction buttonAction) {

            // innerAction = buttonAction;
            for (Object key : buttonAction.getKeys()) {
                putValue((String) key, buttonAction.getValue((String) key));
            }
        }

        public ButtonAction(String text, String toolTipText) {

            this(text, toolTipText, 0);
        }

        public ButtonAction(String text, String toolTipText, int mnemonic) {

            super(text);
            putValue(SHORT_DESCRIPTION, toolTipText);
            putValue(MNEMONIC_KEY, mnemonic);
        }

        public ButtonAction(String text, String toolTipText, int mnemonic, KeyStroke keyStroke) {

            super(text);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            // DO NOTHING
        }
    }

    class MenuItemAction extends ButtonAction {

        public MenuItemAction(String text, String toolTipText, int mnemonic, KeyStroke keyStroke) {

            super(text, toolTipText, mnemonic, keyStroke);
        }

        public MenuItemAction(String text, String toolTipText, int mnemonic) {

            super(text, toolTipText, mnemonic);
        }

        public MenuItemAction(String text, String toolTipText) {

            super(text, toolTipText);
        }

        public MenuItemAction(ButtonAction buttonAction, KeyStroke keyStroke) {

            super(buttonAction);
            putValue(ACCELERATOR_KEY, keyStroke);
        }

        public MenuItemAction(ButtonAction buttonAction, String key, Object value) {

            super(buttonAction);
            putValue(key, value);
        }
    }

    // class ExitAction extends ButtonAction {
    //
    // public ExitAction(String text, String toolTipText, int mnemonic,
    // KeyStroke keyStroke) {
    //
    // super(text, toolTipText, mnemonic, keyStroke);
    // }
    //
    // @Override
    // public void actionPerformed(ActionEvent e) {
    //
    // exit();
    // }
    //
    // }

}
