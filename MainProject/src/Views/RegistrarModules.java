package Views;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Models.Module;
import Models.Student;

public class RegistrarModules extends JPanel {
	private static final long serialVersionUID = 1L;
	
	PrimaryFrame frame;
	Student student;
	private JPanel mainPanel;
	private JPanel localButtons;
	private JButton backButton;
	private JButton applyButton;
	private ArrayList<Module> currentModules;
	private ArrayList<Module> availableModules;
	
	
	public RegistrarModules(PrimaryFrame pf) {
		frame = pf;
	}
	
	public PrimaryFrame getFrame() {
		return frame;
	}
	
	public void viewChange() {
		frame.getContentPane().removeAll();
	}
	
	public void removeUI() {
		if (mainPanel != null)
			frame.remove(mainPanel);
		if (localButtons != null)
			frame.menuBar.remove(localButtons);
	}
	
	public JButton getBackButton() {
		return backButton;
	}
	
	public JButton getApplyButton() {
		return applyButton;
	}
	
	public void setStudent(Student stu) {
		student = stu;
	}
	
	public void setCurrentModules(ArrayList<Module> mod) {
		currentModules = mod;
	}
	
	public void setAvailableModules(ArrayList<Module> mod) {
		availableModules = mod;
	}
	
	public ArrayList<Module> getNewModules() {
		return currentModules;
	}
	
	public void loadUI() throws Exception {
		mainPanel = new JPanel();
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
		
		GridBagConstraints formConstraints = new GridBagConstraints();
		formConstraints.weightx = 1.0;
		formConstraints.insets = new Insets(20,20,20,20);
		formConstraints.fill = GridBagConstraints.HORIZONTAL;
		
		mainPanel.setLayout(new BorderLayout(20,50));
		
		// Student details
		JPanel studentDetails = new JPanel();
		studentDetails.setLayout(new GridLayout(5,1));
		
		JLabel nameLabel = new JLabel("Student Name: "+student.getFirstName()+" "+student.getSecondName());
		JLabel regLabel = new JLabel("Registration Number: "+student.getRegNumber());
		JLabel degLabel = new JLabel("Degree: "+student.getDegree());
		JLabel yearLabel = new JLabel("Year: "+student.getLevel());
		JLabel emailLabel = new JLabel("Email: "+student.getEmail());
		JLabel tutorLabel = new JLabel("Tutor: "+student.getTutor());
		
		studentDetails.add(nameLabel);
		studentDetails.add(regLabel);
		studentDetails.add(degLabel);
		studentDetails.add(yearLabel);
		studentDetails.add(emailLabel);
		studentDetails.add(tutorLabel);
		
		mainPanel.add(studentDetails, BorderLayout.NORTH);
		
		// Remove Module
		JPanel removeModulePanel = new JPanel();
		JLabel removeModuleLabel = new JLabel("Pick module to remove: ");
		JComboBox<Module> removeModuleDropdown = new JComboBox<Module>();
		removeModuleDropdown.setModel(new DefaultComboBoxModel(currentModules.toArray()));
		formConstraints.gridx = 0;
		removeModulePanel.add(removeModuleLabel, formConstraints);
		formConstraints.gridx = 1;
		removeModulePanel.add(removeModuleDropdown, formConstraints);
		formConstraints.gridx = 0;
		mainPanel.add(removeModulePanel);
		
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
		
		frame.add(mainPanel);
		frame.revalidate();
		frame.repaint();
	}
}