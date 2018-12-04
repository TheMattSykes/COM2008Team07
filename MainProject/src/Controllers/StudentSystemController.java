/**
 * 
 */

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
import utils.GradingUtils;

import java.util.Collection;
import java.util.Collections;

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
	
	public Student setupStudent() throws Exception {
		System.out.println("Setting up new student...");
		
		Student student = null;
		
		DatabaseController dc = new DatabaseController();
		
		String query = String.format("SELECT * FROM students WHERE userID = ?");
		
		ArrayList<String[]> values = new ArrayList<String[]>();
		
		values.add(new String[]{Integer.toString(user.getUserID()),"false"});
		
		Boolean exists = false;
		
		String[] queries = {query};
		
		ArrayList<String[]> allResults = dc.executeQuery(query,values);
		String[] results = null;
		
		if(allResults.size() > 0) {
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
			student.setRegistered(results[10]);
			student.setProgress(results[11]);
			
			sv.setStudent(student);
		}
		
		return student;
	}
	
	public void initView() throws Exception {
		sv.setData(getTableData());
		sv.loadUI();
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
				
				//for (int i = 0; i < result.length; i++) {
					//System.out.println("Result No. "+i+": "+result[i]);
				//}
				
				newModule.setName(result[1]);
				
				newModule.setCredits(Integer.parseInt(result[2]));
				
				int[] studentResults = new int[2];
				
				GradingUtils su = new GradingUtils();
				
				String valueOne = "";
				String valueTwo = "";
				
				if (result[3] != null && result[3] != "") {
					valueOne = result[3].toString();
				}
				
				if (result[4] != null && result[4] != "") {
					valueTwo = result[4].toString();
				}
				
				String[] res = new String[] {valueOne, valueTwo};
				
				su.studentResults(res);
				
				newModule.setScores(su.getStudentResults());
				newModule.setGrades(su.getStudentGrades());
				
				newModule.setLevel(Integer.parseInt(result[5]));
				
				newModule.setTeachingPeriod(result[6]);
				newModule.setType(GraduateType.valueOf(result[7].toUpperCase()));
				
				modules.add(newModule);
			}
		}
		
		
		GradingUtils gu = new GradingUtils();
		sv.setClassification(gu.calculateClass(GraduateType.UNDERGRADUATE, modules.toArray(new Module[modules.size()]), studentUser));
		sv.setYearAverages(gu.getYearAverages());
		
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
