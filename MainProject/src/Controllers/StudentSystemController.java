/**
 * StudentSystemController
 * 
 * Gathers student data from the database.
 * Generate student details table form.
 * 
 */

package Controllers;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import Models.Grades;
import Models.GraduateType;
import Models.Module;
import Models.Student;
import Models.User;
import Views.StudentView;
import utils.GradingUtils;

public class StudentSystemController extends Controller {
	
	private Student studentUser;
	private StudentView sv;
	
	public StudentSystemController(User mainUser, StudentView sview) throws Exception {
		
		super(mainUser);
		
		sv = sview;
		
		studentUser = setupStudent();
		
		initView();
	}
	
	public User getUser() {
		return user;
	}
	
	/**
	 * setupStudent
	 * 
	 * Retrieve students' information from the database.
	 * @return
	 * @throws Exception
	 */
	public Student setupStudent() throws Exception {
		Student student = null;
		
		DatabaseController dc = new DatabaseController();
		
		String query = String.format("SELECT * FROM students WHERE userID = ?");
		
		ArrayList<String[]> values = new ArrayList<String[]>();
		
		values.add(new String[]{Integer.toString(user.getUserID()),"false"});
		
		Boolean exists = false;
		
		ArrayList<String[]> allResults = dc.executeQuery(query,values);
		String[] results = null;
		
		if(allResults.size() > 0) {
			results = allResults.get(0);
		}
		
		if (results != null) {
			exists = (results.length > 0);
		}
		
		// If the student exists set information
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
			student.setRegistered(results[10]);
			student.setProgress(results[11]);
			
			sv.setStudent(student);
		} else {
			JOptionPane.showMessageDialog(null, "Account not linked to student. Please contact IT desk.", "Student Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		
		return student;
	}
	
	/**
	 * Initial View
	 * @throws Exception
	 */
	public void initView() throws Exception {
		sv.setData(getTableData());
		sv.loadUI();
	}
	
	/**
	 * getTableData
	 * 
	 * Retrieve students' module data.
	 * Setup information for table format.
	 * @return
	 * @throws Exception
	 */
	public Object[][] getTableData() throws Exception {
		DatabaseController dc = new DatabaseController();
		
		String query = String.format("SELECT m.module_code,m.module_name,m.credits,e.grade1,e.grade2,a.level,m.teaching_period,m.graduation_level " + 
				"FROM modules AS m, enrolled AS e, approval AS a " + 
				"WHERE e.reg_number = ? AND e.module_code = m.module_code AND a.module_code = m.module_code ORDER BY module_code;");
		
		ArrayList<String[]> values = new ArrayList<String[]>();
		
		values.add(new String[]{Integer.toString(studentUser.getRegNumber()),"false"});
		
		ArrayList<String[]> allResults = dc.executeQuery(query,values);
		
		ArrayList<Module> modules = new ArrayList<Module>();
		
		// If exists
		if (allResults.size() > 0) {
			
			int count = 0;
			
			// Use each result
			for (String[] result : allResults) {
				count++;
				Module newModule = new Module();
				
				String code = result[0];
				newModule.setCode(code);
				
				newModule.setName(result[1]);
				
				newModule.setCredits(Integer.parseInt(result[2]));
				
				GradingUtils su = new GradingUtils();
				
				String valueOne = "";
				String valueTwo = "";
				
				// Check grades exist
				if (result[3] != null && result[3] != "") {
					valueOne = result[3].toString();
				}
				
				if (result[4] != null && result[4] != "") {
					valueTwo = result[4].toString();
				}
				
				String[] res = new String[] {valueOne, valueTwo};
				
				su.studentResults(res, studentUser.getLevel());
				
				newModule.setScores(su.getStudentResults());
				newModule.setGrades(su.getStudentGrades());
				
				newModule.setLevel(Integer.parseInt(result[5]));
				
				newModule.setTeachingPeriod(result[6]);
				newModule.setType(GraduateType.valueOf(result[7].toUpperCase()));
				
				modules.add(newModule);
			}
		}
		
		
		GraduateType type = null;
		
		if (studentUser.getLevel() <= 4) {
			type = GraduateType.UNDERGRADUATE;
		} else {
			type = GraduateType.POSTGRADUATE;
		}
		
		// Get classification and year average information
		GradingUtils gu = new GradingUtils();
		sv.setClassification(gu.calculateClass(type, modules.toArray(new Module[modules.size()]), studentUser));
		sv.setYearAverages(gu.getYearAverages());
		
		
		// Set table data
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
	
	public void removeAllUI() {
		if (sv != null)
			sv.removeUI();
	}
}
