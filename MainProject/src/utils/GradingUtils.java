package utils;

import Models.Classification;
import Models.Grades;
import Models.GraduateType;
import Models.Module;
import Models.Student;

public class GradingUtils {
	
	private float[] yearAverages;
	private int[] studentResults;
	private Grades[] studentGrades;
	
	public float[] getYearAverages() {
		return yearAverages;
	}
	
	public int[] getStudentResults() {
		return studentResults;
	}
	
	public Grades[] getStudentGrades() {
		return studentGrades;
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
	
	public void studentResults(String[] res) {
		
		studentResults = new int[2];
		studentGrades = new Grades[2];
		
		if (res[0] != null && res[0] != "") {
			studentResults[0] = (int) Float.parseFloat(res[0]);
			
			if (studentResults[0] >= 40) {
				studentGrades[0] = Grades.PASS;
			} else {
				studentGrades[0] = Grades.FAIL;
			}
		} else {
			studentResults[0] = 0;
			studentGrades[0] = Grades.UNDEFINED;
		}
		
		if (res[1] != null && res[1] != "") {
			studentResults[1] = (int) Float.parseFloat(res[1]);
			
			if (studentResults[1] >= 40) {
				studentGrades[1] = Grades.PASS;
			} else {
				studentGrades[1] = Grades.FAIL;
			}
		} else {
			studentResults[1] = 0;
			studentGrades[1] = Grades.UNDEFINED;
		}
	}
	
	public Classification calculateClass(GraduateType type, Module[] mods, Student studentUser) {
		
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
		
		
		yearAverages = levelTotals;
		
		
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
