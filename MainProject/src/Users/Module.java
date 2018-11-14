package Users;

public class Module {
	private String code;
	private String name;
	private int credits;
	private int[] scores;
	private Grades[] grades;
	private String teachingPeriod;
	private int level;
	private GraduateType type;
	
	public Module() {
		
	}
	
	public String getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
	
	public int getCredits() {
		return credits;
	}
	
	public int[] getScores() {
		return scores;
	}
	
	public Grades[] getGrades() {
		return grades;
	}
	
	public String getTeachingPeriod() {
		return teachingPeriod;
	}
	
	public int getLevel() {
		return level;
	}
	
	public GraduateType getType() {
		return type;
	}
}
