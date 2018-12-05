/**
 * Degree Model
 */

package Models;

import java.util.ArrayList;

public class Degree {
	
	// Attributes
	private String code;
	private String name;
	private Integer level;
	private Boolean industry;
	private GraduateType type;
	// Would represent the data in the separate table
	private Department leadDept;
	private ArrayList<Department> assistingDepts;
	
	public Degree () {
		
	}
	
	public Degree (String c, String n) {
		code = c;
		name = n;
	}
	
	public Degree (String c, String n, Integer l, Boolean i, String gt, Department d) {
		code = c;
		name = n;
		level = l;
		industry = i;
		type = GraduateType.valueOf(gt.toUpperCase());
		leadDept = d;
	}
	
	// Polymorphic alternative constructor
	public Degree (String c, String n, Integer l, Boolean i, String gt, Department ld, ArrayList<Department> ad) {
		code = c;
		name = n;
		level = l;
		industry = i;
		type = GraduateType.valueOf(gt.toUpperCase());
		leadDept = ld;
		assistingDepts = ad;
	}
	
	
	// Set attribute functions
	public void setCode(String c) {
		code = c;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public String getName() {
		return name;
	}
	
	public void setLevel(Integer l) {
		level = l;
	}
	
	public Integer getLevel() {
		return level;
	}
	
	public void setIndustry(Boolean i) {
		industry = i;
	}
	
	public Boolean getIndustry() {
		return industry;
	}
	
	public void setType(GraduateType gt) {
		type = gt;
	}
	
	public GraduateType getType() {
		return type;
	}
	
	public void setLead(Department d) {
		leadDept = d;
	}
	
	public Department getLead() {
		return leadDept;
	}
	
	public void setAssist(ArrayList<Department> ad) {
		assistingDepts = ad;
	}
	
	public ArrayList<Department> getAssist() {
		return assistingDepts;
	}
}
