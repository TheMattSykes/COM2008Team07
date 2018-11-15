package Controllers;
import javax.swing.*;

import Models.User;
import Views.LoggedInView;
import Views.LoginView;
import Views.PrimaryFrame;

import java.awt.*;

public class GraphicsController {
	
	public void runUI(User mainUser) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				LoggedInView menuView = new LoggedInView();
				
				JPanel menuUI = menuView.loggedInUI(mainUser);
				
				PrimaryFrame mainFrame = new PrimaryFrame("University System");
				
				LoginView lv = new LoginView(mainFrame);
				LoginController lc = new LoginController(mainUser, lv);
				lc.initController();
				
				mainFrame.setSize(1000,800);
			}
		});
	}
	
	
}
