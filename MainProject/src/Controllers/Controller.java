/**
 * Controller
 * Abstract Class
 * 
 * Parent class for all controllers, contains a user and a method to retrieve that user.
 */

package Controllers;

import Models.User;

public abstract class Controller {
	protected static User user;
	
	public Controller(User mainUser) {
		user = mainUser;
	}
	
	public User getUpdatedUser() {
		return user;
	}
}
