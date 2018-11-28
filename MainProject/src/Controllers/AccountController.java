package Controllers;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
// import javax.xml.bind.DatatypeConverter;

import Models.User;
import Models.UserTypes;
import Views.LoginView;
import Views.PrimaryFrame;
import Views.StudentView;
import Views.RegistrarView;
import Views.AdminView;
import Views.TeacherView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class AccountController extends Controller {

	private static User user;
	private LoginView lv;
	private JPanel menuBar;
	
	private StudentView studentViewer = null;
	private RegistrarView registrarViewer = null;
	private AdminView adminViewer = null;
	private TeacherView teacherViewer = null;

	public AccountController(User mainUser, LoginView lview) {
		user = mainUser;
		lv = lview;
		
		initView();
	}
	
	public User getUpdatedUser() {
		return user;
	}
	
	public void initView() {
		lv.loginUI();
	}
	
	public void initController() {		
		lv.getLoginButton().addActionListener(e -> loginEvent());;
		
		lv.getPasswordField().addActionListener(e -> loginEvent());
		
		PrimaryFrame frame = lv.getFrame();
		JButton logout = frame.getLogoutButton();
		
		logout.addActionListener(e -> {
			try {
				logoutEvent();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});;
	}
	
	public void logoutEvent() throws Exception {
		Object[] options = {"Logout", "Cancel"};
		
		int logoutOption = JOptionPane.showOptionDialog(lv.getFrame(), "Confirm logout", "Logout question", JOptionPane.YES_NO_OPTION, 
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		
		if (logoutOption == 0) {
			user.logout();
			changeView();
		}
	}
	
	public void changeView() throws Exception {
		if (user.isLoggedIn()) {
			lv.viewChange();
			
			if (user.getUserType() == UserTypes.STUDENT) {
				studentViewer = new StudentView(lv.getFrame());
				StudentSystemController sc = new StudentSystemController(user, studentViewer);
				sc.initController();
			} else if (user.getUserType() == UserTypes.REGISTRAR) {
				registrarViewer = new RegistrarView(lv.getFrame());
				RegistrarSystemController rc = new RegistrarSystemController(user, registrarViewer);
				rc.initController();
			} else if (user.getUserType() == UserTypes.ADMIN) {
				adminViewer = new AdminView(lv.getFrame());
				AdminSystemController ac = new AdminSystemController(user, adminViewer);
			} else if (user.getUserType() == UserTypes.TEACHER) {
				teacherViewer = new TeacherView(lv.getFrame());
				TeacherSystemController tc = new TeacherSystemController(user, teacherViewer);
				tc.initController();
			}
		} else {
			
			if (studentViewer != null) {
				studentViewer.removeUI();
			} else if (registrarViewer != null) {
				registrarViewer.removeUI();
			} else if (adminViewer != null) {
				adminViewer.removeUI();
			} else if (teacherViewer != null) {
				teacherViewer.removeUI();
			}
			
			lv.viewLogoutChange();
		}
	}
	
	public void loginEvent() {
		try {
			loginChecker(lv.getNameField(), lv.getPasswordField());
			
			if (user.isLoggedIn()) {
				changeView();
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	
	
	
	public static void main(String[] args) throws Exception {
		String pass = "Generalkenobi!";
		
		newPasswordChecker(pass);
		
		String salt = generateSalt();
		
		String newPassword = pass + salt;
		
		System.out.println("Salt: "+salt);
		System.out.print("New Password: "+hash(newPassword));
	}
	
	
	/**
	 * loginChecker()
	 * Instantiates a DatabaseController
	 * SELECTS all information from users table
	 * Compares username against database entry
	 * Hashes and salts user password input and compares to entry in database with stored salt
	 * Stores other data from Row in database
	 */
	public static void loginChecker(JTextField nameField, JPasswordField passwordField) 
			throws Exception {
		DatabaseController dc = new DatabaseController();
		
		User mainUser = new User();
		
		Boolean exists = false;
		
		String username = nameField.getText();
		String password = new String(passwordField.getPassword());
		
		String query = String.format("SELECT * FROM users WHERE username = ?");
		
		ArrayList<String[]> values = new ArrayList<String[]>();
		
		values.add(new String[]{username,"true"});
		
		
		String[] queries = {query};
		
		ArrayList<String[]> allResults = dc.executeQuery(query,values);
		String[] results = null;
		
		if(allResults.size() > 0) {
			results = allResults.get(0);
		}
		
		String userID = "";
		String usernameInDB = null, passwordInDB = null, userType = null, salt = null;
		
		if (results != null) {
			exists = (results.length > 0);
		}
		
		if (exists) {
			userID = results[0];
			System.out.println("UID: "+userID);
			usernameInDB = results[1];
			passwordInDB = results[2];
			userType = results[3];
			salt = results[4];
		}
		
		if (salt != null) {
			password = hash(password + salt);
		}
		
		if ((username.equals(usernameInDB)) && (password.equals(passwordInDB)) && exists) {
			
			mainUser.setUserDetails(Integer.parseInt(userID),usernameInDB,UserTypes.valueOf(userType.toUpperCase()));
			mainUser.login();
			
			user = mainUser;
			// JOptionPane.showMessageDialog(null, ("You are now logged in as "+mainUser.getUsername()));
		} else if (username.trim().length() != 0 && password.trim().length() != 0) {
			JOptionPane.showMessageDialog(null, "Username and/or password were incorrect", "Password Error", JOptionPane.ERROR_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "You cannot leave the username or password fields empty", "Password Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	/**
	 * hash()
	 * Uses java's MessageDigest and DatatypeConverter APIs to SHA-256 hash a string.
	 * @throws UnsupportedEncodingException 
	 * */
	public static String hash(String stringToHash) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		// Define algorithm: SHA-256
		MessageDigest stringDigest = MessageDigest.getInstance("SHA-256");
		
		// Generate byte hash store in an array of bytes
		byte[] hash = stringDigest.digest(
				stringToHash.getBytes(StandardCharsets.UTF_8));
		
		// Convert the hash information into hexadecimal
		// String hashValueOld = DatatypeConverter.printHexBinary(hash);
		
		// Build string of hex values
		StringBuffer hashValue = new StringBuffer();
		
		for (int n = 0; n < hash.length; n++) {
			String hexString = Integer.toHexString(0xFF & hash[n]);
			
			// Prevents error where hexString is only one char in length
			if (hexString.length() < 2) {
				hexString = "0" + hexString;
			}
			
			hashValue.append(hexString);
		}
		
		System.out.println(hashValue.toString().toLowerCase());
		
		return hashValue.toString().toLowerCase();
	}
	
	/**
	 * generateSalt()
	 * Function which generates a random Hex string of random length 
	 * between 16 and 30.
	 * This salt string is added to the password.
	 * @throws Exception 
	 * */
	public static String generateSalt() throws Exception {
		DatabaseController dc = new DatabaseController();
		
		Random rand = new Random();
		
		Boolean validSalt = false;
		String salt = "";
		
		while (!validSalt) {
			// Generate random length
			int length = rand.nextInt(10)+16;
			
			salt = "";
			int charValue = 0;
			
			// Generate a salt with a length between 16 and 
			for (int i = 0; i < length; i++) {
				int typeOfValue = rand.nextInt(2)+1;
				
				// Generate ASCII values for 0-9 or A-F
				switch(typeOfValue) {
					case 1:
						// Between 48 and 57 i.e. 0-9
						charValue = rand.nextInt(10)+48;
						break;
					default:
						// Between 65 and 70 i.e. A-F
						charValue = rand.nextInt(6)+65;
						break;
				}
				
				// Convert ASCII value to character and append to salt string
				salt += (char)charValue;
			}
			
			String query = String.format("SELECT salt FROM users");
			
			String[] queries = {query};
			
			ArrayList<String[]> allResults = dc.executeQuery(query,null);
			
			if(allResults.size() < 0) {
				validSalt = true;
			} else {
				for (String[] result : allResults) {
					if (salt != result[0]) {
						validSalt = true;
					}
				}
			}
			
		}
		
		return salt.toLowerCase();
	}
	
	
	
	/*
	 * newPasswordChecker
	 * Takes a password string and tests that it has
	 * a lower case char, an upper case char and a symbol.
	 * Also checks that it does not contain "PASSWORD".
	 * Security mesaure to ensure new passwords are secure.
	 */
	public static Boolean newPasswordChecker(String newPassword) {
		
		// Check password length
		Boolean acceptableLength = newPassword.length() >= 8;
		
		// Default booleans for tests
		Boolean containsSymbol = false;
		Boolean containsCapital = false;
		Boolean containsLowerCase = false;
		Boolean containsPassword = false;
		
		// Check each character of password
		for (int c = 0; c < newPassword.length(); c++) {
			char currentChar = newPassword.charAt(c);
			String currentString = Character.toString(currentChar); // string version for symbol check
			
			// Check if char is upper case
			if (Character.isUpperCase(currentChar)) {
				containsCapital = true;
			}
			
			// Check if char is lower case
			if (Character.isLowerCase(currentChar)) {
				containsLowerCase = true;
			}
			
			// Check if char is a symbol
			if (!currentString.matches("[A-Za-z0-9]")) {
				containsSymbol = true;
			}
		}
		
		// Check if string contains "Password"
		if (newPassword.toUpperCase().contains("PASSWORD")) {
			containsPassword = true;
		}
		
		
		// If all test are passed then return true and display dialog box
		if (containsCapital && containsLowerCase && containsSymbol && !containsPassword) {
			JOptionPane.showMessageDialog(null, "Password Accepted");
			return true;
		} else {
			// Tests failed then return false and info on what is wrong with the password in dialog box
			JOptionPane.showMessageDialog(null, "Password Rejected. Please include: A lower case letter, an upper case letter and a symbol. "
					+ "Minumum length 8 characters. Cannot include 'PASSWORD'.", "Password Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
}
