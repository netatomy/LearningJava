package zqc.wetalk.ui;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class SwingComponentBuilder {
	public Box createHorizontalLabelPasswordFieldBox(String labelTitle, int labelPreferredWidth, int textFieldColumns, int textFieldPreferredWidth) {
		Box result = Box.createHorizontalBox();
		result.add(createLabel(labelTitle, labelPreferredWidth));
		result.add(Box.createHorizontalStrut(10));
		JPasswordField passwordField = createPasswordField(textFieldColumns,
				textFieldPreferredWidth);
		result.add(passwordField);
		return result;
	}

	public Box createHorizontalLabelTextFieldBox(String labelTitle, int labelPreferredWidth, int passwordFieldColumns, int passwordFieldPreferredWidth) {
		Box result = Box.createHorizontalBox();
		result.add(createLabel(labelTitle, labelPreferredWidth));
		result.add(Box.createHorizontalStrut(10));
		JTextField userNameField = createTextField(passwordFieldColumns,
				passwordFieldPreferredWidth);
		result.add(userNameField);

		return result;
	}

	public JLabel createLabel(String title, int preferredWidth) {
		JLabel result = new JLabel(title, SwingConstants.RIGHT);
		Dimension size = result.getPreferredSize();
		result.setPreferredSize(new Dimension(preferredWidth, size.height));
		return result;
	}

	public JTextField createTextField(int columns, int preferredWidth) {
		JTextField result = new JTextField(columns);
		Dimension size = result.getPreferredSize();
		result.setPreferredSize(new Dimension(preferredWidth, size.height));
		return result;
	}
	
	public JTextField createTextField(int columns, String text, boolean rightAlignment){
	    JTextField result = new JTextField(text, columns);
	    if (rightAlignment)
	        result.setHorizontalAlignment(SwingConstants.RIGHT);
	    return result;
	}

	public JPasswordField createPasswordField(int columns, int preferredWidth) {
		JPasswordField result = new JPasswordField(columns);
		Dimension size = result.getPreferredSize();
		result.setPreferredSize(new Dimension(preferredWidth, size.height));
		return result;
	}

    public JButton createButton(String text, char mnemonic) {

        JButton result = new JButton(text);
        result.setMnemonic(mnemonic);
        return result;
    }
}
