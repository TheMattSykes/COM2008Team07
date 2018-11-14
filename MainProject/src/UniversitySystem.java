import java.sql.*;

import Controllers.GraphicsController;
import Models.FindDrivers;
import Users.User;

public class UniversitySystem {
	
	// NOTE: JavaSE-1.8 System Library may be required if issues arrise while running.
	
	public static void main(String[] args) throws Exception {
		// admin dirk567 are username and password
		
		// Check drivers
		FindDrivers fd = new FindDrivers();
		fd.initDrivers();
		
		User mainUser = new User();
		
		// Run GUI
		GraphicsController gm = new GraphicsController();
		gm.runUI(mainUser);
	}
}
