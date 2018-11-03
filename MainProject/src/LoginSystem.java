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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.xml.bind.DatatypeConverter;
import java.util.Random;
import java.sql.*;


public class LoginSystem {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		String password = "test";
		
		String salt = generateSalt();
		
		String stringToHash = password + salt;
		
		String hashValue = hash(stringToHash);
		
		
		System.out.println("Salt: "+salt);
		System.out.println("SHA-256 Hash: "+hashValue);
	}
	
	
	public static void loginChecker(JTextField nameField, JPasswordField passwordField) {
		String username = nameField.getText();
		String password = new String(passwordField.getPassword());
  
		String usernameInDB = "admin";
		String passwordInDB = "test";
  
		if ((username.equals(usernameInDB)) && (password.equals(passwordInDB))) {
			JOptionPane.showMessageDialog(null, "Login successful");
		} else {
			JOptionPane.showMessageDialog(null, "Username and/or password were incorrect");
		}
	}
	
	public JPanel loginUI() {
		JPanel loginForm = new JPanel();
		
		loginForm.setLayout(new GridBagLayout());
		GridBagConstraints loginConstraints = new GridBagConstraints();

		loginForm.setBorder(BorderFactory.createEmptyBorder(100, 100, 200, 100));
		JLabel nameLabel = new JLabel("Username: ");
		JLabel passwordLabel = new JLabel("Password: ");
		JTextField nameField = new JTextField("",20);
		JPasswordField passwordField = new JPasswordField("",20);
		
		loginConstraints.insets = new Insets(5,5,5,5);
		
		loginConstraints.fill = GridBagConstraints.HORIZONTAL;
		loginConstraints.gridx = 0;
		loginConstraints.gridy = 0;
		loginForm.add(nameLabel, loginConstraints);
		
		loginConstraints.gridx = 1;
		loginConstraints.gridy = 0;
		loginForm.add(nameField, loginConstraints);
		
		loginConstraints.gridx = 0;
		loginConstraints.gridy = 1;
		loginForm.add(passwordLabel, loginConstraints);
		
		loginConstraints.gridx = 1;
		loginConstraints.gridy = 1;
		loginForm.add(passwordField, loginConstraints);
		
		loginConstraints.insets = new Insets(20,50,5,50);
		JButton loginButton = new JButton("Log in");
		loginButton.setPreferredSize(new Dimension(400,50));
		
		loginButton.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  loginChecker(nameField,passwordField);
		  }
		});
		
		loginConstraints.gridx = 0;
		loginConstraints.gridy = 2;
		loginConstraints.gridwidth = 2;
		loginButton.setPreferredSize(new Dimension(100,50));
		loginForm.add(loginButton, loginConstraints);
		
		return loginForm;
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
