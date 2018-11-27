package Models;

public class Department {
	private String name;
	private String code;
	
	public Department() {}
	
	public Department(String c, String n) {	
		name = n;
		code = c;
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
	
}
