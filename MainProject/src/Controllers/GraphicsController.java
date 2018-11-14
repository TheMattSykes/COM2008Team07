package Controllers;
import javax.swing.*;

import Users.User;
import Views.LoggedInView;
import Views.LoginView;
import Views.PrimaryFrame;

import java.awt.*;

public class GraphicsController {
	
	public void runUI(User mainUser) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Instantiate objects
				LoginView loginView = new LoginView();
				LoggedInView menuView = new LoggedInView();
				
				JPanel loginUI = loginView.loginUI(mainUser);
				JPanel menuUI = menuView.loggedInUI(mainUser);
				
				PrimaryFrame mainFrame;
				
				if (!mainUser.isLoggedIn()) {
					// Create main frame for UI
					mainFrame = new PrimaryFrame("University System", loginUI);
				} else {
					mainFrame = new PrimaryFrame("University System", menuUI);
				}
				
				// Set frame properties
				mainFrame.setSize(1000,800);
				mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				mainFrame.setVisible(true);
				mainFrame.setResizable(false);
				
				Toolkit tk = Toolkit.getDefaultToolkit();
				Dimension screenSize = tk.getScreenSize();
				mainFrame.setLocation(new Point(screenSize.width/4,screenSize.height/8));
			}
		});
	}
}
