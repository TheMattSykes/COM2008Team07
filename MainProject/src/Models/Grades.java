/**
 * Enum for Grades
 */

package Models;

public enum Grades {
	FAIL("Fail"), PASS("Pass"), DISTINCTION("Distinction"), UNDEFINED("Undefined");
	
	private final String displayThis;

    private Grades(String value) {
        displayThis = value;
    }

    public String toString() {
        return displayThis;
    }
}
