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
import Views.RegistrarView;
import Views.AdminView;

import java.util.ArrayList;
import java.util.Random;
import java.sql.*;
import java.text.MessageFormat;


public class AccountController extends Controller {

	private static User user;
	private LoginView lv;
	private JPanel menuBar;
	
	private StudentView studentViewer = null;
	private RegistrarView registrarViewer = null;
	private AdminView adminViewer = null;

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
				RegistrarSystemController sc = new RegistrarSystemController(user, registrarViewer);
				sc.initController();
			} else if (user.getUserType() == UserTypes.ADMIN) {
				adminViewer = new AdminView(lv.getFrame());
				AdminSystemController sc = new AdminSystemController(user, adminViewer);
			}
		} else {
			
			if (studentViewer != null) {
				studentViewer.removeUI();
			} else if (registrarViewer != null) {
				registrarViewer.removeUI();
			} else if (adminViewer != null) {
				adminViewer.removeUI();
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
		String pass = "generalkenobi";
		
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
		} else {
			JOptionPane.showMessageDialog(null, "Username and/or password were incorrect");
		}
	}
	
	
	/**
	 * hash()
	 * Uses java's MessageDigest and DatatypeConverter APIs to SHA-256 hash a string.
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

}
