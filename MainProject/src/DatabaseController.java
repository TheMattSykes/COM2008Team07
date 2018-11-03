import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class DatabaseController {
	private Connection con = null;
	private String db = "jdbc:mysql://stusql.dcs.shef.ac.uk/team007?user=team007&password=412fe569";
	
	public ArrayList<String[]> executeQueries(String[] queries) throws Exception {
		ArrayList<String[]> output = null;
		
		try {
			// Initiate connection with database
			DriverManager.setLoginTimeout(10);
			con = DriverManager.getConnection(db);
			System.out.println("DATABASE CONNECTION ESTABILISHED");
			
			//TODO: Sanatise input to protect from SQL injection
			
			System.out.println("\t[DATABASE] Starting query processing...");
			for (String query : queries) {
				System.out.println("\t[DATABASE] Query input: "+query);
				String startOfQuery = query.substring(0, 6); // All required starts of statements are 6 chars long
				
				Statement stmt = null; // a SQL statement object
				
				try {
					stmt = con.createStatement();
					
					if (startOfQuery.contains("DELETE") || startOfQuery.contains("INSERT") || startOfQuery.contains("UPDATE")) {
						// Update database with query
						stmt.executeUpdate(query);
					} else if (startOfQuery.contains("SELECT")) {
						// Return a list of results with query
						output = new ArrayList<String[]>();
						
						System.out.println("\t[DATABASE] Executing query...");
						ResultSet res = stmt.executeQuery(query);
						System.out.println("\t[DATABASE] Query executed");
						System.out.println("\t[DATABASE] Processing output...");
						
						// Get the number of columns in the table through MetaData
						ResultSetMetaData meta = res.getMetaData();
						int noOfColumns = meta.getColumnCount();
						
						// Initialised string array with a length of the number of columns
						String[] nextRow = new String[noOfColumns];
						
						// Store row data in array
						while (res.next()) {
							for (int column = 1; column <= noOfColumns; column++) {
								nextRow[column-1] = res.getString(column);
							}
						}
						
						// Append array to list of arrays
						output.add(nextRow);
						System.out.println("\t[DATABASE] Output processed");
						
						// Close ResultSet
						res.close();
					} else {
						// Do not return results
						stmt.executeQuery(query);
					}
				}
				catch (SQLException ex) {
					System.out.println("\t[DATABASE ERROR!] ERROR WITH QUERY REPORTED");
					ex.printStackTrace();
				}
				finally {
					if (stmt != null) stmt.close();
				}
			}
			System.out.println("\t[DATABASE] Query processed");
			
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Unable to connect to the database. Please ensure you are connected to the campus network.");
			System.out.println("[DATABASE ERROR!] CONNECTION ERROR");
			ex.printStackTrace();
		} finally {
			try {
				con.close();
				System.out.println("DATABASE CONNECTION TERMINATED");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return output;
	}
}
