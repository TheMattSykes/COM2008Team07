package Views;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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

import Models.GraduateType;
import Models.Module;

public class AddModule extends JPanel {
	private static final long serialVersionUID = 1L;
	PrimaryFrame frame;
	JPanel form;
	private JPanel formButtons;
	private JPanel localButtons;
	private JButton backButton;
	private JButton applyButton;
	private String[] availableDepartment;
	private JTextField codeTextField;
	private JTextField nameTextField;
	private JTextField credsTextField;
	private JComboBox<String> periodDropdown;
	private JComboBox<Integer> levelDropdown;
	private JComboBox<String> typeDropdown;
	private JComboBox<String> deptDropdown;
	private JComboBox<String> coreDropdown;
	
	public AddModule(PrimaryFrame pf) {
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
	
	public Module getNewModule() {
		Module module = new Module();
		String code = codeTextField.getText().trim();
        code = code.toUpperCase();
		module.setCode(code);
		String moduleName = nameTextField.getText().trim();
		if (moduleName.length() > 0)
			moduleName = moduleName.substring(0, 1).toUpperCase()+moduleName.substring(1);
		module.setName(moduleName);
		int credits = Integer.parseInt(credsTextField.getText());
		module.setCredits(credits);
		module.setTeachingPeriod((String)periodDropdown.getSelectedItem());
		module.setLevel((int)levelDropdown.getSelectedItem());
		module.setType((GraduateType)typeDropdown.getSelectedItem());
		module.setDepartment((String)deptDropdown.getSelectedItem());
		module.setCore((String)coreDropdown.getSelectedItem());
		return module;
	}
	
	public void loadUI() throws Exception {
		formButtons = new JPanel();
		formButtons.setLayout(new GridBagLayout());
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
		
		// Module code
		JPanel codePanel = new JPanel();
		JLabel codeLabel = new JLabel("Module Code: ");
		codeTextField = new JTextField();
		codeTextField.setPreferredSize(textFieldSize);
		formConstraints.gridx = 0;
		codePanel.add(codeLabel, formConstraints);
		formConstraints.gridx = 1;
		codePanel.add(codeTextField, formConstraints);
		formLeft.add(codePanel);
		
		// Module name
		JPanel namePanel = new JPanel();
		JLabel nameLabel = new JLabel("Module Name: ");
		nameTextField = new JTextField();
		nameTextField.setPreferredSize(textFieldSize);
		formConstraints.gridx = 0;
		namePanel.add(nameLabel, formConstraints);
		formConstraints.gridx = 1;
		namePanel.add(nameTextField, formConstraints);
		formLeft.add(namePanel);
		
		// Credits
		JPanel credsPanel = new JPanel();
		JLabel credsLabel = new JLabel("Credits: ");
		credsTextField = new JTextField();
		credsTextField.setPreferredSize(textFieldSize);
		formConstraints.gridx = 0;
		credsPanel.add(credsLabel, formConstraints);
		formConstraints.gridx = 1;
		credsPanel.add(credsTextField, formConstraints);
		formLeft.add(credsPanel);
		
		// Teaching period
		JPanel periodPanel = new JPanel();
		JLabel periodLabel = new JLabel("Teaching Period: ");
		periodDropdown = new JComboBox<String>(new String[] {"Autumn","Spring","Year"});
		formConstraints.gridx = 0;
		periodPanel.add(periodLabel, formConstraints);
		formConstraints.gridx = 1;
		periodPanel.add(periodDropdown, formConstraints);
		formLeft.add(periodPanel);
		
		// Level
		JPanel levelPanel = new JPanel();
		JLabel levelLabel = new JLabel("Level: ");
		levelDropdown = new JComboBox<Integer>(new Integer[] {1,2,3,4,5,6,7,8,9});
		formConstraints.gridx = 0;
		levelPanel.add(levelLabel, formConstraints);
		formConstraints.gridx = 1;
		levelPanel.add(levelDropdown, formConstraints);
		formRight.add(levelPanel);
		
		// Graduate Type
		JPanel typePanel = new JPanel();
		JLabel typeLabel = new JLabel("Graduate Type: ");
		typeDropdown = new JComboBox<String>(new String[] {"Undergraduate","Postgraduate"});
		formConstraints.gridx = 0;
		typePanel.add(typeLabel, formConstraints);
		formConstraints.gridx = 1;
		typePanel.add(typeDropdown, formConstraints);
		formRight.add(typePanel);
		
		// Department
		JPanel deptPanel = new JPanel();
		JLabel deptLabel = new JLabel("Department: ");
		deptDropdown = new JComboBox<String>(availableDepartment);
		formConstraints.gridx = 0;
		deptPanel.add(deptLabel, formConstraints);
		formConstraints.gridx = 1;
		deptPanel.add(deptDropdown, formConstraints);
		formRight.add(deptPanel);
		
		// isCore
		JPanel corePanel = new JPanel();
		JLabel coreLabel = new JLabel("Core: ");
		coreDropdown = new JComboBox<String>(new String[] {"yes","no"});
		formConstraints.gridx = 0;
		corePanel.add(coreLabel, formConstraints);
		formConstraints.gridx = 1;
		corePanel.add(coreDropdown, formConstraints);
		formRight.add(corePanel);
		
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
		frame.showMenuBar();
		frame.revalidate();
		frame.repaint();
	}
}