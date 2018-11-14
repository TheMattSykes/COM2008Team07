package Models;

import java.util.Arrays;
import java.util.List;
import java.util.Collection;
import java.util.Collections;

import Users.Classification;
import Users.GraduateType;
import Users.Module;

public class StudentSystemModel {
	public static void main(String[] args) {
		// Main stuff
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
