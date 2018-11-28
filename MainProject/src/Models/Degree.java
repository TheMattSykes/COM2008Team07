package Models;

public class Degree {
	private String code;
	private String name;
	private Boolean industry;
	private GraduateType type;
	// Would represent the data in the separate table
	private Department leadDept;
	private Department[] assistingDepts;
	
	public Degree () {
		
	}
	
	public Degree (String c, String n, Boolean i, String gt, Department d) {
		code = c;
		name = n;
		industry = i;
		type = GraduateType.valueOf(gt.toUpperCase());
		leadDept = d;
	}
	
	public Degree (String c, String n, Boolean i, String gt, Department ld, Department[] ad) {
		code = c;
		name = n;
		industry = i;
		type = GraduateType.valueOf(gt.toUpperCase());
		leadDept = ld;
		assistingDepts = ad;
	}
}
