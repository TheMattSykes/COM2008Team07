package Views;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Models.Enrolled;
import Models.Module;

/**
 * The EditGrades view is for the teacher to add or update grade1 or grade2 
 * for a module that the student is taking.
 * @author Amira Abraham
 */
public class EditGrades extends JPanel {
	private static final long serialVersionUID = 1L;
	PrimaryFrame frame;
	private JPanel form;
	private JPanel localButtons;
	private JButton backButton;
	private JButton logoutButton;
	private JButton applyButton;
	private JTextField grade1TextField;
	private JTextField grade2TextField;
	private Module selectedModule;
	private Module module;
	private Enrolled enrolled;
	
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
	
	public JButton getLogoutButton() {
		return logoutButton;
	}
	
	public void setSelectedModule(Module sm) {
		selectedModule = sm;
	}
	
	/**
	 * editGrades()
	 * Gets the new grades of a module which the student is taking (after the teacher
	 * has made all the changes they wanted).
	 * @return the module object, with all the required changes being made to it.
	 */
	public Module editGrades() {
		int grade1 = Integer.parseInt(grade1TextField.getText());
		int grade2 = Integer.parseInt(grade2TextField.getText());
		return module;
	}
	
	/**
	 * loadUI()
	 * Loads the UI, with all the fields, boxes and buttons needed for the teacher to be able to make grades changes to a module.
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
		
		//  Module code
		JPanel regPanel = new JPanel();
		JLabel regLabel = new JLabel("Module code: ");
		JTextField regTextField = new JTextField();
		regTextField.setText(selectedModule.getCode());
		regTextField.setEnabled(false);
		regTextField.setPreferredSize(textFieldSize);
		formConstraints.gridx = 0;
		formConstraints.gridy = 0;
		regPanel.add(regLabel, formConstraints);
		formConstraints.gridx = 1;
		regPanel.add(regTextField, formConstraints);
		formLeft.add(regPanel);
		
		// Result 1
		JPanel grade1Panel = new JPanel();
		JLabel grade1Label = new JLabel("Grade 1: ");
		JTextField grade1TextField = new JTextField();
		grade1TextField.setPreferredSize(textFieldSize);
		formConstraints.gridx = 0;
		grade1Panel.add(grade1Label, formConstraints);
		formConstraints.gridx = 1;
		grade1Panel.add(grade1TextField, formConstraints);
		formLeft.add(grade1Panel);
		
		// Result 2
		JPanel grade2Panel = new JPanel();
		JLabel grade2Label = new JLabel("Grade 2: ");
		JTextField grade2TextField = new JTextField();
		grade2TextField.setPreferredSize(textFieldSize);
		formConstraints.gridx = 0;
		grade2Panel.add(grade2Label, formConstraints);
		formConstraints.gridx = 1;
		grade2Panel.add(grade2TextField, formConstraints);
		formLeft.add(grade2Panel);
		
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