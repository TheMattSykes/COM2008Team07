package Views;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import Models.Degree;
import Models.Department;
import Models.GraduateType;
import Models.UserTypes;

public class AddDegree extends JPanel {
	private static final long serialVersionUID = 1L;
	PrimaryFrame frame;
	private JPanel degreeForm;
	private JPanel buttonPanel;
	private Object[][] departmentData;
	private String[] departmentNames;
	private JTextField degreeName;
	private JComboBox<Integer> levelDropdown;
	private JComboBox<String> leadDeptDropdown;
	private JComboBox<GraduateType> gradTypeDropdown;
	private JList<String> assistingDeptChoice;
	private JCheckBox industryChoice;
	private JButton apply;
	private JButton back;
	
	public AddDegree(PrimaryFrame pf) {
		frame = pf;
	}
	
	public PrimaryFrame getFrame() {
		return frame;
	}
	
	public void setDeptData(Object[][] d) {
		departmentData = d;
		departmentNames = new String[departmentData.length];
		for (int i = 0; i < departmentData.length; i++) {
			departmentNames[i] = (String)departmentData[i][1];
		}
	}
	
	public void viewChange() {
		frame.getContentPane().removeAll();
	}
	
	public void removeUI() {
		if (degreeForm != null)
			frame.remove(degreeForm);
		if (buttonPanel != null)
			frame.menuBar.remove(buttonPanel);
	}
	
	public JButton getBackButton() {
		return back;
	}
	
	public JButton getApplyButton() {
		return apply;
	}
	
	public Degree getNewDegree() {
		Degree degree = new Degree();
		degree.setName(degreeName.getText().trim());
		degree.setLevel((Integer)levelDropdown.getSelectedItem());
		degree.setIndustry(industryChoice.isSelected());
		degree.setType((GraduateType)gradTypeDropdown.getSelectedItem());
		String leadName = (String)leadDeptDropdown.getSelectedItem();
		degree.setLead(new Department(getCode(leadName) ,leadName));
		int[] ids = assistingDeptChoice.getSelectedIndices();
		ArrayList<Department> assisting = new ArrayList<Department>();
		for (int i = 0; i < ids.length; i++) {
			String deptName = (String)assistingDeptChoice.getModel().getElementAt(ids[i]);
			Department sel = new Department(getCode(deptName),deptName);
			assisting.add(sel);
		}
		degree.setAssist(assisting);
		return degree;
	}
	
	public String getCode(String n) {
		String code = "";
		for (Object[] department : departmentData) {
			if (((String)department[1]).equals(n)) {
				code = (String)department[0];
			}
		}
		return code;
	}
	
	public void loadUI() {
		degreeForm = new JPanel();
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints formConstraints = new GridBagConstraints();
		formConstraints.weightx = 1.0;
		formConstraints.insets = new Insets(20,20,20,20);
		formConstraints.fill = GridBagConstraints.HORIZONTAL;
		
		JPanel formLeft = new JPanel();
		JPanel formRight = new JPanel();
		BoxLayout twoColumns = new BoxLayout (degreeForm, BoxLayout.X_AXIS);
		BoxLayout leftColumn = new BoxLayout (formLeft, BoxLayout.Y_AXIS);
		BoxLayout rightColumn = new BoxLayout (formRight, BoxLayout.Y_AXIS);
	
		degreeForm.setLayout(twoColumns);
		formLeft.setLayout(leftColumn);
		formRight.setLayout(rightColumn);
		Dimension textFieldSize = new Dimension (300,20);
		
		// Degree name
		JPanel namePanel = new JPanel();
		JLabel nameLabel = new JLabel("Degree Name: ");
		degreeName = new JTextField();
		degreeName.setPreferredSize(textFieldSize);
		formConstraints.gridx = 0;
		namePanel.add(nameLabel, formConstraints);
		formConstraints.gridx = 1;
		namePanel.add(degreeName, formConstraints);
		formLeft.add(namePanel);
		
		// Graduate Type
		JPanel typePanel = new JPanel();
		JLabel typeLabel = new JLabel("Grad. Type: ");
		gradTypeDropdown = new JComboBox<GraduateType>(GraduateType.values());
		formConstraints.gridx = 0;
		typePanel.add(typeLabel,formConstraints);
		formConstraints.gridx = 1;
		typePanel.add(gradTypeDropdown, formConstraints);
		formLeft.add(typePanel);
		
		// Graduate Type
		JPanel levelPanel = new JPanel();
		JLabel levelLabel = new JLabel("Level: ");
		levelDropdown = new JComboBox<Integer>(new Integer[] {1,2,3,4});
		formConstraints.gridx = 0;
		levelPanel.add(levelLabel,formConstraints);
		formConstraints.gridx = 1;
		levelPanel.add(levelDropdown, formConstraints);
		formLeft.add(levelPanel);
		
		// Industry Choice
		JPanel checkPanel = new JPanel();
		industryChoice = new JCheckBox("Year in Industry?");
		formConstraints.gridx = 0;
		checkPanel.add(industryChoice, formConstraints);
		formRight.add(checkPanel);
		
		// Lead Departments
		JPanel leadPanel = new JPanel();
		JLabel leadLabel = new JLabel("Lead Department: ");
		leadDeptDropdown = new JComboBox<String>(departmentNames);
		formConstraints.gridx = 0;
		leadPanel.add(leadLabel, formConstraints);
		formConstraints.gridx = 1;
		leadPanel.add(leadDeptDropdown, formConstraints);
		formRight.add(leadPanel);
		
		// Assisting Departments
		JPanel assistPanel = new JPanel();
		JLabel assistLabel = new JLabel("Assisting Departments: ");
		assistingDeptChoice = new JList<String>(departmentNames);
		assistingDeptChoice.setVisibleRowCount(3);
		assistingDeptChoice.setSelectionModel(new DefaultListSelectionModel() {
			private static final long serialVersionUID = 1L;
		    public void setSelectionInterval(int i0, int i1) {
		        if(super.isSelectedIndex(i0)) {
		            super.removeSelectionInterval(i0, i1);
		        }
		        else {
		            super.addSelectionInterval(i0, i1);
		        }
		    }
		});
		JScrollPane assistingScroll = new JScrollPane(assistingDeptChoice);
		formConstraints.gridx = 0;
		assistPanel.add(assistLabel, formConstraints);
		formConstraints.gridx = 1;
		assistPanel.add(assistingScroll, formConstraints);
		formRight.add(assistPanel);
		
		degreeForm.add(formLeft);
		degreeForm.add(formRight);
		
		GridBagConstraints buttonConstraints = new GridBagConstraints();
		buttonConstraints.insets = new Insets(0,5,0,5);
		buttonConstraints.fill = GridBagConstraints.HORIZONTAL;
		buttonConstraints.gridy = 0;
		buttonConstraints.gridx = 0;
		back = new JButton("Back");
		apply = new JButton("Apply");
		buttonPanel.add(apply, buttonConstraints);
		buttonConstraints.gridx = 1;
		buttonPanel.add(back, buttonConstraints);
		
		buttonConstraints.gridx = 0;
		frame.menuBar.add(buttonPanel);
		frame.add(degreeForm);
		frame.showMenuBar();
		frame.revalidate();
		frame.repaint();
	}
}
