package Views;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Models.Student;

public class EditStudent extends JPanel {
	private static final long serialVersionUID = 1L;
	PrimaryFrame frame;
	private JPanel form;
	private JPanel localButtons;
	private JButton backButton;
	private JButton applyButton;
	private JButton logoutButton;
	private String[] availableDegrees;
	private JComboBox<String> titleDropdown;
	private JTextField forenameTextField;
	private JTextField surnameTextField;
	private JComboBox<String> degreeDropdown;
	private JTextField tutorTextField;
	private JComboBox<Character> periodDropdown;
	private JComboBox<Integer> levelDropdown;
	private JComboBox<String> registeredDropdown;
	private Student student;
	
	public EditStudent(PrimaryFrame pf) {
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
	
	public void setAvailableDegrees(String[] d) {
		availableDegrees = d;
	}
	
	public void setStudent(Student stu) {
		student = stu;
	}
	
	public Student getNewStudent() {
		student.setTitle((String)titleDropdown.getSelectedItem());
		String firstName = forenameTextField.getText().trim();
		if (firstName.length() > 0)
			firstName = firstName.substring(0, 1).toUpperCase()+firstName.substring(1);
		student.setFirstName(firstName);
		String secondName = surnameTextField.getText().trim();
		if (secondName.length() > 0)
			secondName = secondName.substring(0, 1).toUpperCase()+secondName.substring(1);
		student.setSecondName(secondName);
		student.setDegree((String)degreeDropdown.getSelectedItem());
		student.setTutor(tutorTextField.getText().trim());
		student.setPeriod((char)periodDropdown.getSelectedItem());
		student.setLevel((int)levelDropdown.getSelectedItem());
		student.setRegistered((String)registeredDropdown.getSelectedItem());
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
		
		// Reg. Number
		JPanel regPanel = new JPanel();
		JLabel regLabel = new JLabel("Reg. Number: ");
		JTextField regTextField = new JTextField();
		regTextField.setText(Integer.toString(student.getRegNumber()));
		regTextField.setEnabled(false);
		regTextField.setPreferredSize(textFieldSize);
		formConstraints.gridx = 0;
		formConstraints.gridy = 0;
		regPanel.add(regLabel, formConstraints);
		formConstraints.gridx = 1;
		regPanel.add(regTextField, formConstraints);
		formLeft.add(regPanel);
		
		// Title
		JPanel titlePanel = new JPanel();
		JLabel titleLabel = new JLabel("Title: ");
		titleDropdown = new JComboBox<String>(new String[] {"Mr","Ms"});
		titleDropdown.setSelectedItem(student.getTitle());
		formConstraints.gridx = 0;
		titlePanel.add(titleLabel, formConstraints);
		formConstraints.gridx = 1;
		titlePanel.add(titleDropdown, formConstraints);
		formRight.add(titlePanel);
		
		// Forename
		JPanel forenamePanel = new JPanel();
		JLabel forenameLabel = new JLabel("Forename: ");
		forenameTextField = new JTextField();
		forenameTextField.setText(student.getFirstName());
		forenameTextField.setPreferredSize(textFieldSize);
		formConstraints.gridx = 0;
		forenamePanel.add(forenameLabel, formConstraints);
		formConstraints.gridx = 1;
		forenamePanel.add(forenameTextField, formConstraints);
		formLeft.add(forenamePanel);
		
		// Surname
		JPanel surnamePanel = new JPanel();
		JLabel surnameLabel = new JLabel("Surname: ");
		surnameTextField = new JTextField();
		surnameTextField.setText(student.getSecondName());
		surnameTextField.setPreferredSize(textFieldSize);
		formConstraints.gridx = 0;
		surnamePanel.add(surnameLabel, formConstraints);
		formConstraints.gridx = 1;
		surnamePanel.add(surnameTextField, formConstraints);
		formRight.add(surnamePanel);
		
		// Degree
		JPanel degreePanel = new JPanel();
		JLabel degreeLabel = new JLabel("Degree: ");
		degreeDropdown = new JComboBox<String>(availableDegrees);
		degreeDropdown.setSelectedItem(student.getDegree());
		formConstraints.gridx = 0;
		degreePanel.add(degreeLabel, formConstraints);
		formConstraints.gridx = 1;
		degreePanel.add(degreeDropdown, formConstraints);
		formLeft.add(degreePanel);
		
		// Email
		JPanel emailPanel = new JPanel();
		JLabel emailLabel = new JLabel("Email: ");
		JTextField emailTextField = new JTextField();
		emailTextField.setText(student.getEmail());
		emailTextField.setEnabled(false);
		emailTextField.setPreferredSize(textFieldSize);
		formConstraints.gridx = 0;
		emailPanel.add(emailLabel, formConstraints);
		formConstraints.gridx = 1;
		emailPanel.add(emailTextField, formConstraints);
		formRight.add(emailPanel);
		
		// Tutor
		JPanel tutorPanel = new JPanel();
		JLabel tutorLabel = new JLabel("Tutor: ");
		tutorTextField = new JTextField();
		tutorTextField.setText(student.getTutor());
		tutorTextField.setPreferredSize(textFieldSize);
		formConstraints.gridx = 0;
		tutorPanel.add(tutorLabel, formConstraints);
		formConstraints.gridx = 1;
		tutorPanel.add(tutorTextField, formConstraints);
		formLeft.add(tutorPanel);
		
		// Period
		JPanel periodPanel = new JPanel();
		JLabel periodLabel = new JLabel("Period: ");
		periodDropdown =
				new JComboBox<Character>(new Character[] {'A','B','C','D','E','F','G','H','I','J','K'});
		periodDropdown.setSelectedItem(student.getPeriod());
		formConstraints.gridx = 0;
		periodPanel.add(periodLabel, formConstraints);
		formConstraints.gridx = 1;
		periodPanel.add(periodDropdown, formConstraints);
		formRight.add(periodPanel);
		
		// Level
		JPanel levelPanel = new JPanel();
		JLabel levelLabel = new JLabel("Level: ");
		levelDropdown = new JComboBox<Integer>(new Integer[] {1,2,3,4,5,6,7,8,9});
		levelDropdown.setSelectedItem(student.getLevel());
		formConstraints.gridx = 0;
		levelPanel.add(levelLabel, formConstraints);
		formConstraints.gridx = 1;
		levelPanel.add(levelDropdown, formConstraints);
		formLeft.add(levelPanel);
		
		// Registered
		JPanel registeredPanel = new JPanel();
		JLabel registeredLabel = new JLabel("Registered: ");
		registeredDropdown = new JComboBox<String>(new String[] {"Not Registered","Partially Registered","Fully Registered"});
		registeredDropdown.setSelectedItem(student.getRegistered());
		formConstraints.gridx = 0;
		registeredPanel.add(registeredLabel, formConstraints);
		formConstraints.gridx = 1;
		registeredPanel.add(registeredDropdown, formConstraints);
		formRight.add(registeredPanel);
		
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
		
		logoutButton = (JButton) frame.menuBar.getComponent(0);
		
		menuConstraints.gridx = 0;
		frame.menuBar.add(localButtons, menuConstraints);
		
		frame.add(form);
		frame.revalidate();
		frame.repaint();
	}
}