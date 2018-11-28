package Views;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
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
	// Names are currently only used in generation of username
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
	
	public void loadUI() throws Exception {
		accountForm = new JPanel();
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints formConstraints = new GridBagConstraints();
		formConstraints.weightx = 1.0;
		formConstraints.insets = new Insets(20,20,20,20);
		formConstraints.fill = GridBagConstraints.HORIZONTAL;
		
		JPanel formLeft = new JPanel();
		JPanel formRight = new JPanel();
		BoxLayout twoColumns = new BoxLayout (accountForm, BoxLayout.X_AXIS);
		BoxLayout leftColumn = new BoxLayout (formLeft, BoxLayout.Y_AXIS);
		BoxLayout rightColumn = new BoxLayout (formRight, BoxLayout.Y_AXIS);
		
		accountForm.setLayout(twoColumns);
		formLeft.setLayout(leftColumn);
		formRight.setLayout(rightColumn);
		Dimension textFieldSize = new Dimension (300,20);
		
		// User first name
		JPanel fNamePanel = new JPanel();
		JLabel fNameLabel = new JLabel("First Name: ");
		firstName = new JTextField();
		firstName.setPreferredSize(textFieldSize);
		formConstraints.gridx = 0;
		fNamePanel.add(fNameLabel, formConstraints);
		formConstraints.gridx = 1;
		fNamePanel.add(firstName, formConstraints);
		formRight.add(fNamePanel);
		
		// User second name
		JPanel sNamePanel = new JPanel();
		JLabel sNameLabel = new JLabel("Second Name: ");
		secondName = new JTextField();
		secondName.setPreferredSize(textFieldSize);
		formConstraints.gridx = 0;
		fNamePanel.add(sNameLabel, formConstraints);
		formConstraints.gridx = 1;
		fNamePanel.add(secondName, formConstraints);
		formRight.add(sNamePanel);
		
		// User type
		JPanel typePanel = new JPanel();
		JLabel typeLabel = new JLabel("User Type: ");
		userType = new JComboBox<UserTypes>(UserTypes.values());
		formConstraints.gridx = 0;
		typePanel.add(typeLabel,formConstraints);
		formConstraints.gridx = 1;
		typePanel.add(userType, formConstraints);
		formRight.add(typePanel);
		
		// Password
		JPanel pwdPanel = new JPanel();
		JLabel pwdLabel = new JLabel("Password: ");
		secondName = new JTextField();
		secondName.setPreferredSize(textFieldSize);
		formConstraints.gridx = 0;
		pwdPanel.add(pwdLabel, formConstraints);
		formConstraints.gridx = 1;
		pwdPanel.add(password, formConstraints);
		formLeft.add(pwdPanel);
		
		// Password confirm
		JPanel cPwdPanel = new JPanel();
		JLabel cPwdLabel = new JLabel("Confirm Password: ");
		secondName = new JTextField();
		secondName.setPreferredSize(textFieldSize);
		formConstraints.gridx = 0;
		cPwdPanel.add(cPwdLabel, formConstraints);
		formConstraints.gridx = 1;
		cPwdPanel.add(confirmPassword, formConstraints);
		formLeft.add(cPwdPanel);
		
		accountForm.add(formLeft);
		accountForm.add(formRight);
		
		GridBagConstraints buttonConstraints = new GridBagConstraints();
		buttonConstraints.insets = new Insets(0,5,0,5);		
		buttonConstraints.fill = GridBagConstraints.HORIZONTAL;
		buttonConstraints.gridy = 0;
		buttonConstraints.gridx = 0;
		back = new JButton("Back");
		apply = new JButton("Apply");
		buttonPanel.add(apply, buttonConstraints);
		buttonConstraints.gridx = 1;
		buttonPanel.add(back, buttonConstraints);
		
		buttonConstraints.gridx = 0;
		frame.menuBar.add(buttonPanel);
		frame.add(accountForm);
		frame.showMenuBar();
		frame.revalidate();
		frame.repaint();
	}
}
