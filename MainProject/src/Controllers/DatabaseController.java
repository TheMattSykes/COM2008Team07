package Controllers;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class DatabaseController {
	private Connection con = null;
	private String db = "jdbc:mysql://stusql.dcs.shef.ac.uk/team007?user=team007&password=412fe569";
	
	public ArrayList<String[]> executeQuery(String query, ArrayList<String[]> values) throws Exception {
		
		ArrayList<String[]> output = new ArrayList<String[]>();
		
		try {
			// Initiate connection with database
			DriverManager.setLoginTimeout(10);
			con = DriverManager.getConnection(db);
			System.out.println("DATABASE CONNECTION ESTABILISHED");
			
			//TODO: Sanitise input to protect from SQL injection
			
			System.out.println("\t[DATABASE] Starting query processing...");
			System.out.println("\t[DATABASE] Query input: "+query);
			String startOfQuery = query.substring(0, 6); // All required starts of statements are 6 chars long
			
			PreparedStatement stmt = null;
			
			try {
				stmt = con.prepareStatement(query);
				
				if (values != null) {
					for (int itemNo = 0; itemNo < values.size(); itemNo++) {
						String value = values.get(itemNo)[0];
						Boolean typeString = Boolean.valueOf(values.get(itemNo)[1]);
						int dbColumnNo = itemNo + 1;
						
						if (!typeString) {
							stmt.setInt(dbColumnNo, Integer.valueOf(value));
						} else {
							stmt.setString(dbColumnNo, (String)value);
						}
					}
				}
				
				if (startOfQuery.contains("DELETE") || startOfQuery.contains("INSERT") || startOfQuery.contains("UPDATE")) {
					// Update database with query
					stmt.executeUpdate();
				} else if (startOfQuery.contains("SELECT")) {
					// Return a list of results with query
					
					System.out.println("\t[DATABASE] Executing query...");
					ResultSet res = stmt.executeQuery();
					System.out.println("\t[DATABASE] Query executed");
					System.out.println("\t[DATABASE] Processing output...");
					
					// Get the number of columns in the table through MetaData
					ResultSetMetaData meta = res.getMetaData();
					int noOfColumns = meta.getColumnCount();
					
					// Initialised string array with a length of the number of columns
					
					// Store row data in array
					while (res.next()) {
						String[] nextRow = new String[noOfColumns];
						for (int column = 1; column <= noOfColumns; column++) {
							nextRow[column-1] = res.getString(column);
						}
						
						output.add(nextRow);
					}
					
					// Append array to list of arrays
					System.out.println("\t[DATABASE] Output processed");
					
					// Close ResultSet
					res.close();
				} else {
					// Do not return results
					stmt.executeQuery();
				}
			}
			catch (SQLException ex) {
				System.out.println("\t[DATABASE ERROR!] ERROR WITH QUERY REPORTED");
				ex.printStackTrace();
			}
			finally {
				if (stmt != null) stmt.close();
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
