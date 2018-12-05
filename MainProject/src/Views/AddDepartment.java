/**
 * AddDepartment View
 * 
 * Defines form for adding a new department to the database.
 */

package Views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Models.Department;

public class AddDepartment extends JPanel {
	private static final long serialVersionUID = 1L;
	PrimaryFrame frame;
	JPanel form;
	private JPanel localButtons;
	private JButton backButton;
	private JButton applyButton;
	private JTextField nameTextField;
	private JTextField codeTextField;
	
	public AddDepartment(PrimaryFrame pf) {
		frame = pf;
	}
	
	public void viewChange() {
		frame.getContentPane().removeAll();
	}
	
	public void removeUI() {
		if (form != null)
			frame.remove(form);
		if (localButtons != null)
			frame.menuBar.remove(localButtons);
	}
	
	// Get/Set methods
	public PrimaryFrame getFrame() {
		return frame;
	}
	
	public JButton getBackButton() {
		return backButton;
	}
	
	public JButton getApplyButton() {
		return applyButton;
	}
	
	// Gets the new department, specified in the form by the user
	public Department getNewDepartment() {
		Department department = new Department();
		String departmentName = nameTextField.getText().trim();
		if (departmentName.length() > 0)
			departmentName = departmentName.substring(0, 1).toUpperCase()+departmentName.substring(1);
		department.setName(departmentName);
		String code = codeTextField.getText().trim();
		code = code.toUpperCase();
		department.setCode(code);
		return department;
	}
	
	/**
	 * loadUI
	 * Load and define the UI for adding a department form.
	 * @throws Exception
	 */
	public void loadUI() throws Exception {
		form = new JPanel();
		form.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		
		JPanel formLeft = new JPanel();
		JPanel formRight = new JPanel();
		
		GridBagConstraints formConstraints = new GridBagConstraints();
		formConstraints.weightx = 1.0;
		formConstraints.insets = new Insets(20,20,20,20);
		formConstraints.fill = GridBagConstraints.HORIZONTAL;
		
		BoxLayout twoColumns = new BoxLayout (form, BoxLayout.X_AXIS);
		BoxLayout leftColumn = new BoxLayout (formLeft, BoxLayout.Y_AXIS);
		BoxLayout rightColumn = new BoxLayout (formRight, BoxLayout.Y_AXIS);
		
		form.setLayout(twoColumns);
		formLeft.setLayout(leftColumn);
		formRight.setLayout(rightColumn);
		
		Dimension textFieldSize = new Dimension (300, 20);
		
		// Department name
		JPanel namePanel = new JPanel();
		JLabel nameLabel = new JLabel("Department name: ");
		nameTextField = new JTextField();
		nameTextField.setPreferredSize(textFieldSize);
		formConstraints.gridx = 0;
		namePanel.add(nameLabel, formConstraints);
		formConstraints.gridx = 1;
		namePanel.add(nameTextField, formConstraints);
		formLeft.add(namePanel);
		
		// Department code
		JPanel codePanel = new JPanel();
		JLabel codeLabel = new JLabel("Department code: ");
		codeTextField = new JTextField();
		codeTextField.setPreferredSize(textFieldSize);
		formConstraints.gridx = 0;
		codePanel.add(codeLabel, formConstraints);
		formConstraints.gridx = 1;
		codePanel.add(codeTextField, formConstraints);
		formRight.add(codePanel);
		
		form.add(formLeft);
		form.add(formRight);
		
		GridBagConstraints menuConstraints = new GridBagConstraints();
		menuConstraints.insets = new Insets(0,5,0,5);		
		menuConstraints.fill = GridBagConstraints.HORIZONTAL;
		menuConstraints.gridy = 0;
		menuConstraints.gridx = 0;
		localButtons = new JPanel();
		applyButton = new JButton("Apply");
		localButtons.add(applyButton, menuConstraints);
		backButton = new JButton("Back");
		menuConstraints.gridx = 1;
		localButtons.add(backButton, menuConstraints);
		
		menuConstraints.gridx = 0;
		frame.menuBar.add(localButtons, menuConstraints);
		
		frame.add(form, BorderLayout.CENTER);
		frame.showMenuBar();
		frame.revalidate();
		frame.repaint();
	}
}