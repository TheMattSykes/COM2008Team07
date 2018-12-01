package Views;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Models.GraduateType;

public class AddDegree extends JPanel {
	private static final long serialVersionUID = 1L;
	PrimaryFrame frame;
	private JPanel form;
	private JPanel buttonPanel;
	private ArrayList<String> departmentNames;
	
	private JTextField degreeName;
	private JComboBox<String> leadDepartmentDropdown;
	private JComboBox<GraduateType> gradTypeDropdown;
	private JList<String> assistingDeptChoice;
	private JCheckBox industryChoice;
	private JButton apply;
	private JButton back;
	
	public AddDegree(PrimaryFrame pf) {
		frame = pf;
	}
	
	public void removeUI() {
		if (form != null)
			frame.remove(form);
		if (buttonPanel != null)
			frame.menuBar.remove(buttonPanel);
	}
}
