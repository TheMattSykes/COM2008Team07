import java.sql.*;

public class UniversitySystem {
	public static void main(String[] args) throws Exception {
		// admin dirk567 are username and password
		
		// Check drivers
		FindDrivers fd = new FindDrivers();
		fd.initDrivers();
		
		// Run GUI
		GraphicsManager gm = new GraphicsManager();
		gm.runUI();
	}
}
