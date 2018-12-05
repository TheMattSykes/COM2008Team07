package Views;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * LoginView
 * Defines the view all users of the system see at the start, before logging in
 */
public class LoginView extends JPanel {
	private static final long serialVersionUID = 1L;

	private PrimaryFrame frame;
	
	private JPanel loginForm;
	
	private JButton loginButton;
	private JTextField nameField;
	private JPasswordField passwordField;
	
	public LoginView(PrimaryFrame mf) {
		frame = mf;
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
	
	// Get/Set methods
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
	
	/**
	 * loginUI
	 * Load and define the UI for logging in
	 */
	public void loginUI() {		
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
