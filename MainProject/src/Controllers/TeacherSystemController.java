package Controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JTable;

import Models.Student;
import Models.Teacher;
import Models.User;           
import Models.Views;
import Views.TeacherView;
import Views.AddGrades;

public class TeacherSystemController extends Controller {
	
	User user;
	TeacherView tv;
	AddGrades ag;
	DatabaseController dc = new DatabaseController();
	private Views currentView;
	
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
		tv.getAddButton().addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {					
					try {
						changeView(Views.ADDGRADES);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		);
		
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

	public void initAddGradesView() throws Exception {
		if (ag == null)
			ag = new AddGrades(tv.getFrame());
		ag.loadUI();
		currentView = Views.ADDGRADES;
		// Action listener for Back button
		ag.getBackButton().addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {					
					try {
						changeView(Views.TEACHERVIEW);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		);
	}
	
	private void calculateMean() throws Exception{}
	
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
		
		String query = "SELECT * FROM students LIMIT ?";
		ArrayList<String[]> values = new ArrayList<String[]>();
		values.add(new String[] {"1000", ""});
		ArrayList<String[]> results = dc.executeQuery(query, values);
		
		ArrayList<Student> students = new ArrayList<Student>();
		
		for (int i = 0; i < results.size(); i++) {
			students.add(new Student(Integer.parseInt(results.get(i)[0]),results.get(i)[1],results.get(i)[2],results.get(i)[3],results.get(i)[4], results.get(i)[5], results.get(i)[6], results.get(i)[7].charAt(0), Integer.parseInt(results.get(i)[8])));
		}
		
		Object[][] data = new Object[results.size()][9];
		
		int row = 0;
		for (Student student : students) {
			data[row][0] = student.getRegNumber();
			data[row][1] = student.getTitle();
			data[row][2] = student.getFirstName();
			data[row][3] = student.getSecondName();
			data[row][4] = student.getDegree();
			data[row][5] = student.getEmail();
			data[row][6] = student.getTutor();
			data[row][7] = student.getPeriod();
			data[row][8] = student.getLevel();
			
			row++;
		}
		
		studentData = data;
		
		return data;
	}
	
}