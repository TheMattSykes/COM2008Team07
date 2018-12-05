/**
 * User Model
 * 
 * Defines attributes and methods for users.
 */

package Models;

public class User {
	
	// Attributes
	private Boolean loggedIn;
	private int userID;
	private String username;
	private UserTypes userType;
	
	public User() {
		// Not logged in at start
		loggedIn = false;
	}
	
	// polymorphic alternate constructor
	public User(int uID, String name, UserTypes type) {
		userID = uID;
		username = name;
		userType = type;
	}
	
	
	// Get/set methods
	public void setUserDetails(int uID, String name, UserTypes type) {
		userID = uID;
		username = name;
		userType = type;
	}

	public int getUserID() {
		return userID;
	}
	
	public String getUsername() {
		return username;
	}
	
	public UserTypes getUserType() {
		return userType;
	}
	
	// Check if user is logged in
	public Boolean isLoggedIn() {
		return loggedIn;
	}
	
	// Login and logout methods
	public void login() {
		loggedIn = true;
	}
	
	public void logout() {
		loggedIn = false;
	}
}
