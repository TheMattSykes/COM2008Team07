/**
 *  UniversitySystem
 *  
 *  Main class file for the university program.
 *  Run this class to start the program.
 */

import Controllers.FindDrivers;
import Controllers.MasterController;
import Models.User;

public class UniversitySystem {
	
	// NOTE: JavaSE-1.8 System Library may be required if issues arise while running.
	
	public static void main(String[] args) throws Exception {
		// admin Dirk567! are username and password for admin
		// student GeneralKenobi! are for student
		// registrar Bohemianrhapsody! are for registrar
		// teacher Iamyourfather! are for teacher
		
		// Initialise drivers
		FindDrivers.initDrivers();
		
		User mainUser = new User();
		
		// Run GUI
		MasterController mc = new MasterController();
		mc.runUI(mainUser);
	}
}
