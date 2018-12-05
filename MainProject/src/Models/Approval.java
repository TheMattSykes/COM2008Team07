package Models;

public class Approval {
	
	// Attributes
	private String degreeCode;
	private String moduleCode;
	private Integer level;
	private Boolean core;
	
	public Approval() {
		
	}
	
	// Constructor
	public Approval(String d, String m, Integer l, Boolean c) {
		degreeCode = d;
		moduleCode = m;
		level = l;
		core = c;
	}
	
	// Get & Set methods
	public void setDegreeCode(String d) {
		degreeCode = d;
	}
	
	public String getDegreeCode() {
		return degreeCode;
	}
	
	public void setModuleCode(String m) {
		moduleCode = m;
	}
	
	public String getModuleCode() {
		return moduleCode;
	}
	
	public void setLevel(Integer l) {
		level = l;
	}
	
	public Integer getLevel() {
		return level;
	}
	
	public void setCore(Boolean c) {
		core = c;
	}
	
	public Boolean getCore() {
		return core;
	}
}
