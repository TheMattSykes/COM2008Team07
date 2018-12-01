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
import Views.AddAccount;
import Views.AddDegree;
import Views.AddDepartment;
import Views.AddModule;
import Views.AdminView;
import Views.LoginView;
import Views.PrimaryFrame;
import Views.StudentView;
import utils.PasswordUtilities;

import java.util.Collection;
import java.util.Collections;

public class AdminSystemController extends Controller{

	private AdminView av;
	private AddAccount addAccountView;
	private AddDepartment addDeptView;
	private AddDegree addDegreeView;
	private AddModule addModuleView;
	private DatabaseController dc;
	//private Views currentView;
	
	public AdminSystemController (User mainUser, AdminView aview) throws Exception {
		super(mainUser);
		
		dc = new DatabaseController();
		av = aview;
		initMenuView();
	}

	public void initMenuView() {
		try {
			getDegreeData();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
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
		av.getAccountAdd().addActionListener(e -> initAddAccountView());
		
		deleteButton.addActionListener(e -> {
			Object[][] data = av.getDataAccounts();
			JTable table = av.getAccountTable();
			int row = table.getSelectedRow();
			User targetUser = new User(Integer.parseInt(data[row][0]+""),(String)data[row][1],UserTypes.valueOf(data[row][2].toString()));
			deleteAccount(targetUser);
		});
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
		av.setDataDegrees(getDegreeData());
		av.loadDegreeUI();
		av.getBackButton().addActionListener(e -> initMenuView());
		av.getDegreeAdd().addActionListener(e -> initAddDegreeView());
		JButton deleteButton = av.getDegreeDelete();
		
	}
	
	public void initModuleView() throws Exception {
		av.setDataModules(getModuleData());
		av.loadModuleUI();
		av.getBackButton().addActionListener(e -> initMenuView());
		av.getModuleAdd().addActionListener(e -> initAddModuleView());
	}
	
	public void initAddAccountView() {
		if (addAccountView == null) {
			addAccountView = new AddAccount(av.getFrame());
		}
		
		av.removeUI();
		try {
			addAccountView.loadUI();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		addAccountView.getBackButton().addActionListener(e -> {
			addAccountView.removeUI();
			try {
				initAccountView();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
		addAccountView.getApplyButton().addActionListener(e -> {
			addAccount(addAccountView.getDetails());
		});
	}
	
	public void initAddDepartmentView() {
		if (addDeptView == null) {
			addDeptView = new AddDepartment(av.getFrame());
		}
		
		av.removeUI();
		try {
			addDeptView.loadUI();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		addDeptView.getBackButton().addActionListener(e -> {
			addDeptView.removeUI();
			try {
				initDepartmentView();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
		addDeptView.getApplyButton().addActionListener(e -> {
			addDepartment(addDeptView.getNewDepartment());
		});
	}
	
	public void initAddDegreeView() {
		//if (addDegreeView == null) {
		//	addDegreeView = new AddDegree(av.getFrame());
		//}
	}
	
	public void initAddModuleView() {
		if (addModuleView == null) {
			addModuleView = new AddModule(av.getFrame());
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
			addModuleView.setAvailableDepartment(deptNames);
			addModuleView.loadUI();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		addModuleView.getBackButton().addActionListener(e ->{
			addModuleView.removeUI();
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
	
	public Object[][] getDegreeData() throws Exception {
		String degreeQuery = new String("SELECT * FROM degrees;");
		String leadQuery = new String("SELECT * FROM leads;");
		ArrayList<String[]> degreeResults = dc.executeQuery(degreeQuery, null);
		ArrayList<String[]> leadResults = dc.executeQuery(leadQuery, null);
		Object[][] data = new Object[degreeResults.size()][5];
		int row = 0;
		for (String[] degree : degreeResults) {
			data[row][0] = degree[0];
			data[row][1] = degree[1];
			data[row][2] = degree[2];
			for (String[] lead : leadResults) {
				ArrayList<String> assisting = new ArrayList<String>();
				if (degree[0].equals(lead[1])) {
					if ( Integer.parseInt(lead[2]) == 1 ) {
						data[row][3] = lead[0];
					} else if ( Integer.parseInt(lead[2]) == 0 ) {
						assisting.add(lead[0]);
					}
				}
				String assistString = "";
				for (int i = 0; i < assisting.size(); i++) {
					if (assistString.equals("") ) {
						assistString += assisting.get(i);
					} else {
						assistString += ", " + assisting.get(i);
					}
				}
				data[row][4] = assistString;
			}
			row++;
		}
		return data;
	}
	
	public void addAccount(String[] details) {
		try {
			// Start compiling an error message for a detailed error
			String errorMessage = "Issue(s) with the data entered: \n";
			Boolean error = false;
			// Setting up variables for data
			String salt;
			String newPass;
			String hashedPass;
			ArrayList<String[]> results = new ArrayList<String[]>();
			// Obtaining usernames
			String query = "SELECT username FROM users;";
			results = dc.executeQuery(query, null);
			// Checking if any field is empty
			String[] detailTitles = new String[] {"First Name", "Second Name", "User Type", "Password", "Password Confirmation"};
			for (int i=0; i<details.length ; i++) {
				if (details[i] == "") {
					errorMessage += "The "+detailTitles[i]+" field is empty \n";
					error = true;
				}
			}
			// Checking password equality
			if (!details[3].equals(details[4])) {
				errorMessage += "Confirmation password doesnt match password \n";
				error = true;
			}
			
			// Checking password strength
			if ( !PasswordUtilities.newPasswordChecker(details[3]) ) {
				errorMessage += "Password must contain atleast one lower case, upper case and a symbol - as well as being atleast 8 characters long";
				error = true;
			}
			
			if ( !error ) {
				// Compiling the user data into a new user.
				Integer id = results.size()+1;
				UserTypes type =  UserTypes.valueOf(details[2]);
				String username = makeUsername(details[0], details[1], type);
				// Finding the relevant password data
				salt = PasswordUtilities.generateSalt();
				newPass = details[3]+salt;
				hashedPass = PasswordUtilities.hash(newPass);
				// Asking for confirmation
				Object[] options = {"Yes", "No"};
				int applyOption = JOptionPane.showOptionDialog(addAccountView.getFrame(), "Confirm adding user with username "+ username, "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
						null, options, options[0]);
				if (applyOption == 0) {
					// carry out query & redirect back to accounts view
					String addQuery = "INSERT INTO users VALUES(?,?,?,?,?)";
					ArrayList<String[]> values = new ArrayList<String[]>();
					values.add(new String[] {"" + id, "false"});
					values.add(new String[] {username, "true"});
					values.add(new String[] {hashedPass, "true"});
					values.add(new String[] {type.toString().toLowerCase(), "true"});
					values.add(new String[] {salt, "true"});
					dc.executeQuery(addQuery, values);
					addAccountView.removeUI();
					initAccountView();
				} 
			} else {
				JOptionPane inputError = new JOptionPane(errorMessage);
				JDialog dialog = inputError.createDialog("Failure");
				dialog.setAlwaysOnTop(true);
				dialog.setVisible(true);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	// Produces a Username for a user account based off of given name and type
	public String makeUsername(String fn, String sn, UserTypes type) {
		String base = "";
		if (type == UserTypes.ADMIN) {
			base = "adm";
		} else if (type == UserTypes.REGISTRAR) {
			base = "reg";
		} else if (type == UserTypes.TEACHER) {
			base = "tcr";
		} else {
			base = "";
		}
		base += fn.substring(0,1).toLowerCase();
		base += sn.toLowerCase();
		try {
			String query = "SELECT username FROM users WHERE username LIKE '"+base+"%';";
			ArrayList<String[]> results = dc.executeQuery(query, null);
			int count = results.size();
			base += (count+1);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return base;
	}
	
	public void deleteAccount(User u) {
		String userID = u.getUserID() + "";
		String username = u.getUsername();
		if (u.getUserID() == user.getUserID()) {
			JOptionPane inputError = new JOptionPane("You cannot delete your own account");
			JDialog dialog = inputError.createDialog("Failure");
			dialog.setAlwaysOnTop(true);
			dialog.setVisible(true);
		} else if (typeCount(u.getUserType()) < 2) {
			JOptionPane inputError = new JOptionPane("You can't delete the last account of a type");
			JDialog dialog = inputError.createDialog("Failure");
			dialog.setAlwaysOnTop(true);
			dialog.setVisible(true);
		} else {
			Object[] options = {"Yes", "No"};
			int applyOption = JOptionPane.showOptionDialog(av.getFrame(), "Confirm deleting user "+userID+" with username "+ username, 
					"Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if (applyOption == 0) {
				try {
					String query = "DELETE FROM users WHERE userID = ?;";
					ArrayList<String[]> values = new ArrayList<String[]>();
					values.add(new String[] {userID, "true"});
					dc.executeQuery(query, values);
					initAccountView();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	public int typeCount(UserTypes t) {
		Integer count = 0;
		try {
			String query = "SELECT * FROM users WHERE user_type = ?";
			ArrayList<String[]> values = new ArrayList<String[]>();
			values.add(new String[] {t.toString(), "true"});
			ArrayList<String[]> results = dc.executeQuery(query, values);
			count = results.size();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return count;
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
					int applyOption = JOptionPane.showOptionDialog(addDeptView.getFrame(), "Confirm adding the department "+d.getName()+
							" with code "+d.getCode(), "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
							null, options, options[0]);
					if (applyOption == 0) {
						String insertQuery = "INSERT INTO departments VALUES(?,?);";
						ArrayList<String[]> insertValues = new ArrayList<String[]>();
						insertValues.add(new String[] {d.getName(), "true"});
						insertValues.add(new String[] {d.getCode(), "true"});
						dc.executeQuery(insertQuery, insertValues);

						addDeptView.removeUI();
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
	
	public void removeAllUI() {
		if (av != null)
			av.removeUI();
		if (addAccountView != null)
			addAccountView.removeUI();
		if (addDeptView != null)
			addDeptView.removeUI();
		if (addDegreeView != null)
			addDegreeView.removeUI();
		if (addModuleView != null)
			addModuleView.removeUI();
		
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
