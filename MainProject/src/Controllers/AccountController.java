/**
 * AccountController
 * 
 * Controller for the login UI.
 * Instantiates other controllers after user is logged in.
 */

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
import utils.PasswordUtilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class AccountController extends Controller {
	private LoginView lv;
	private JPanel menuBar;
	
	private StudentView studentViewer = null;
	private RegistrarView registrarViewer = null;
	private AdminView adminViewer = null;
	private TeacherView teacherViewer = null;
	
	private static PasswordUtilities pu;
	private RegistrarSystemController rc;

	public AccountController(User mainUser, LoginView lview) {
		super(mainUser);
		
		lv = lview;
		
		pu = new PasswordUtilities();
		
		initView();
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
			} else if (user.getUserType() == UserTypes.REGISTRAR) {
				registrarViewer = new RegistrarView(lv.getFrame());
				rc = new RegistrarSystemController(user, registrarViewer);
			} else if (user.getUserType() == UserTypes.ADMIN) {
				adminViewer = new AdminView(lv.getFrame());
				AdminSystemController ac = new AdminSystemController(user, adminViewer);
			} else if (user.getUserType() == UserTypes.TEACHER) {
				teacherViewer = new TeacherView(lv.getFrame());
				TeacherSystemController tc = new TeacherSystemController(user, teacherViewer);
			}
		} else {
			
			if (studentViewer != null) {
				studentViewer.removeUI();
			} else if (registrarViewer != null) {
				rc.removeAllUI();
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
			password = pu.hash(password + salt);
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
}
