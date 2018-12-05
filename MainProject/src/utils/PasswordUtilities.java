/**
 * PasswordUtilities
 * 
 * Hashes using SHA-512 a string.
 * Generates a random salt to add to a password.
 * Checks if a password is sufficient stength.
 */

package utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import Controllers.DatabaseController;

public class PasswordUtilities {
	
	public static void main(String[] args) throws Exception {
		String pass = "Iamyourfather!";
		
		newPasswordChecker(pass);
		
		String salt = generateSalt();
		
		String newPassword = pass + salt;
		
		System.out.println("Salt: "+salt);
		System.out.print("New Password: "+hash(newPassword));
	}
	
	
	/**
	 * hash()
	 * Uses java's MessageDigest and DatatypeConverter APIs to SHA-512 hash a string.
	 * @throws UnsupportedEncodingException 
	 * */
	public static String hash(String stringToHash) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		try {
			// Define algorithm: SHA-256
			MessageDigest stringDigest = MessageDigest.getInstance("SHA-512");
			
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
			
			return hashValue.toString().toLowerCase();
		} catch (NoSuchAlgorithmException e) {
			JOptionPane.showMessageDialog(null, "Our password security systems are not compatible with your device"
					+ " please install Java SE 1.8 to continue.", "System Error", JOptionPane.ERROR_MESSAGE);
		}
		
		return null;
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
		if (acceptableLength && containsCapital && containsLowerCase && containsSymbol && !containsPassword) {
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
