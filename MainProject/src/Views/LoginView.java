package Views;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Controllers.AccountController;
import Controllers.StudentSystemController;
import Models.User;

public class LoginView extends JPanel {
	
	private PrimaryFrame frame;
	
	private JPanel loginForm;
	
	private JButton loginButton;
	private JTextField nameField;
	private JPasswordField passwordField;
	
	public LoginView(PrimaryFrame mf) {
		frame = mf;
	}
	
	public PrimaryFrame getFrame() {
		return frame;
	}
	
	
	public JButton getLoginButton() {
		return loginButton;
	}
	
	public JTextField getNameField() {
		return nameField;
	}
	
	public JPasswordField getPasswordField() {
		return passwordField;
	}
	
	public void viewChange() {
		frame.remove(loginForm);
	}
	
	public void viewLogoutChange() {
		frame.add(loginForm, BorderLayout.CENTER);
		nameField.setText("");
		passwordField.setText("");
		frame.hideMenuBar();
		frame.revalidate();
		frame.repaint();
	}
	
	public void loginUI() {
		// LoginController ls = new LoginController();
		
		loginForm = new JPanel();
		
		loginForm.setLayout(new GridBagLayout());
		GridBagConstraints loginConstraints = new GridBagConstraints();

		loginForm.setBorder(BorderFactory.createEmptyBorder(100, 100, 200, 100));
		JLabel nameLabel = new JLabel("Username: ");
		JLabel passwordLabel = new JLabel("Password: ");
		nameField = new JTextField("",20);
		passwordField = new JPasswordField("",20);
		
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
		loginButton = new JButton("Log in");
		loginButton.setPreferredSize(new Dimension(400,50));
		
		loginConstraints.gridx = 0;
		loginConstraints.gridy = 2;
		loginConstraints.gridwidth = 2;
		loginButton.setPreferredSize(new Dimension(100,50));
		loginForm.add(loginButton, loginConstraints);
		
		frame.add(loginForm, BorderLayout.CENTER);
	}
}
