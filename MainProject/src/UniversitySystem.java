import java.sql.*;

public class UniversitySystem {
	public static void main(String[] args) {
		Connection con = null;
		String db = "jdbc:mysql://stusql.dcs.shef.ac.uk/team007?user=team007&password=412fe569";
		
		FindDrivers fd = new FindDrivers();
		
		try {
			con = DriverManager.getConnection(db);
			System.out.println("DATABASE CONNECTION ESTABILISHED");
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				con.close();
				System.out.println("DATABASE CONNECTION TERMINATED");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
