package Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;

import Models.Classification;
import Models.Grades;
import Models.GraduateType;
import Models.Module;
import Models.Student;
import Models.User;
import Views.LoginView;
import Views.PrimaryFrame;
import Views.StudentView;

import java.util.Collection;
import java.util.Collections;

public class StudentSystemController extends Controller {
	
	User user;
	Student studentUser;
	StudentView sv;
	
	public StudentSystemController(User mainUser, StudentView sview) throws Exception {
		
		user = mainUser;
		
		sv = sview;
		
		studentUser = setupStudent();
		
		initView();
	}
	
	public User getUser() {
		return user;
	}
	
	public static void main(String[] args) {
		// Main stuff
	}
	
	public Student setupStudent() throws Exception {
		System.out.println("Setting up new student...");
		
		Student student = null;
		
		DatabaseController dc = new DatabaseController();
		
		String query = String.format("SELECT * FROM students WHERE userID = ?");
		
		ArrayList<String[]> values = new ArrayList<String[]>();
		
		System.out.println("Setting up query...");
		values.add(new String[]{Integer.toString(user.getUserID()),"false"});
		
		Boolean exists = false;
		
		String[] queries = {query};
		
		System.out.println("Executing query...");
		ArrayList<String[]> allResults = dc.executeQuery(query,values);
		String[] results = null;
		
		if(allResults.size() > 0) {
			System.out.println("Getting results...");
			results = allResults.get(0);
		}
		
		if (results != null) {
			exists = (results.length > 0);
		}
		
		if (exists) {
			student = new Student();
			
			student.setCode(Integer.parseInt(results[0]));
			student.setTitle(results[1]);
			student.setSecondName(results[2]);
			student.setFirstName(results[3]);
			student.setDegree(results[4]);
			student.setEmail(results[5]);
			student.setTutor(results[6]);
			student.setPeriod(results[7].charAt(0));
			student.setLevel(Integer.parseInt(results[8]));
			System.out.println("Reg Number: "+results[0]);
			
			sv.setStudent(student);
		}
		
		return student;
	}
	
	
	public void initController() {
		
	}
	
	public void initView() throws Exception {
		sv.setData(getTableData());
		sv.loadUI();
	}
	
	
	public Object[][] getTableData() throws Exception {
		DatabaseController dc = new DatabaseController();
		
		
		Boolean exists = false;
		
		String query = String.format("SELECT module_code, grade1, grade2 FROM enrolled WHERE reg_number = ? ORDER BY module_code");
		
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
				
				int[] studentResults = new int[2];
				Grades[] studentGrades = new Grades[2];
				
				if (result[1] != null) {
					studentResults[0] = (int) Float.parseFloat(result[1]);
					System.out.println("STDUENT RESULT: "+studentResults[0]);
					
					if (studentResults[0] >= 40) {
						studentGrades[0] = Grades.PASS;
					} else {
						studentGrades[0] = Grades.FAIL;
					}
				} else {
					studentResults[0] = 0;
					studentGrades[0] = Grades.UNDEFINED;
				}
				
				if (result[2] != null) {
					studentResults[1] = (int) Float.parseFloat(result[2]);
					System.out.println("STDUENT RESULT 2: "+studentResults[1]);
					
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
				
				System.out.println("STARTING MOD QUERY...");
				String modQuery = String.format("SELECT module_name, credits, teaching_period, level, graduation_level FROM modules WHERE module_code = ?");
				ArrayList<String[]> modValues = new ArrayList<String[]>();
				modValues.add(new String[]{code,"true"});
				String[] modResults = dc.executeQuery(modQuery,modValues).get(0);
				
				newModule.setName(modResults[0]);
				newModule.setCredits(Integer.parseInt(modResults[1]));
				newModule.setTeachingPeriod(modResults[2]);
				newModule.setLevel(Integer.parseInt(modResults[3]));
				newModule.setType(GraduateType.valueOf(modResults[4].toUpperCase()));
				
				modules.add(newModule);
			}
		}
		
		Classification classi = calculateClass(GraduateType.UNDERGRADUATE, modules.toArray(new Module[modules.size()]));
		
		sv.setClassification(classi);
		
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
			
			System.out.println("Credits: "+(float)credits+" Year Credits: "+(float)yearCredits+" Credits Divided: "+
					((float)credits / (float)yearCredits)+" Weighted Score: "+weightedScore);
			
			if (type == GraduateType.UNDERGRADUATE) {
				levelTotals[level-1] += weightedScore;
			} else {
				postGradTotal += weightedScore;
			}
		}
		
		
		sv.setYearAverages(levelTotals);
		
		
		float finalValue = 0;
		
		System.out.println("STUDENT LEVEL: " + studentUser.getLevel());
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
				
				if (finalValue < 39.5) {
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
