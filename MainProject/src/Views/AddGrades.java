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

public class AddGrades extends JPanel {
	private static final long serialVersionUID = 1L;
	PrimaryFrame frame;
	JPanel form;
	private JPanel formButtons;
	private JPanel localButtons;
	private JButton backButton;
	String[] availableDegrees;
	
	public AddGrades(PrimaryFrame pf) {
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
	
	public void setAvailableDegrees(String[] d) {
		availableDegrees = d;
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
		
		// Reg. Number
		JPanel regPanel = new JPanel();
		JLabel regLabel = new JLabel("Reg. Number: ");
		JTextField regTextField = new JTextField();
		regTextField.setText("Reg. Numbers are automatically generated");
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
		JComboBox<String> titleDropdown = new JComboBox<String>(new String[] {"Mr","Ms"});
		formConstraints.gridx = 0;
		titlePanel.add(titleLabel, formConstraints);
		formConstraints.gridx = 1;
		titlePanel.add(titleDropdown, formConstraints);
		formRight.add(titlePanel);
		
		// Forename
		JPanel forenamePanel = new JPanel();
		JLabel forenameLabel = new JLabel("Forename: ");
		JTextField forenameTextField = new JTextField();
		forenameTextField.setPreferredSize(textFieldSize);
		formConstraints.gridx = 0;
		forenamePanel.add(forenameLabel, formConstraints);
		formConstraints.gridx = 1;
		forenamePanel.add(forenameTextField, formConstraints);
		formLeft.add(forenamePanel);
		
		// Surname
		JPanel surnamePanel = new JPanel();
		JLabel surnameLabel = new JLabel("Surname: ");
		JTextField surnameTextField = new JTextField();
		surnameTextField.setPreferredSize(textFieldSize);
		formConstraints.gridx = 0;
		surnamePanel.add(surnameLabel, formConstraints);
		formConstraints.gridx = 1;
		surnamePanel.add(surnameTextField, formConstraints);
		formRight.add(surnamePanel);
		
		// Degree
		JPanel degreePanel = new JPanel();
		JLabel degreeLabel = new JLabel("Degree: ");
		JComboBox<String> degreeDropdown = new JComboBox<String>(availableDegrees);
		formConstraints.gridx = 0;
		degreePanel.add(degreeLabel, formConstraints);
		formConstraints.gridx = 1;
		degreePanel.add(degreeDropdown, formConstraints);
		formLeft.add(degreePanel);
		
		// Email
		JPanel emailPanel = new JPanel();
		JLabel emailLabel = new JLabel("Email: ");
		JTextField emailTextField = new JTextField();
		emailTextField.setText("Emails are automatically generated");
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
		JTextField tutorTextField = new JTextField();
		tutorTextField.setPreferredSize(textFieldSize);
		formConstraints.gridx = 0;
		tutorPanel.add(tutorLabel, formConstraints);
		formConstraints.gridx = 1;
		tutorPanel.add(tutorTextField, formConstraints);
		formLeft.add(tutorPanel);
		
		// Period
		JPanel periodPanel = new JPanel();
		JLabel periodLabel = new JLabel("Period: ");
		JTextField periodTextField = new JTextField();
		periodTextField.setText("The initial period of study is 'A' by default");
		periodTextField.setEnabled(false);
		periodTextField.setPreferredSize(textFieldSize);
		formConstraints.gridx = 0;
		periodPanel.add(periodLabel, formConstraints);
		formConstraints.gridx = 1;
		periodPanel.add(periodTextField, formConstraints);
		formRight.add(periodPanel);
		
		// Level
		JPanel levelPanel = new JPanel();
		JLabel levelLabel = new JLabel("Period: ");
		JComboBox<String> levelDropdown = new JComboBox<String>(new String[] {"1","2","3","4"});
		formConstraints.gridx = 0;
		levelPanel.add(levelLabel, formConstraints);
		formConstraints.gridx = 1;
		levelPanel.add(levelDropdown, formConstraints);
		formLeft.add(levelPanel);
		
		form.add(formLeft);
		form.add(formRight);
		
		GridBagConstraints menuConstraints = new GridBagConstraints();
		menuConstraints.insets = new Insets(0,5,0,5);		
		menuConstraints.fill = GridBagConstraints.HORIZONTAL;
		menuConstraints.gridy = 0;
		menuConstraints.gridx = 0;
		localButtons = new JPanel();
		JButton applyButton = new JButton("Apply");
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