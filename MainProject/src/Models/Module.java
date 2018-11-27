package Models;

public class Module {
	private String code;
	private String name;
	private int credits;
	private int[] scores;
	private Grades[] grades;
	private String teachingPeriod;
	private int level;
	private GraduateType type;
	private String department;
	private String core;
	
	public Module() {}
	
	public Module(String c, String n, int cred, int[] sc, Grades[] gr, String tp, int l, GraduateType ty, String d, String core) {
		code = c;
		name = n;
		credits = cred;
		scores = sc;
		grades = gr;
		teachingPeriod = tp;
		level = l;
		type = ty;
		department = d;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String inputCode) {
		code = inputCode;
	}
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String inputName) {
		name = inputName;
	}
	
	
	public int getCredits() {
		return credits;
	}
	
	public void setCredits(int creditVal) {
		credits = creditVal;
	}
	
	
	public int[] getScores() {
		return scores;
	}
	
	public void setScores(int[] scoresVals) {
		scores = scoresVals;
	}
	
	
	public Grades[] getGrades() {
		return grades;
	}
	
	public void setGrades(Grades[] gradesVals) {
		grades = gradesVals;
	}
	
	
	public String getTeachingPeriod() {
		return teachingPeriod;
	}
	
	public void setTeachingPeriod(String teachPeriod) {
		teachingPeriod = teachPeriod;
	}
	
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int levelValue) {
		level = levelValue;
	}
	
	
	public GraduateType getType() {
		return type;
	}
	
	public void setType(GraduateType gradType) {
		type = gradType;
	}
	
	public String toString() {
		String result;
		if (grades[0] != Grades.FAIL && grades[0] != Grades.UNDEFINED) {
			result = grades[0].toString();
		} else if (grades[0] == Grades.FAIL) {
			result = grades[1].toString();
		} else {
			result = grades[0].toString();
		}
		return code + " - " + name + " (Grade: "+result+ ")";
	}
	
	public String getDepartment() {
		return department;
	}
	
	public void setDepartment(String inputDepartment) {
		department = inputDepartment;
	}
	
	public Boolean isCore() {
		return true;
	}
	
	public Boolean setCore(String inputCore) {
		core = inputCore;
		if (core.equals("yes")) {
			return true;
		}else if (core.equals("no")) {
			return false;
		} else {
			return false;
		}
		
	}
}
