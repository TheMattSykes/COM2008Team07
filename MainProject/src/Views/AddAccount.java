package Views;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Models.User;
import Models.UserTypes;

public class AddAccount extends JPanel {
	private static final long serialVersionUID = 1L;
	PrimaryFrame frame;
	private JPanel accountForm;
	private JPanel buttonPanel;
	private JButton apply;
	private JButton back;
	private JTextField firstName;
	private JTextField secondName;
	private JComboBox<UserTypes> userType;
	private JPasswordField password;
	private JPasswordField confirmPassword;
	
	public AddAccount(PrimaryFrame pf) {
		frame = pf;
	}
	
	public PrimaryFrame getFrame() {
		return frame;
	}
	
	public void viewChange() {
		frame.getContentPane().removeAll();
	}
	
	public void removeUI() {
		if (accountForm != null) {
			frame.remove(accountForm);
		}
		if (buttonPanel != null) {
			frame.menuBar.remove(buttonPanel);
		}
	}
	
	public JButton getBackButton() {
		return back;
	}
	
	public JButton getApplyButton() {
		return apply;
	}
	
	public String[] getDetails() {
		String[] values = new String[] {
				firstName.getText().trim(),
				secondName.getText().trim(),
				userType.getSelectedItem().toString(),
				password.getPassword().toString(),
				confirmPassword.getPassword().toString()
		};
		return values;
	}
	
	
}
