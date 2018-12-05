/**
 * Student Model
 * 
 * Defines attributes and methods for students.
 */

package Models;

public class Student {

	// Attributes
	private int regNumber;
	private String title;
	private String firstName;
	private String secondName;
	private String degree;
	private String email;
	private String tutor;
	private char period;
	private int level;
	private String registered;
	private String progress;
	
	public Student() {}
	
	// Polymorphic alternative constructor
	public Student(int r, String t, String fn, String sn, String d, String e, String tu, char p, int l, String reg, String prog) {
		regNumber = r;
		title = t;
		firstName = fn;
		secondName = sn;
		degree = d;
		email = e;
		tutor = tu;
		period = p;
		level = l;
		registered = reg;
		progress = prog;
	}
	
	// Get/set methods
	public int getRegNumber() {
		return regNumber;
	}
	
	public void setCode(int inputReg) {
		regNumber = inputReg;
	}
	
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String inputTitle) {
		title = inputTitle;
	}
	
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String inputFN) {
		firstName = inputFN;
	}
	
	public String getSecondName() {
		return secondName;
	}
	
	public void setSecondName(String inputSN) {
		secondName = inputSN;
	}
	
	public String getDegree() {
		return degree;
	}
	
	public void setDegree(String inputDegree) {
		degree = inputDegree;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String inputEmail) {
		email = inputEmail;
	}
	
	public String getTutor() {
		return tutor;
	}
	
	public void setTutor(String inputTutor) {
		tutor = inputTutor;
	}
	
	public char getPeriod() {
		return period;
	}
	
	public void setPeriod(char inputPeriod) {
		period = inputPeriod;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int inputLevel) {
		level = inputLevel;
	}
	
	public String getRegistered() {
		return registered;
	}
	
	public void setRegistered(String inputReg) {
		registered = inputReg;
	}
	
	public String getProgress() {
		return progress;
	}
	
	public void setProgress(String inputProg) {
		progress = inputProg;
	}
	
	// Check if student data is complete
	public boolean isComplete() {
		if (title != null && firstName.length() > 0 && firstName.length() <= 50 &&
			secondName.length() > 0 && secondName.length() <= 50 && degree != null &&
			tutor.length() > 0 && tutor.length() <= 50 && period != '\u0000' && level != 0) {
			return true;
		} else {
			return false;
		}
	}

}
