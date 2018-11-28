package Controllers;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import Models.Classification;
import Models.Degree;
import Models.Department;
import Models.Grades;
import Models.GraduateType;
import Models.Module;
import Models.User;
import Models.UserTypes;
import Models.Views;
import Views.AddDepartment;
import Views.AddModule;
import Views.AdminView;
import Views.LoginView;
import Views.PrimaryFrame;
import Views.StudentView;

import java.util.Collection;
import java.util.Collections;

public class AdminSystemController extends Controller{
	
	User user;
	AdminView av;
	AddDepartment addDept;
	AddModule addModule;
	DatabaseController dc = new DatabaseController();
	//private Views currentView;
	
	public AdminSystemController (User mainUser, AdminView aview) throws Exception {
		user = mainUser;
		av = aview;
		initMenuView();
	}

	public void initMenuView() {
		av.loadMenuUI();
		// Accounts button loads the Admin's Account UI
		av.getAccountButton().addActionListener(e -> { 
				try {
					initAccountView();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			});
		
		// Department button loads the Admin's Department UI
		av.getDeptartmentButton().addActionListener(e -> {
				try {
					initDepartmentView();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			});
		
		// Degree button loads the Admin's Degree UI
		av.getDegreeButton().addActionListener(e -> {
				try {
					initDegreeView();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			});
		
		// Module button loads the Admin's Module UI
		av.getModuleButton().addActionListener(e -> {
				try {
					initModuleView();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			});
	}
	
	public void initAccountView() throws Exception{
		// collect the account data
		av.setDataAccounts(getAccountData());
		av.loadAccountUI();
		// Back button loads the Admin menu
		av.getBackButton().addActionListener(e -> initMenuView());
		// Get the Account delete button & enable when a table selection is made
		JButton deleteButton = av.getAccountDelete();
		av.getAccountTable().getSelectionModel().addListSelectionListener(e -> {
						if(!deleteButton.isEnabled()) {
							deleteButton.setEnabled(true);
						}
					});
		// Listener to point to delete the account selected
		deleteButton.addActionListener(e -> {});
	}
	
	public void initDepartmentView() throws Exception {
		av.setDataDepartments(getDepartmentData());
		av.loadDepartmentUI();
		av.getBackButton().addActionListener(e -> initMenuView());
		av.getDepartmentAdd().addActionListener(e -> initAddDepartmentView());
		JButton deleteButton = av.getDepartmentDelete();
		av.getDepartmentTable().getSelectionModel().addListSelectionListener(e -> {
						if(!deleteButton.isEnabled()) {
							deleteButton.setEnabled(true);
						}
					});
		deleteButton.addActionListener(e -> {
			Object[][] data = av.getDataDepartments();
			JTable table = av.getDepartmentTable();
			int row = table.getSelectedRow();
			Department targetDepartment = new Department((String)(data[row][0]), (String)(data[row][1]));
			deleteDepartment(targetDepartment);
		});
	}
	
	public void initDegreeView() throws Exception {
		av.loadDegreeUI();
		av.getBackButton().addActionListener(e -> initMenuView());
	}
	
	public void initModuleView() throws Exception {
		av.setDataModules(getModuleData());
		av.loadModuleUI();
		av.getBackButton().addActionListener(e -> initMenuView());
		av.getModuleAdd().addActionListener(e -> initAddModuleView());
	}
	
	public void initAddAccountView() {
		System.out.println("Change to the Account add view - WIP");
	}
	
	public void initAddDepartmentView() {
		if (addDept == null) {
			addDept = new AddDepartment(av.getFrame());
		}
		
		av.removeUI();
		try {
			addDept.loadUI();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		addDept.getBackButton().addActionListener(e -> {
			addDept.removeUI();
			try {
				initDepartmentView();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
		addDept.getApplyButton().addActionListener(e -> {
			addDepartment(addDept.getNewDepartment());
		});
	}
	
	public void initAddDegreeView() {
		System.out.println("Change to the Degree add view - WIP");
	}
	
	public void initAddModuleView() {
		if (addModule == null) {
			addModule = new AddModule(av.getFrame());
		}
		
		av.removeUI();
		try {
			Object[][] departments = getDepartmentData();
			String[] deptNames = new String[departments.length];
			int i = 0;
			for (Object[] department : departments) {
				deptNames[i] = (String)department[1];
				i++;
			}
			addModule.setAvailableDepartment(deptNames);
			addModule.loadUI();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		addModule.getBackButton().addActionListener(e ->{
			addModule.removeUI();
			try {
				initModuleView();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
	}
	
	public Object[][] getAccountData() throws Exception {
		
		String query = "SELECT userID, username, user_type FROM users LIMIT ?;";
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
		
		String query = "SELECT code, name FROM departments LIMIT ?;";
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
		
		String query = new String("SELECT * FROM modules LIMIT ?;");
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
	
	// public Object[][] getDegreeData() throws Exception { }
	
	public void addAccount(User u) {
		
	}
	
	public void deleteAccount(User u) {
		
	}
	
	public void addDepartment(Department d) {
		if (d.getName().length() != 0 && d.getCode().length() != 0) {
			// To do: Managing duplicate entries
			try {
				String query = "SELECT code, name FROM departments;";
				ArrayList<String[]> values = new ArrayList<String[]>();
				ArrayList<String[]> results = dc.executeQuery(query, values);
				ArrayList<String> deptCodes = new ArrayList<String>();
				ArrayList<String> deptNames = new ArrayList<String>();
				
				for (int i = 0; i < results.size(); i++) {
					deptCodes.add(results.get(i)[0]);
					deptNames.add(results.get(i)[1]);
				}
				
				if(deptCodes.contains(d.getCode()) || deptNames.contains(d.getName())){
					JOptionPane inputError = new JOptionPane("One of the entered values already exists in the database, please ensure name and code are unique");
					JDialog dialog = inputError.createDialog("Failure");
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);
				} else {
					Object[] options = {"Yes", "No"};
					int applyOption = JOptionPane.showOptionDialog(addDept.getFrame(), "Confirm adding the department "+d.getName()+
							" with code "+d.getCode(), "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
							null, options, options[0]);
					if (applyOption == 0) {
						String insertQuery = "INSERT INTO departments VALUES(?,?);";
						ArrayList<String[]> insertValues = new ArrayList<String[]>();
						insertValues.add(new String[] {d.getName(), "true"});
						insertValues.add(new String[] {d.getCode(), "true"});
						dc.executeQuery(insertQuery, insertValues);

						addDept.removeUI();
						try {
							initDepartmentView();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			JOptionPane inputError = new JOptionPane("Please make sure both values have been entered");
			JDialog dialog = inputError.createDialog("Failure");
			dialog.setAlwaysOnTop(true);
			dialog.setVisible(true);
		}
	}
	
	public void deleteDepartment(Department d) {
		String deptCode = d.getCode();
		String deptName = d.getName();
		Object[] options = {"Yes", "No"};
		int applyOption = JOptionPane.showOptionDialog(av.getFrame(), "Confirm deleting the department "+deptName+
				" with code "+deptCode, "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
				null, options, options[0]);
		if (applyOption == 0) {
			try {
				String query = "DELETE FROM departments WHERE code = ? AND name = ? ";
				ArrayList<String[]> values = new ArrayList<String[]>();
				values.add(new String[] {deptCode, "true"});
				values.add(new String[] {deptName, "true"});
				dc.executeQuery(query, values);
				initDepartmentView();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public void addDegree(Degree d) {

	}
	
	public void deleteDegree(Degree d) {
		
	}
	
	public void addModule(Module m) {
		
	}
	
	public void deleteModule(Module m) {
		
	}
}
