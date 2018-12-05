/**
 * Enrolled Model
 * 
 * Defines attributes and methods for student module codes and results.
 */

package Models;

public class Enrolled {
	private int regNo;
	private String code; //module code
	private int result1;
	private int result2;
	
	public Enrolled() {}
	
	public Enrolled(int rn, String c, int r1, int r2) {
		regNo = rn;
		code = c;
		result1 = r1;
		result2 = r2;
	}
	
	public int getRegNo() {
		return regNo;
	}
	
	public void setRegNo(int inputRegNo) {
		inputRegNo = regNo;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String inputCode) {
		code = inputCode;
	}
	
	public int getRes1() {
		return result1;
	}
	
	public void setRes1(int inputRes1) {
		inputRes1 = result1;
	}
	
	public int getRes2() {
		return result2;
	}
	
	public void setRes2(int inputRes2) {
		inputRes2 = result2;
	}
	
}
