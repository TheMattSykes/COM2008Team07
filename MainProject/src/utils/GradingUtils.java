/**
 * GradingUtils
 * 
 * Has methods for classifying results and checking result inputs.
 */

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
	
	// Get maximum in array of int scores
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
	 * studentResults
	 * 
	 * Takes student results and converts them into ints and grades.
	 * If not present in database then show as undefined.
	 * 
	 * @param res
	 */
	public void studentResults(String[] res) {
		
		// Generate new arrays
		studentResults = new int[2];
		studentGrades = new Grades[2];
		
		// First result
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
		
		// Resit result
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
	
	
	/**
	 * calculateClass
	 * 
	 * Calculate a student's degree classification.
	 * Considers duration of degree and student type.
	 * 
	 * @param type
	 * @param mods
	 * @param studentUser
	 * @return
	 */
	public Classification calculateClass(GraduateType type, Module[] mods, Student studentUser) {
		
		float[] levelTotals = new float[4];
		float postGradTotal = 0;
		
		Boolean fourYearCourse = false;
		
		// Failed if not enough credits gained or fail in module.
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
		
		// Store totals for each level as averages
		yearAverages = levelTotals;
		
		
		float finalValue = 0;
		
		// If student is at level three or above, calculate class.
		if (studentUser.getLevel() >= 3) {
			if (type == GraduateType.UNDERGRADUATE) {
				
				if (!fourYearCourse) {
					
					// Three year course then 1/3 for year 2 and 2/3 for year 3
					float convertedLv2Total = (float) ((1.0/3.0)*levelTotals[1]);
					float convertedLv3Total = (float) ((2.0/3.0)*levelTotals[2]);
					
					// Calculate final value
					finalValue = ( convertedLv2Total + convertedLv3Total );
				} else {
					
					// Four year course then 1/5 for year 2 and 2/5 for year 3 and 2/5 for year 4
					finalValue = ( ((1/5)*levelTotals[1]) + ((2/5)*levelTotals[2]) + ((2/5)*levelTotals[3]) );
				}
				
				// Classify degree based on final value
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
				// Postgraduate classification
				
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
		
		// Degree incomplete otherwise
		return Classification.INCOMPLETE;
	}
}
