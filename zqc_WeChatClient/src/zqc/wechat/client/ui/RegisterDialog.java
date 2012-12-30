package zqc.wechat.client.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.StringReader;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import zqc.wechat.client.WeChatClientApp;
import zqc.wechat.client.net.ChatClient;
import zqc.wetalk.RegisterInfo;
import zqc.wetalk.ui.SwingComponentBuilder;

public class RegisterDialog extends JDialog {
	private SwingComponentBuilder swingBuilder = new SwingComponentBuilder();
	private boolean modalResult;

	private static final int LABEL_PREFERRED_WIDTH = 70;
	private static final int TEXTFIELD_PREFERRED_WIDTH = 900;
	private static final int TEXTFIELD_COLUMNS = 15;
	private JTextField userNameField;
	private JPasswordField passwordField;
	private JPasswordField confirmPasswordField;
	private JTextField nickNameField;

	public RegisterDialog(JFrame owner) {
		super(owner);
		setTitle("注册新用户");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(600, 480);

		initializeComponents();
	}

	private void initializeComponents() {

		Box regBox = Box.createVerticalBox();
		regBox.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 10, 10, 10),
				BorderFactory.createCompoundBorder(
						BorderFactory.createTitledBorder("用户信息"),
						BorderFactory.createEmptyBorder(10, 10, 10, 10))));

		Box userNameBox = Box.createHorizontalBox();
		userNameBox
				.add(swingBuilder.createLabel("用户名：", LABEL_PREFERRED_WIDTH));
		userNameField = swingBuilder.createTextField(TEXTFIELD_COLUMNS,
				TEXTFIELD_PREFERRED_WIDTH);
		userNameBox.add(userNameField);
		regBox.add(userNameBox);

		regBox.add(Box.createVerticalStrut(10));

		Box passwordBox = Box.createHorizontalBox();
		passwordBox.add(swingBuilder.createLabel("密码：", LABEL_PREFERRED_WIDTH));
		passwordField = swingBuilder.createPasswordField(TEXTFIELD_COLUMNS,
				TEXTFIELD_PREFERRED_WIDTH);
		passwordBox.add(passwordField);
		regBox.add(passwordBox);

		regBox.add(Box.createVerticalStrut(10));

		Box confirmPasswordBox = Box.createHorizontalBox();
		confirmPasswordBox.add(swingBuilder.createLabel("确认密码：",
				LABEL_PREFERRED_WIDTH));
		confirmPasswordField = swingBuilder.createPasswordField(
				TEXTFIELD_COLUMNS, TEXTFIELD_PREFERRED_WIDTH);
		confirmPasswordBox.add(confirmPasswordField);
		regBox.add(confirmPasswordBox);

		regBox.add(Box.createVerticalStrut(10));

		Box nickNameBox = Box.createHorizontalBox();
		nickNameBox.add(swingBuilder.createLabel("昵称：", LABEL_PREFERRED_WIDTH));
		nickNameField = swingBuilder.createTextField(TEXTFIELD_COLUMNS,
				TEXTFIELD_PREFERRED_WIDTH);
		nickNameBox.add(nickNameField);
		regBox.add(nickNameBox);

		Box buttonBox = Box.createHorizontalBox();
		buttonBox.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 12));
		JButton okButton = new JButton("注册");
		JButton cancelButton = new JButton("取消");

		buttonBox.add(Box.createHorizontalGlue());
		buttonBox.add(okButton);
		buttonBox.add(Box.createHorizontalStrut(10));
		buttonBox.add(cancelButton);

		add(regBox, BorderLayout.CENTER);
		add(buttonBox, BorderLayout.SOUTH);
		
		
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				register();
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cancel();
			}
		});
	}

	public boolean showDialog() {
		pack();
		setLocationRelativeTo(getOwner());
		setModal(true);
		setVisible(true);
		return modalResult;
	}

	private RegisterInfo getRegisterInfo() {

		RegisterInfo result = new RegisterInfo(userNameField.getText(),
				new String(passwordField.getPassword()), new String(
						confirmPasswordField.getPassword()),
				nickNameField.getText());

		return result;
	}

	private void checkInput(){
		if (0 == userNameField.getText().trim().length()){
			JOptionPane.showMessageDialog(this, "用户名不能为空", "警告", JOptionPane.WARNING_MESSAGE);
			userNameField.requestFocus();
			return ;
		}
		
		if (0 == passwordField.getPassword().length){
			JOptionPane.showMessageDialog(this, "密码不能为空", "警告", JOptionPane.WARNING_MESSAGE);
			passwordField.requestFocus();
			return ;
		}
		
		if (!new String(confirmPasswordField.getPassword()).equals(new String(passwordField.getPassword()))){
			JOptionPane.showMessageDialog(this, "两次密码不相同", "警告", JOptionPane.WARNING_MESSAGE);
			passwordField.requestFocus();
			return ;
		}
			
		if (0 == nickNameField.getText().trim().length()){
			JOptionPane.showMessageDialog(this, "昵称不能为空", "警告", JOptionPane.WARNING_MESSAGE);
			nickNameField.requestFocus();
			return ;
		}
	}
	
	private void cancel() {
		setVisible(false);
	}

	private void register() {
		checkInput();
		RegisterInfo regInfo = getRegisterInfo();
		try {
			ChatClient client = new ChatClient(WeChatClientApp.SERVER_NAME,
					WeChatClientApp.SERVER_PORT);
			try {
				client.connect();
				client.send("REGISTER\n" + regInfo.toMessage() + "\n");
				String response = client.receive();
				BufferedReader reader = new BufferedReader(new StringReader(
						response));
				String header = reader.readLine();

				int messageType = JOptionPane.INFORMATION_MESSAGE;

				if ("SUCCESS".equals(header)) {
					modalResult = true;
					messageType = JOptionPane.ERROR_MESSAGE;
				}

				JOptionPane.showMessageDialog(this, reader.readLine(), header,
						messageType);
				
				modalResult = true;
			} finally {
				client.close();
				
				setVisible(false);
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}

}
