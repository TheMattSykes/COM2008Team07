package Controllers;

import java.util.ArrayList;
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
import utils.GradingUtils;
import Views.EditGrades;
import Views.TeacherView;
import Views.Progress;

/**
 * The TeacherSystemController controls the parts of the system, which are accessed by all users of type teacher.
 * @author Matt Sykes, Amira Abraham
 */
public class TeacherSystemController extends Controller {
	
	private TeacherView tv;
	private ViewGrades vg;
	private EditGrades eg;
	private Progress pg;
	private Student selectedStudent;
	private DatabaseController dc;
	private Views currentView;
	private ArrayList<Integer> regNumbersInUse;
	private Module selectedModule;
	
	private Object[][] studentData;
	private Object[][] tableData;
	
	/**
	 * TeacherSystemController()
	 * The constructor of the controller.
	 * @param mainUser is the user object, the teacher in this case
	 * @param rview is the default teacher view
	 * @throws Exception
	 */
	public TeacherSystemController(User mainUser, TeacherView tview) throws Exception {
		super(mainUser);
		
		tv = tview;
		
		dc = new DatabaseController();
		
		initDefaultView();
	}
	
	/**
	 * getUser()
	 * @return the user, which will be of type teacher in this case
	 */
	public User getUser() {
		return user;
	}
	
	public static void main(String[] args) {
		// Main stuff
	}
	
	
	public void initController() {		
		
	}
	
