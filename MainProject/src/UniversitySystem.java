import java.sql.*;

import Controllers.FindDrivers;
import Controllers.MasterController;
import Models.User;

public class UniversitySystem {
	
	// NOTE: JavaSE-1.8 System Library may be required if issues arrise while running.
	
	public static void main(String[] args) throws Exception {
		// admin dirk567 are username and password
		
		// Check drivers
		FindDrivers fd = new FindDrivers();
		fd.initDrivers();
		
		User mainUser = new User();
		
		// Run GUI
		MasterController mc = new MasterController();
		mc.runUI(mainUser);
	}
}
