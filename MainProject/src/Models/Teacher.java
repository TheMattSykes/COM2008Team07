/**
 * Teacher Model
 * 
 * Defines attributes and methods for teachers.
 */

package Models;

public class Teacher {

	// Attributes
	private int teacherID;
	private String departmentName;
	private String title;
	private String firstName;
	private String secondName;
	
	public Teacher() {}
	
	// Polymorphic alternative constructor
	public Teacher(int ti, String dn, String t, String fn, String sn) {
		teacherID = ti;
		departmentName = dn;
		title = t;
		firstName = fn;
		secondName = sn;
	}
	
	// Get/set methods
	public int getTeacherID() {
		return teacherID;
	}
	
	public void setID(int inputID) {
		teacherID = inputID;
	}
	
	public String getDepartmentName() {
		return departmentName;
	}
	
	public void setDepartmentName(String inputDN) {
		title = inputDN;
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
	
}
