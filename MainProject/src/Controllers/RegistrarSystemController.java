package Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;

import Models.Classification;
import Models.Grades;
import Models.GraduateType;
import Models.Module;
import Models.User;
import Views.LoginView;
import Views.PrimaryFrame;
import Views.RegistrarView;

import java.util.Collection;
import java.util.Collections;

public class RegistrarSystemController extends Controller {
	
	User user;
	RegistrarView rv;
	DatabaseController dc = new DatabaseController();
	
	public RegistrarSystemController(User mainUser, RegistrarView rview) throws Exception {
		user = mainUser;
		rv = rview;
		
		initView();
	}
	
	public User getUser() {
		return user;
	}
	
	public static void main(String[] args) {
		// Main stuff
	}
	
	
	public void initController() {		
		
	}
	
	public void initView() throws Exception {
		rv.setData(getTableData());
		rv.loadUI();
	}
	
	
	public Object[][] getTableData() throws Exception {
		
		String query = "SELECT * FROM students WHERE reg_number = ?";
		ArrayList<String[]> values = new ArrayList<String[]>();
		values.add(new String[] {"12345678", ""});
		ArrayList<String[]> results = dc.executeQuery(query, values);
		
		for (int i = 0; i < results.size(); i++) {
			for (String result : results.get(i)) {
				System.out.println(result);
			}
		}
		
		Object[][] data = new Object[50][8];
		
		ArrayList<Module> modules = new ArrayList<Module>();
		
		modules.add(new Module("COM1101","Web Design with Scratch",40,new int[]{20,40},new Grades[]{Grades.FAIL, Grades.PASS}, "", 1, GraduateType.UNDERGRADUATE));
		modules.add(new Module("COM1103","BASIC Programming on a Typewriter",20,new int[]{90,0},new Grades[]{Grades.PASS, Grades.UNDEFINED}, "", 1, GraduateType.UNDERGRADUATE));
		modules.add(new Module("COM1105","Lab Robots That Don't Work",20,new int[]{50,0},new Grades[]{Grades.PASS, Grades.UNDEFINED}, "", 1, GraduateType.UNDERGRADUATE));
		modules.add(new Module("COM1106","Introduction to Dirk",20,new int[]{80,0},new Grades[]{Grades.PASS, Grades.UNDEFINED}, "", 1, GraduateType.UNDERGRADUATE));
		modules.add(new Module("COM1107","Data Driven Ducks",20,new int[]{60,0},new Grades[]{Grades.PASS, Grades.UNDEFINED}, "", 1, GraduateType.UNDERGRADUATE));
		
		int row = 0;
		for (Module module : modules) {
			data[row][0] = module.getCode();
			System.out.println("Code: "+data[row][0]);
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
			
			float weightedScore = (((float)credits / (float)yearCredits) * (float)score) * 100;
			
			if (type == GraduateType.UNDERGRADUATE) {
				levelTotals[level-1] += weightedScore;
			} else {
				postGradTotal += weightedScore;
			}
		}
		
		float finalValue = 0;
		
		if (type == GraduateType.UNDERGRADUATE) {
			
			if (!fourYearCourse) {
				finalValue = ( ((1/3)*levelTotals[1]) + ((2/3)*levelTotals[2]) );
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
}
