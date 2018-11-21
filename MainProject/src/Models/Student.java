package Models;

public class Student extends User {

	private int regNumber;
	private String title;
	private String firstName;
	private String secondName;
	private String degree;
	private String email;
	private String tutor;
	private int level;
	private GraduateType type;
	
	public Student() {}
	
	public Student(int r, String t, String fn, String sn, String d, String e, String tu, int l) {
		regNumber = r;
		title = t;
		firstName = fn;
		secondName = sn;
		degree = d;
		email = e;
		tutor = tu;
		level = l;
	}
	
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
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int inputLevel) {
		level = inputLevel;
	}

}
