package zqc.wetalk.client.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class LogonDialog extends JDialog {

    public LogonDialog() {

        this(null);
    }

    public LogonDialog(JFrame owner) {

        super(owner, "WeTalk Logon");
        
        setSize(240, 180);
        setLocationRelativeTo(owner);
        //setLayout(new GridLayout(3, 1));
        
        initComponents();
    }
    
    private void initComponents(){
        JLabel messageLabel = new JLabel("WeTalk Logon", SwingConstants.CENTER);
        
        JPanel logonPanel = new JPanel(new GridLayout(3, 1));
        JPanel userNamePanel = new JPanel();
        
        JLabel userNameLabel = new JLabel("User Name:", SwingConstants.RIGHT);
        JTextField userNameTextField = new JTextField(10);
        userNamePanel.add(userNameLabel);
        userNamePanel.add(userNameTextField);
        
        JPanel passwordPanel = new JPanel();
        JLabel passwordLabel = new JLabel("Password:", SwingConstants.RIGHT);
        JPasswordField passwordField = new JPasswordField(10);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        
        JPanel buttonsPanel = new JPanel();
        JButton okButton = new JButton("Logon");
        okButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                state = LogonDialogResult.OK;
                dispose();
            }
        });
        JButton cancelButton = new JButton("Exit");
        cancelButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
            
                dispose();
            }
        });
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);
        
        
        logonPanel.add(userNamePanel);
        logonPanel.add(passwordPanel);
        logonPanel.add(buttonsPanel);
        
        add(messageLabel, BorderLayout.NORTH);
        add(logonPanel);
    }
    
    public static LogonDialogResult showDialog(JFrame owner){
        LogonDialog dlg = new LogonDialog(owner);
        dlg.setModal(true);
        dlg.setVisible(true);
        return dlg.state;
    }
    
    private LogonDialogResult state = LogonDialogResult.CANCEL;
    
    public enum LogonDialogResult { OK, CANCEL} 

}
