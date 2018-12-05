/**
 * MasterController
 * 
 * Instantiates a login controller which manages further controllers.
 * Sets the frame size.
 */

package Controllers;
import javax.swing.*;

import Models.User;
import Views.LoggedInView;
import Views.LoginView;
import Views.PrimaryFrame;

public class MasterController {
	
	/**
	 * Start UI from relevant controllers
	 * @param mainUser
	 */
	public void runUI(User mainUser) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				LoggedInView menuView = new LoggedInView();
				
				menuView.loggedInUI(mainUser);
				
				PrimaryFrame mainFrame = new PrimaryFrame("University System");
				
				LoginView lv = new LoginView(mainFrame);
				AccountController ac = new AccountController(mainUser, lv);
				ac.initController();
				
				mainFrame.setSize(1000,800);
			}
		});
	}
	
	
}
