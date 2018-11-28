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

import Models.Enrolled;
import Models.GraduateType;
import Models.Module;

public class EditGrades extends JPanel {
	private static final long serialVersionUID = 1L;
	PrimaryFrame frame;
	private JPanel form;
	private JPanel formButtons;
	private JPanel localButtons;
	private JButton backButton;
	private JButton applyButton;
	private JComboBox<Integer> regNoDropdown;
	private JComboBox<String> codeDropdown;
	private JTextField res1TextField;
	private JTextField res2TextField;
	String[] availableModules;
	private Enrolled student;
	
	public EditGrades(PrimaryFrame pf) {
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
	
	public void setAvailableModules(String[] m) {
		availableModules = m;
	}
	
	public Enrolled getNewGrades() {
		student.setRegNo((int)regNoDropdown.getSelectedItem());
		student.setCode((String)codeDropdown.getSelectedItem());
		int res1 = Integer.parseInt(res1TextField.getText());
		student.setRes1(res1);
		int res2 = Integer.parseInt(res2TextField.getText());
		student.setRes2(res2);
		return student;
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
		
		// Registration number
		JPanel regNoPanel = new JPanel();
		JLabel regNoLabel = new JLabel("Reg No: ");
		JTextField regNoTextField = new JTextField();
		regNoTextField.setPreferredSize(textFieldSize);
		formConstraints.gridx = 0;
		regNoPanel.add(regNoLabel, formConstraints);
		formConstraints.gridx = 1;
		regNoPanel.add(regNoTextField, formConstraints);
		formLeft.add(regNoPanel);
		
		// Module code
		JPanel codePanel = new JPanel();
		JLabel codeLabel = new JLabel("Module code: ");
		JComboBox<String> codeDropdown = new JComboBox<String>(availableModules);
		formConstraints.gridx = 0;
		codePanel.add(codeLabel, formConstraints);
		formConstraints.gridx = 1;
		codePanel.add(codeDropdown, formConstraints);
		formLeft.add(codePanel);
		
		// Result 1
		JPanel res1Panel = new JPanel();
		JLabel res1Label = new JLabel("Result 1: ");
		JTextField res1TextField = new JTextField();
		res1TextField.setPreferredSize(textFieldSize);
		formConstraints.gridx = 0;
		res1Panel.add(res1Label, formConstraints);
		formConstraints.gridx = 1;
		res1Panel.add(res1TextField, formConstraints);
		formRight.add(res1Panel);
		
		// Result 2
		JPanel res2Panel = new JPanel();
		JLabel res2Label = new JLabel("Result 2: ");
		JTextField res2TextField = new JTextField();
		res2TextField.setPreferredSize(textFieldSize);
		formConstraints.gridx = 0;
		res2Panel.add(res2Label, formConstraints);
		formConstraints.gridx = 1;
		res2Panel.add(res2TextField, formConstraints);
		formRight.add(res2Panel);
		
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