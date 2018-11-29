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
	
	public Module(String c, String n, int cred, String tp, GraduateType ty) {
		code = c;
		name = n;
		credits = cred;
		teachingPeriod = tp;
		type = ty;
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
	
	public Grades getMaxGrade() {
		Grades maxGrade;
		if (grades[0] == Grades.DISTINCTION || grades[0] == Grades.PASS || grades[0] == Grades.UNDEFINED) {
			maxGrade = grades[0];
		} else {
			maxGrade = grades[1];
		}
		return maxGrade;
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
		//return code + " - " + name + " (Grade: "+getMaxGrade()+ ")";
		return code;
	}
	
	public String getDepartment() {
		return department;
	}
	
	public void setDepartment(String inputDepartment) {
		department = inputDepartment;
	}
	
	public Boolean isCore() {
		if (core != null) {
			if (core.toLowerCase().equals("yes") || core.toLowerCase().equals("true")) {
				return true;
			} else if (core.toLowerCase().equals("no") || core.toLowerCase().equals("false")) {
				return false;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}
	
	public void setCore(String inputCore) {
		core = inputCore;
	}
	
}
