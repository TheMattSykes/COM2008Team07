package Controllers;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.xml.bind.DatatypeConverter;

import Models.User;
import Models.UserTypes;
import Views.LoginView;
import Views.PrimaryFrame;
import Views.StudentView;

import java.util.ArrayList;
import java.util.Random;
import java.sql.*;
import java.text.MessageFormat;


public class AccountController extends Controller {

	private static User user;
	private LoginView lv;
	private JPanel menuBar;
	
	private StudentView studentViewer = null;

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
		
		logout.addActionListener(e -> logoutEvent());;
	}
	
	public void logoutEvent() {
		Object[] options = {"Logout", "Cancel"};
		
		int logoutOption = JOptionPane.showOptionDialog(lv.getFrame(), "Confirm logout", "Logout question", JOptionPane.YES_NO_OPTION, 
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		
		if (logoutOption == 0) {
			user.logout();
			changeView();
		}
	}
	
	public void changeView() {
		if (user.isLoggedIn()) {
			lv.viewChange();
			
			if (user.getUserType() == UserTypes.STUDENT) {
				studentViewer = new StudentView(lv.getFrame());
				StudentSystemController sc = new StudentSystemController(user, studentViewer);
				sc.initController();
			}
		} else {
			
			if (studentViewer != null) {
				studentViewer.removeUI();
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
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
		String pass = "hellotheregeneralkenobi";
		
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
		String[][] values = new String[1][2];
		values[0][0] = username;
		values[0][1] = "true";
		
		
		String[] queries = {query};
		String[] results = dc.executeQuery(query,values);
		
		String userID = "";
		String usernameInDB = null, passwordInDB = null, userType = null, salt = null;
		
		exists = (results.length > 0);
		
		if (exists) {
			userID = results[0];
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
		} else {
			JOptionPane.showMessageDialog(null, "Username and/or password were incorrect");
		}
	}
	
	
	/**
	 * hash()
	 * Uses java's MessageDigest and DatatypeConverter apis to SHA-256 hash a string.
	 * */
	public static String hash(String stringToHash) throws NoSuchAlgorithmException {
		// Define algorithm: SHA-256
		MessageDigest stringDigest = MessageDigest.getInstance("SHA-256");
		
		// Generate byte hash store in an array of bytes
		byte[] hash = stringDigest.digest(
				stringToHash.getBytes(StandardCharsets.UTF_8));
		
		// Convert the hash information into hexadecimal
		String hashValue = DatatypeConverter.printHexBinary(hash);
		
		return hashValue.toLowerCase();
	}
	
	/**
	 * generateSalt()
	 * Function which generates a random Hex string of random length 
	 * between 16 and 30.
	 * This salt string is added to the password.
	 * */
	public static String generateSalt() {
		Random rand = new Random();
		
		// Generate random length
		int length = rand.nextInt(10)+16;
		
		String salt = "";
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
		
		return salt.toLowerCase();
	}

}
