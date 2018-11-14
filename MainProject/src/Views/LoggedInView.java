package Views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Users.User;

public class LoggedInView {
	public JPanel loggedInUI(User mainUser) {
		JPanel menuPanel = new JPanel();
		
		menuPanel.setLayout(new GridBagLayout());
		GridBagConstraints welcomeConstraints = new GridBagConstraints();

		menuPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 200, 100));
		JLabel welcomeLabel = new JLabel("Welcome: " + mainUser.getUsername());
		
		welcomeConstraints.insets = new Insets(5,5,5,5);
		
		welcomeConstraints.fill = GridBagConstraints.HORIZONTAL;
		welcomeConstraints.gridx = 0;
		welcomeConstraints.gridy = 0;
		menuPanel.add(welcomeLabel, welcomeConstraints);
		
		return null;
	}
}
