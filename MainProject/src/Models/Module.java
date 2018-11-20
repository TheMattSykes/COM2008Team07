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
	
	public Module() {}
	
	public Module(String c, String n, int cred, int[] sc, Grades[] gr, String tp, int l, GraduateType ty) {
		code = c;
		name = n;
		credits = cred;
		scores = sc;
		grades = gr;
		teachingPeriod = tp;
		level = l;
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
	
	public void setScores(Grades[] gradesVals) {
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
}
