package Controllers;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;

import Models.Classification;
import Models.Department;
import Models.Grades;
import Models.GraduateType;
import Models.Module;
import Models.User;
import Models.UserTypes;
import Models.Views;
import Views.AddDepartment;
import Views.AdminView;
import Views.LoginView;
import Views.PrimaryFrame;
import Views.StudentView;

import java.util.Collection;
import java.util.Collections;

public class AdminSystemController extends Controller{
	
	User user;
	AdminView av;
	AddDepartment ad;
	DatabaseController dc = new DatabaseController();
	//private Views currentView;
	
	public AdminSystemController (User mainUser, AdminView aview) throws Exception {
		user = mainUser;
		av = aview;
		initMenuView();
	}

	public void initMenuView() {
		av.loadMenuUI();
		// Setting up button actions
		av.getAccountButton().addActionListener(e -> { 
				try {
					initAccountView();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			});
		
		av.getDeptartmentButton().addActionListener(e -> {
				try {
					initDepartmentView();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			});
		
		av.getDegreeButton().addActionListener(e -> {
				try {
					initDegreeView();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			});
		
		av.getModuleButton().addActionListener(e -> {
				try {
					initModuleView();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			});
	}
	
	public void initAccountView() throws Exception{
		av.setDataAccounts(getAccountData());
		av.loadAccountUI();
		av.getBackButton().addActionListener(e -> initMenuView());
	}
	
	public void initDepartmentView() throws Exception {
		av.setDataDepartments(getDepartmentData());
		av.loadDepartmentUI();
		av.getBackButton().addActionListener(e -> initMenuView());
	}
	
	public void initDegreeView() throws Exception {
		av.loadDegreeUI();
		av.getBackButton().addActionListener(e -> initMenuView());
		av.getDepartmentAdd().addActionListener(e -> initAddDepartmentView());
	}
	
	public void initModuleView() throws Exception {
		av.setDataModules(getModuleData());
		av.loadModuleUI();
		av.getBackButton().addActionListener(e -> initMenuView());
	}
	
	public void initAddAccountView() {
		System.out.println("Change to the Account add view - WIP");
	}
	
	public void initAddDepartmentView() {
		System.out.println("Change to the Dept. add view - WIP");
	}
	
	public void initAddDegreeView() {
		System.out.println("Change to the Degree add view - WIP");
	}
	
	public void initAddModuleView() {
		System.out.println("Change to the Module add view - WIP");
	}
	
	public Object[][] getAccountData() throws Exception {
		
		String query = "SELECT userID, username, user_type FROM users LIMIT ?";
		ArrayList<String[]> values = new ArrayList<String[]>();
		values.add(new String[] {"1000",""});
		ArrayList<String[]> results = dc.executeQuery(query,values);
		
		ArrayList<User> users = new ArrayList<User>();
		
		for (int i = 0; i < results.size(); i++) {
			users.add(new User(Integer.parseInt(results.get(i)[0]), results.get(i)[1], UserTypes.valueOf(results.get(i)[2].toUpperCase())));
		}
		
		Object[][] data = new Object[results.size()][3];
		
		int row = 0;
		for (User user : users) {
			data[row][0] = user.getUserID();
			data[row][1] = user.getUsername();
			data[row][2] = user.getUserType();
			row++;
		}
		
		return data;
	}
	
	public Object[][] getDepartmentData() throws Exception {
		
		String query = "SELECT * FROM departments LIMIT ?";
		ArrayList<String[]> values = new ArrayList<String[]>();
		values.add(new String[] {"100",""});
		ArrayList<String[]> results = dc.executeQuery(query, values);
		
		ArrayList<Department> departments = new ArrayList<Department>();
		
		for (int i=0; i < results.size(); i++) {
			departments.add(new Department(results.get(i)[0],results.get(i)[1]));
		}
		
		Object[][] data = new Object[results.size()][2];
		int row = 0;
		for (Department department : departments) {
			data[row][0] = department.getCode();
			data[row][1] = department.getName();
			row++;
		}
		
		return data;
	}
	
	public Object[][] getModuleData() throws Exception {
		
		String query = new String("SELECT * FROM modules LIMIT ?");
		ArrayList<String[]> values = new ArrayList<String[]>();
		values.add(new String[] {"1000", ""});
		ArrayList<String[]> results = dc.executeQuery(query, values);
		
		ArrayList<Module> modules = new ArrayList<Module>();
		
		for (int i=0; i < results.size(); i++) {
			modules.add(new Module(results.get(i)[0],results.get(i)[1],Integer.parseInt(results.get(i)[2]),results.get(i)[3],GraduateType.valueOf(results.get(i)[4].toUpperCase())));
		}
		
		Object[][] data = new Object[results.size()][5];
		
		int row = 0;
		for (Module module : modules) {
			data[row][0] = module.getCode();
			data[row][1] = module.getName();
			data[row][2] = module.getCredits();
			data[row][3] = module.getTeachingPeriod();
			data[row][4] = module.getType();
			row++;		
		}
		
		return data;
	}
	
	// get degree data WIP
}
