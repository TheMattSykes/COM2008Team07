package Controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import Models.Classification;
import Models.Grades;
import Models.GraduateType;
import Models.Module;
import Models.Student;
import Models.User;
import Models.Views;
import Views.ViewGrades;
import Views.EditGrades;
import Views.TeacherView;

public class TeacherSystemController extends Controller {
	
	private TeacherView tv;
	private Student studentUser;
	private ViewGrades vg;
	private EditGrades eg;
	private Student selectedStudent;
	private DatabaseController dc;
	private Views currentView;
	private ArrayList<Integer> regNumbersInUse;
	private Module newModule;
	private JButton logoutButton;
	
	private Object[][] studentData;
	
	public TeacherSystemController(User mainUser, TeacherView tview) throws Exception {
		super(mainUser);
		
		tv = tview;
		
		dc = new DatabaseController();
		
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
        
		// Action listener for View Grades Button
		tv.getEditButton().addActionListener(e -> {
			try {
				JTable table = tv.getTable();
				selectedStudent = new Student();
				int row = table.getSelectedRow();
				selectedStudent.setCode((int)studentData[row][0]);
				selectedStudent.setTitle((String)studentData[row][1]);
				selectedStudent.setSecondName((String)studentData[row][2]);
				selectedStudent.setFirstName((String)studentData[row][3]);
				selectedStudent.setDegree((String)studentData[row][4]);
				selectedStudent.setEmail((String)studentData[row][5]);
				selectedStudent.setTutor((String)studentData[row][6]);
				selectedStudent.setPeriod((Character)studentData[row][7]);
				selectedStudent.setLevel((int)studentData[row][8]);
				selectedStudent.setRegistered((String)studentData[row][9]);
				changeView(Views.VIEWGRADES);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
		// Action listener for Logout Button
		logoutButton = tv.getLogoutButton();
		logoutButton.addActionListener(e -> {
			tv.removeUI();
		});
	}

	public void initviewGradesView() throws Exception {
		if (vg == null)
			vg = new ViewGrades(tv.getFrame());
		
		vg.setStudent(selectedStudent);
		vg.setData(getTableData());
		vg.loadUI();
		currentView = Views.VIEWGRADES;
		
		// Action listener for Edit Grades Button
		vg.getEditButton().addActionListener(e -> {
			try {
				JTable table = vg.getTable();
				changeView(Views.EDITGRADES);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
		// Action listener for Back button
		vg.getBackButton().addActionListener(e -> {				
			try {
				changeView(Views.TEACHERVIEW);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
		// Action listener for Logout Button
		logoutButton = vg.getLogoutButton();
		logoutButton.addActionListener(e -> {
			vg.removeUI();
		});
	
	}
	
	public void initeditGradesView() throws Exception {
		if (eg == null)
			eg = new EditGrades(tv.getFrame());
        
		eg.loadUI();
		currentView = Views.EDITGRADES;
		// Action listener for Back button
		eg.getBackButton().addActionListener(e -> {				
			try {
				changeView(Views.TEACHERVIEW);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
		// Action listener for Logout Button
		logoutButton = eg.getLogoutButton();
		logoutButton.addActionListener(e -> {
			eg.removeUI();
		});
	}
	
	// Changes to the specified view
	public void changeView(Views changeTo) throws Exception {
		if (currentView == Views.TEACHERVIEW) {
			tv.removeUI();
		} else if (currentView == Views.VIEWGRADES) {
			vg.removeUI();
		} 
		
		if (changeTo == Views.TEACHERVIEW) {
			initDefaultView();
		} else if (changeTo == Views.VIEWGRADES) {
			initviewGradesView();
		}
	}
	
	// Gets the data of all the students currently in the database
	public Object[][] getStudentsData() throws Exception {
		
		String query = "SELECT * FROM students LIMIT ?";
		ArrayList<String[]> values = new ArrayList<String[]>();
		values.add(new String[] {"1000", ""});
		ArrayList<String[]> results = dc.executeQuery(query, values);
		
		ArrayList<Student> students = new ArrayList<Student>();
		regNumbersInUse = new ArrayList<Integer>();
		
		for (int i = 0; i < results.size(); i++) {
			students.add(new Student(Integer.parseInt(results.get(i)[0]),results.get(i)[1],results.get(i)[2],results.get(i)[3],results.get(i)[4], results.get(i)[5], results.get(i)[6], results.get(i)[7].charAt(0), Integer.parseInt(results.get(i)[8]), results.get(i)[10]));
			regNumbersInUse.add(Integer.parseInt(results.get(i)[0]));
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
	
	public Object[][] getTableData() throws Exception {
		DatabaseController dc = new DatabaseController();
		
		Boolean exists = false;
		
		String query = String.format("SELECT m.module_code,m.module_name,m.credits,e.grade1,e.grade2,a.level,m.teaching_period,m.graduation_level " + 
				"FROM modules AS m, enrolled AS e, approval AS a " + 
				"WHERE e.reg_number = ? AND e.module_code = m.module_code AND a.module_code = m.module_code ORDER BY module_code;");
		
		ArrayList<String[]> values = new ArrayList<String[]>();
		
		values.add(new String[]{Integer.toString(studentUser.getRegNumber()),"false"});
		
		
		String[] queries = {query};
		
		ArrayList<String[]> allResults = dc.executeQuery(query,values);
		String[] results = null;
		
		ArrayList<Module> modules = new ArrayList<Module>();
		
		if (allResults.size() > 0) {
			
			int count = 0;
			for (String[] result : allResults) {
				System.out.println("RESULT: "+count);
				count++;
				Module newModule = new Module();
				
				String code = result[0];
				newModule.setCode(code);
				
				for (int i = 0; i < result.length; i++) {
					System.out.println("Result No. "+i+": "+result[i]);
				}
				
				newModule.setName(result[1]);
				
				newModule.setCredits(Integer.parseInt(result[2]));
				
				int[] studentResults = new int[2];
				Grades[] studentGrades = new Grades[2];
				
				if (result[3] != null) {
					studentResults[0] = (int) Float.parseFloat(result[3]);
					
					if (studentResults[0] >= 40) {
						studentGrades[0] = Grades.PASS;
					} else {
						studentGrades[0] = Grades.FAIL;
					}
				} else {
					studentResults[0] = 0;
					studentGrades[0] = Grades.UNDEFINED;
				}
				
				if (result[4] != null) {
					studentResults[1] = (int) Float.parseFloat(result[4]);
					
					if (studentResults[1] >= 40) {
						studentGrades[1] = Grades.PASS;
					} else {
						studentGrades[1] = Grades.FAIL;
					}
				} else {
					studentResults[1] = 0;
					studentGrades[1] = Grades.UNDEFINED;
				}
				
				newModule.setScores(studentResults);
				newModule.setGrades(studentGrades);
				
				newModule.setLevel(Integer.parseInt(result[5]));
				
				newModule.setTeachingPeriod(result[6]);
				newModule.setType(GraduateType.valueOf(result[7].toUpperCase()));
				
				modules.add(newModule);
			}
		}
		
		Classification classi = calculateClass(GraduateType.UNDERGRADUATE, modules.toArray(new Module[modules.size()]));
		
		vg.setClassification(classi);
		
		Object[][] data = new Object[modules.size()][8];
		
		int row = 0;
		for (Module module : modules) {
			data[row][0] = module.getCode();
			data[row][1] = module.getName();
			data[row][2] = module.getCredits();
			
			int[] scores = module.getScores();
			Grades[] grades = module.getGrades();
			
			data[row][3] = scores[0];
			data[row][4] = grades[0].toString();
			
			if (grades[1] == Grades.UNDEFINED) {
				data[row][5] = "";
				data[row][6] = "";
			} else {
				data[row][5] = scores[1];
				data[row][6] = grades[1].toString();
			}
			
			data[row][7] = module.getLevel();
			
			row++;
		}
		
		return data;
	}
	
	public int getMax(int[] scores) {
		int max = 0;
		
		for (int score : scores) {
			if (score > max) {
				max = score;
			}
		}
		
		return max;
	}
	
	public Classification calculateClass(GraduateType type, Module[] mods) {
		
		float[] levelTotals = new float[4];
		float postGradTotal = 0;
		
		Boolean fourYearCourse = false;
		
		Boolean degreeFailed = false;
		
		int yearCredits = 0;
		
		// Determine how many credits are in a year for graduate type
		if (type == GraduateType.UNDERGRADUATE) {
			yearCredits = 120;
		} else {
			yearCredits = 180;
		}
		
		// Get all module scores by level
		for (Module mod : mods) {
			int level = mod.getLevel();
			int[] scores = mod.getScores();
			int score = getMax(scores);
			int credits = mod.getCredits();
			
			if (level == 4) {
				fourYearCourse = true;
			}
			
			float weightedScore = (((float)credits / (float)yearCredits) * (float)score);
			
			
			if (type == GraduateType.UNDERGRADUATE) {
				levelTotals[level-1] += weightedScore;
				
				if (score < 40 && level != 4) {
					degreeFailed = true;
				} 
				
				if (score < 50 && level == 4) {
					degreeFailed = true;
				}
			} else {
				postGradTotal += weightedScore;
			}
		}
		
		
		vg.setYearAverages(levelTotals);
		
		
		float finalValue = 0;
		
		if (studentUser.getLevel() >= 3) {
			if (type == GraduateType.UNDERGRADUATE) {
				
				if (!fourYearCourse) {
					float convertedLv2Total = (float) ((1.0/3.0)*levelTotals[1]);
					float convertedLv3Total = (float) ((2.0/3.0)*levelTotals[2]);
					
					finalValue = ( convertedLv2Total + convertedLv3Total );
					
					System.out.println("YEAR 2 Total: "+levelTotals[1]+"    (1/3 Version): "+convertedLv2Total);
					System.out.println("YEAR 3 Total: "+levelTotals[2]+"    (1/3 Version): "+convertedLv3Total);
					System.out.println("Final Value: "+finalValue);
				} else {
					finalValue = ( ((1/5)*levelTotals[1]) + ((2/5)*levelTotals[2]) + ((2/5)*levelTotals[3]) );
				}
				
				if (finalValue < 39.5 || degreeFailed) {
					return Classification.FAIL;
				} else if (finalValue >= 39.5 && finalValue < 44.5) {
					return Classification.PASS;
				} else if (finalValue >= 44.5 && finalValue < 49.5) {
					return Classification.THIRD;
				} else if (finalValue >= 49.5 && finalValue < 59.5) {
					return Classification.LOWER_SECOND;
				} else if (finalValue >= 59.5 && finalValue < 69.5) {
					return Classification.UPPER_SECOND;
				} else {
					return Classification.FIRST;
				}
			} else {
				finalValue = postGradTotal;
				
				if (finalValue < 49.5) {
					return Classification.FAIL;
				} else if (finalValue >= 49.5 && finalValue < 59.5) {
					return Classification.PASS;
				} else if (finalValue >= 59.5 && finalValue < 69.5) {
					return Classification.MERIT;
				} else {
					return Classification.DISTINCTION;
				}
			}
		}
		
		return Classification.INCOMPLETE;
	}
}