	/**
	 * initDefaultView()
	 * Initialises (loads) the default view for the teacher
	 * @throws Exception
	 */
	public void initDefaultView() throws Exception {
		tv.setStudentsData(getStudentsData());
		tv.loadUI();
		currentView = Views.TEACHERVIEW;
        
		// Action listener for View Grades Button
		tv.getViewButton().addActionListener(e -> {
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
				selectedStudent.setProgress((String)studentData[row][9]);
				changeView(Views.VIEWGRADES);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
	}

	/**
	 * initViewGradesView()
	 * Initialises (loads) the View Grades view, which is where the teacher can view all the enrolled modules by a student.
	 * (includes module code, name, credits, scores, grades, level and period)
	 * @throws Exception
	 */
	public void initViewGradesView() throws Exception {
		if (vg == null)
			vg = new ViewGrades(tv.getFrame());
		
		vg.setStudent(selectedStudent);
		vg.setData(getTableData());
		vg.loadUI();
		currentView = Views.VIEWGRADES;
		
		JButton editButton = vg.getEditButton();
		
		// Action listener for Edit Grades Button
		vg.getEditButton().addActionListener(e -> {
			try {
				if (!editButton.isEnabled())
	        		editButton.setEnabled(true);
				
				
	        		JTable table = vg.getTable();
				selectedModule = new Module();
				int row = table.getSelectedRow();
				
				selectedModule.setCode((String)tableData[row][0]);
				selectedModule.setName((String)tableData[row][1]);
				selectedModule.setCredits((int)tableData[row][2]);
				
				
				GradingUtils gu = new GradingUtils();
				
				String valueOne = "";
				String valueTwo = "";
				
				if (tableData[row][3] != null && tableData[row][3] != "") {
					valueOne = tableData[row][3].toString();
				}
				
				if (tableData[row][5] != null && tableData[row][5] != "") {
					valueTwo = tableData[row][5].toString();
				}
				
				String[] results = new String[] {valueOne, valueTwo};
				
				gu.studentResults(results);
				
				Grades[] studentGrades = gu.getStudentGrades();
				int[] studentResults = gu.getStudentResults();
				
				selectedModule.setScores(studentResults);
				selectedModule.setGrades(studentGrades);
				selectedModule.setLevel((int)tableData[row][7]);
				selectedModule.setTeachingPeriod((String)tableData[row][8]);
				changeView(Views.EDITGRADES);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
		// Action listener for Year Progression button
		vg.getProgButton().addActionListener(e -> {				
			try {
				changeView(Views.PROGRESS);
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
	
	}
	
    public void gradeInputError() {
		
	}
	
	/**
	 * editGrades()
	 * Gets the new grades of a module which the student is taking (after the teacher
	 * has made all the changes they wanted).
	 * @return the module object, with all the required changes being made to it.
	 */
	public Module editGrades(Module m) {
		
		String score1String = eg.getG1TextField().getText();
		String score2String = eg.getG2TextField().getText();
		
		int score1 = 0;
		int score2 = 0;
		
		Grades grade1 = Grades.UNDEFINED;
		Grades grade2 = Grades.UNDEFINED;
		
		if (score1String != "") {
			try {
				int score1try = Integer.parseInt(score1String);
				
				if (score1try >= 0 && score1try <= 100) {
					
					score1 = score1try;
					
					if (score1try >= 40) {
						grade1 = Grades.PASS;
					} else {
						grade1 = Grades.FAIL;
					}
				} else {
					gradeInputError();
				}
			} catch (NumberFormatException e) {
				gradeInputError();
			}
		} else {
			gradeInputError();
		}
		
		if (score2String != "") {
			try {
				int score2try = Integer.parseInt(score2String);
				
				if (score2try >= 0 && score2try <= 100) {
					
					score2 = score2try;
					
					if (score2try >= 40) {
						grade2 = Grades.PASS;
					} else {
						grade2 = Grades.FAIL;
					}
				} else {
					gradeInputError();
				}
			} catch (NumberFormatException e) {
				gradeInputError();
			}
		} else {
			gradeInputError();
		}
		
		m.setScores(new int[] {score1, score2});
		m.setGrades(new Grades[] {grade1, grade2});
		
		return m;
	}
	
	/**
	 * initEditStudentView()
	 * Initialises (loads) the Edit Student view, which is where the teacher can edit the grades of a module taken by the student.
	 * @throws Exception
	 */
	public void initEditGradesView() throws Exception {
		if (eg == null)
			eg = new EditGrades(tv.getFrame());
        
		eg.setSelectedModule(selectedModule);
		eg.loadUI();
		currentView = Views.EDITGRADES;
		
		// Action listener for Back button
		eg.getBackButton().addActionListener(e -> {				
			try {
				changeView(Views.VIEWGRADES);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
		// Action listener for Apply button
		eg.getApplyButton().addActionListener(e -> {
			try {
				selectedModule = editGrades(selectedModule);
				String query = "";
				ArrayList<String[]> values = new ArrayList<String[]>();
						
				// Each value String[] has (1) the data, (2) boolean, which denotes whether it is a string
				
				int[] grades = selectedModule.getScores();
				Grades[] g = selectedModule.getGrades();
				
				if (grades.length > 0 && g[0] != Grades.UNDEFINED) {
					query = "UPDATE enrolled SET grade1 = ? WHERE module_code = ? AND reg_number = ?";
					values.add(new String[] {Integer.toString(grades[0]), "false"});
					
					if (grades.length > 1 && g[1] != Grades.UNDEFINED) {
						query = "UPDATE enrolled SET grade1 = ?, grade2 = ? WHERE module_code = ? AND reg_number = ?";
						values.add(new String[] {Integer.toString(grades[1]), "false"});
					}
				}
				
				values.add(new String[] {selectedModule.getCode(), "true"});
				
				values.add(new String[] {Integer.toString(selectedStudent.getRegNumber()), "false"});
				
				dc.executeQuery(query, values);
				changeView(Views.VIEWGRADES);
				} catch (Exception ex) {
			      ex.printStackTrace();
			      JOptionPane optionPane = new JOptionPane("Error in input", JOptionPane.ERROR_MESSAGE);    
				  JDialog dialog = optionPane.createDialog("Error");
				  dialog.setAlwaysOnTop(true);
				  dialog.setVisible(true);
				}
		    });
	}
	
	/**
	 * initProgressView()
	 * Initialises (loads) the Progress view, which is where the teacher can change the year progression of a student.
	 * @throws Exception
	 */
	public void initProgressView() throws Exception {
		if (pg == null)
			pg = new Progress(tv.getFrame());
        
		pg.setStudent(selectedStudent);
		pg.loadUI();
		currentView = Views.PROGRESS;
		
		// Action listener for Back button
		pg.getBackButton().addActionListener(e -> {				
			try {
				changeView(Views.VIEWGRADES);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
		// Action listener for Apply button
		pg.getApplyButton().addActionListener(e -> {
			try {
				Student student = pg.getYearProgression();
				String query = "UPDATE students SET year_progression = ? WHERE reg_number = ?";
				ArrayList<String[]> values = new ArrayList<String[]>();
						
				// Each value String[] has (1) the data, (2) boolean, which denotes whether it is a string
				values.add(new String[] {student.getProgress(), "true"});
				values.add(new String[] {Integer.toString(student.getRegNumber()), "true"});
						
				dc.executeQuery(query, values);
				changeView(Views.VIEWGRADES);
				} catch (Exception ex) {
			      ex.printStackTrace();
			      JOptionPane optionPane = new JOptionPane("Error connecting to dabatase.", JOptionPane.ERROR_MESSAGE);    
				  JDialog dialog = optionPane.createDialog("Error");
				  dialog.setAlwaysOnTop(true);
				  dialog.setVisible(true);
				}
		    });
		}
	
	/**
	 * changeView()
	 * Changes the view of the teacher.
	 * @param changeTo is the view you wish to change to (has to be a view for the teacher)
	 * @throws Exception
	 */
	public void changeView(Views changeTo) throws Exception {
		if (currentView == Views.TEACHERVIEW) {
			tv.removeUI();
		} else if (currentView == Views.VIEWGRADES) {
			vg.removeUI();
		} else if (currentView == Views.EDITGRADES) {
			eg.removeUI();
		} else if (currentView == Views.PROGRESS) {
			pg.removeUI();
		} 
		
		if (changeTo == Views.TEACHERVIEW) {
			initDefaultView();
		} else if (changeTo == Views.VIEWGRADES) {
			initViewGradesView();
		} else if (changeTo == Views.EDITGRADES) {
			initEditGradesView();
		} else if (changeTo == Views.PROGRESS) {
			initProgressView();
		}
	}
	
	/**
	 * getStudentsData()
	 * Gets the data of all the students currently in the database, so they can be displayed in a table.
	 * @throws Exception
	 */
	public Object[][] getStudentsData() throws Exception {
		
		String query = "SELECT * FROM students LIMIT ?";
		ArrayList<String[]> values = new ArrayList<String[]>();
		values.add(new String[] {"1000", ""});
		ArrayList<String[]> results = dc.executeQuery(query, values);
		
		ArrayList<Student> students = new ArrayList<Student>();
		regNumbersInUse = new ArrayList<Integer>();
		
		for (int i = 0; i < results.size(); i++) {
			students.add(new Student(Integer.parseInt(results.get(i)[0]),results.get(i)[1],results.get(i)[2],
					results.get(i)[3],results.get(i)[4], results.get(i)[5], results.get(i)[6], 
					results.get(i)[7].charAt(0), Integer.parseInt(results.get(i)[8]), results.get(i)[10],results.get(i)[11]));
			regNumbersInUse.add(Integer.parseInt(results.get(i)[0]));
		}
		
		Object[][] data = new Object[results.size()][10];
		
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
			data[row][9] = student.getProgress();
			
			row++;
		}
		
		studentData = data;
		
		return data;
	}
	
	/**
	 * getTableData()
	 * Gets all the modules the selected student is currently enrolled in.
	 * @return the currently enrolled modules, by the selected student.
	 * @throws Exception
	 */
	public Object[][] getTableData() throws Exception {
		DatabaseController dc = new DatabaseController();
				
		String query = String.format("SELECT m.module_code,m.module_name,m.credits,e.grade1,e.grade2,a.level,m.teaching_period,m.graduation_level " + 
				"FROM modules AS m, enrolled AS e, approval AS a " + 
				"WHERE e.reg_number = ? AND e.module_code = m.module_code AND a.module_code = m.module_code ORDER BY module_code;");
		
		ArrayList<String[]> values = new ArrayList<String[]>();
		
		values.add(new String[]{Integer.toString(selectedStudent.getRegNumber()),"true"});
		
		ArrayList<String[]> allResults = dc.executeQuery(query,values);
		
		ArrayList<Module> modules = new ArrayList<Module>();
		
		if (allResults.size() > 0) {
			
			for (String[] result : allResults) {
				Module newModule = new Module();
				
				String code = result[0];
				newModule.setCode(code);
				
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
		
		GradingUtils gu = new GradingUtils();
		
		Classification classi = gu.calculateClass(GraduateType.UNDERGRADUATE, modules.toArray(new Module[modules.size()]), selectedStudent);
		
		vg.setClassification(classi);
		vg.setYearAverages(gu.getYearAverages());
		
		Object[][] data = new Object[modules.size()][9];
		
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
			data[row][8] = module.getTeachingPeriod();
			
			row++;
		}
	 
		tableData = data;
		
		return data;
	}
	
	/**
<<<<<<< HEAD
	 * getMax()
	 * Gets the biggest int, of an array of ints.
	 * @param scores is the array of ints
	 * @return the biggest int in the array
	 */
	public int getMax(int[] scores) {
		int max = 0;
		
		for (int score : scores) {
			if (score > max) {
				max = score;
			}
		}
		
		return max;
	}
	
	/**
	 * calculateClass()
	 * Calculates the degree classification of a student, given their graduate type and array of modules.
	 * @param type is the type of degree the student is taking
	 * @param mods is the array of modules the student is taking
	 * @return the degree classification
	 */
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
		
		if (selectedStudent.getLevel() >= 3) {
			if (type == GraduateType.UNDERGRADUATE) {
				
				if (!fourYearCourse) {
					float convertedLv2Total = (float) ((1.0/3.0)*levelTotals[1]);
					float convertedLv3Total = (float) ((2.0/3.0)*levelTotals[2]);
					
					finalValue = ( convertedLv2Total + convertedLv3Total );
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
	
	/**
=======
>>>>>>> 6a9658a6907bd9a6f39a83ca803fba7bbfc2c7c7
	 * removeAllUI()
	 * Removes all the UI of the teacher (used when logging out).
	 */
	public void removeAllUI() {
		if (tv != null)
			tv.removeUI();
		if (vg != null)
			vg.removeUI();
		if (eg != null)
			eg.removeUI();
	}
}
