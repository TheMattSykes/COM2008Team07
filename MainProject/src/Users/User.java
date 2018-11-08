package Users;

public class User {
	private Boolean loggedIn;
	private int userID;
	private String username;
	private UserTypes userType;
	
	public User() {
		loggedIn = false;
	}
	
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
	
	public Boolean isLoggedIn() {
		return loggedIn;
	}
	
	public void login() {
		loggedIn = true;
	}
	
	public void logout() {
		loggedIn = false;
	}
}