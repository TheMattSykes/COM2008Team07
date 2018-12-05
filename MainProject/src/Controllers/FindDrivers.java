/**
 * FindDrivers Class
 * 
 * Locates JDBC driver in project file.
 */

package Controllers;
import java.sql.*;
import java.util.*;

public class FindDrivers {

	public static void initDrivers() throws Exception {
	
		System.out.println("\nDrivers loaded as properties:");
		System.out.println(System.getProperty("jdbc.drivers"));
		System.out.println("\nDrivers loaded by DriverManager:");
		Enumeration<Driver> list = DriverManager.getDrivers();
		// DriverManager.setLoginTimeout(2);
		while (list.hasMoreElements())
			System.out.println(list.nextElement());
	}
	
	public static void main(String[] args) throws Exception {
		initDrivers();
	}
}