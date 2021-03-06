package Views;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Models.Department;

public class EditDepartment extends JPanel {
	private static final long serialVersionUID = 1L;
	PrimaryFrame frame;
	JPanel form;
	private JPanel formButtons;
	private JPanel localButtons;
	private JButton backButton;
	private JButton applyButton;
	private JTextField nameTextField;
	private JTextField codeTextField;
	private Department department;
	private String[] availableDepartment;
	
	public EditDepartment(PrimaryFrame pf) {
		frame = pf;
	}
	
	public PrimaryFrame getFrame() {
		return frame;
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
	
	public JButton getBackButton() {
		return backButton;
	}
	
	public JButton getApplyButton() {
		return applyButton;
	}
	
	public void setAvailableDepartment(String[] d) {
		availableDepartment = d;
	}
	
	public void setDepartment(Department dept) {
		department = dept;
	}
	
	public Department getNewDepartment() {
		String departmentName = nameTextField.getText().trim();
		if (departmentName.length() > 0)
			departmentName = departmentName.substring(0, 1).toUpperCase()+departmentName.substring(1);
		department.setName(departmentName);
		String code = codeTextField.getText().trim();
		code = code.toUpperCase();
		department.setCode(code);
		return department;
	}
	
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
		
		// Remove UI, when logout is pressed
		JButton logout = (JButton) frame.menuBar.getComponent(0);
		logout.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						removeUI();
					}
				}
		);
		
		menuConstraints.gridx = 0;
		frame.menuBar.add(localButtons, menuConstraints);
		
		frame.add(form);
		frame.revalidate();
		frame.repaint();
	}
}