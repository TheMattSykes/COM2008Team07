package Views;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Models.LoginModel;
import Users.User;

public class LoginView {
	public JPanel loginUI(User mainUser) {
		LoginModel ls = new LoginModel();
		
		JPanel loginForm = new JPanel();
		
		loginForm.setLayout(new GridBagLayout());
		GridBagConstraints loginConstraints = new GridBagConstraints();

		loginForm.setBorder(BorderFactory.createEmptyBorder(100, 100, 200, 100));
		JLabel nameLabel = new JLabel("Username: ");
		JLabel passwordLabel = new JLabel("Password: ");
		JTextField nameField = new JTextField("",20);
		JPasswordField passwordField = new JPasswordField("",20);
		
		loginConstraints.insets = new Insets(5,5,5,5);
		
		loginConstraints.fill = GridBagConstraints.HORIZONTAL;
		loginConstraints.gridx = 0;
		loginConstraints.gridy = 0;
		loginForm.add(nameLabel, loginConstraints);
		
		loginConstraints.gridx = 1;
		loginConstraints.gridy = 0;
		loginForm.add(nameField, loginConstraints);
		
		loginConstraints.gridx = 0;
		loginConstraints.gridy = 1;
		loginForm.add(passwordLabel, loginConstraints);
		
		loginConstraints.gridx = 1;
		loginConstraints.gridy = 1;
		loginForm.add(passwordField, loginConstraints);
		
		loginConstraints.insets = new Insets(20,50,5,50);
		JButton loginButton = new JButton("Log in");
		loginButton.setPreferredSize(new Dimension(400,50));
		
		// Create action for login authentication
		Action loginAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e)
			{
				try {
					ls.loginChecker(mainUser, nameField,passwordField);
				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		};
		
		// Add action listeners
		loginButton.addActionListener(loginAction);
		passwordField.addActionListener(loginAction);
		
		loginConstraints.gridx = 0;
		loginConstraints.gridy = 2;
		loginConstraints.gridwidth = 2;
		loginButton.setPreferredSize(new Dimension(100,50));
		loginForm.add(loginButton, loginConstraints);
		
		return loginForm;
	}
}
