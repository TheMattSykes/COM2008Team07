package Controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import Models.Student;
import Models.User;           
import Models.Views;
import Models.Enrolled;
import Views.TeacherView;
import Views.AddGrades;

public class TeacherSystemController extends Controller {
	
	User user;
	TeacherView tv;
	AddGrades ag;
	DatabaseController dc = new DatabaseController();
	private Views currentView;
	private JButton logoutButton;
	
	private Object[][] studentData;
	
	public TeacherSystemController(User mainUser, TeacherView tview) throws Exception {
		user = mainUser;
		tv = tview;
		
		initDefaultView();
	}
	
	public User getUser() {
		return user;
	}
	
	public static void main(String[] args) {
		// Main stuff
	}
	
	
	public void initController() {		
		
	}
	
	public void initDefaultView() throws Exception {
		tv.setStudentsData(getStudentsData());
		tv.loadUI();
		currentView = Views.TEACHERVIEW;
		// Action listener for Add Grades button
		tv.getAddButton().addActionListener(e -> {				
			try {
				changeView(Views.ADDGRADES);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
		tv.getCalculateButton().addActionListener(e -> {
			try {
				calculateMean();
				changeView(Views.TEACHERVIEW);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
	}

	private void calculateMean() throws Exception{}
	
	public String[] getAvailableModules() throws Exception {
		String query = "SELECT module_code FROM modules LIMIT ?";
		ArrayList<String[]> values = new ArrayList<String[]>();
		values.add(new String[] {"1000", ""});
		ArrayList<String[]> results = dc.executeQuery(query, values);
		String[] availableModules = new String[results.size()];
		
		for (int i = 0; i < results.size(); i++) {
			availableModules[i] = results.get(i)[0];
		}
		
		return availableModules;
	}
	 /*
	  * for modules that is already in the enrolled table
	public String[] getAvailableModules() throws Exception {
		String query = "SELECT module_code FROM enrolled LIMIT ?";
		ArrayList<String[]> values = new ArrayList<String[]>();
		values.add(new String[] {"1000", ""});
		ArrayList<String[]> results = dc.executeQuery(query, values);
		String[] availableModules = new String[results.size()];
		
		for (int i = 0; i < results.size(); i++) {
			availableModules[i] = results.get(i)[0];
		}
		
		return availableModules;
	}
	
	** for available students to edit
	public String[] getAvailableStudents() throws Exception {
		String query = "SELECT reg_number FROM enrolled LIMIT ?";
		ArrayList<String[]> values = new ArrayList<String[]>();
		values.add(new String[] {"1000", ""});
		ArrayList<String[]> results = dc.executeQuery(query, values);
		String[] availableStudents = new String[results.size()];
		
		for (int i = 0; i < results.size(); i++) {
			availableStudents[i] = results.get(i)[0];
		}
		
		return availableStudents;
	}
	*/
	
	public void changeView(Views changeTo) throws Exception {
		if (currentView == Views.TEACHERVIEW) {
			tv.removeUI();
		} else if (currentView == Views.ADDGRADES) {
			ag.removeUI();
		} 
		
		if (changeTo == Views.TEACHERVIEW) {
			initDefaultView();
		} else if (changeTo == Views.ADDGRADES) {
			initAddGradesView();
		}
	}
	
	
	public Object[][] getStudentsData() throws Exception {
		
		String query = "SELECT * FROM enrolled LIMIT ? ORDER BY module_code";
		ArrayList<String[]> values = new ArrayList<String[]>();
		values.add(new String[] {"1000", ""});
		ArrayList<String[]> results = dc.executeQuery(query, values);
		
		Object[][] data = new Object[results.size()][9];
		
		for (int i = 0; i < results.size(); i++) {
			int row = 0;
			data[row][0] = Integer.parseInt(results.get(i)[0]);
			data[row][1] = results.get(i)[1];
			data[row][2] = results.get(i)[2];
			data[row][3] = results.get(i)[3];
			row++;
		}
		
		studentData = data;
		
		return data;
	}
	
	public void initAddGradesView() throws Exception {
		if (ag == null)
			ag = new AddGrades(tv.getFrame());
		ag.setAvailableModules(getAvailableModules());
		ag.loadUI();
		currentView = Views.ADDGRADES;
		// Action listener for Back button
		ag.getBackButton().addActionListener(e -> {				
			try {
				changeView(Views.TEACHERVIEW);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
		// Action listener for Apply button
		ag.getApplyButton().addActionListener(e -> {
			try {
				Enrolled student = ag.getNewGrades();

				if (student != null) {
						try {
							String query = "INSERT INTO enrolled VALUES(?, ?, ?, ?)";
							ArrayList<String[]> values = new ArrayList<String[]>();
							
							values.add(new String[] {Integer.toString(student.getRegNo()), "false"});
							values.add(new String[] {student.getCode(), "true"});
							values.add(new String[] {Integer.toString(student.getRes1()), "false"});
							values.add(new String[] {Integer.toString(student.getRes2()), "false"});

							dc.executeQuery(query, values);
							changeView(Views.TEACHERVIEW);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane optionPane = new JOptionPane("Error connecting to dabatase.", JOptionPane.ERROR_MESSAGE);    
				JDialog dialog = optionPane.createDialog("Error");
				dialog.setAlwaysOnTop(true);
				dialog.setVisible(true);
			}
		});
		
		// Action listener for Logout Button
		logoutButton = ag.getLogoutButton();
		logoutButton.addActionListener(e -> {
			ag.removeUI();
		});
	}
	
}