package Views;

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

import Models.Student;

/**
 * The Progress view is for the teacher to update the year progression of a student,
 * which will cause a student to be registered for their next period of study, either
 * progressing to next level, repeating a level, or graduating or failing to progress. 
 * @author Amira Abraham
 */
public class Progress extends JPanel {
	private static final long serialVersionUID = 1L;
	PrimaryFrame frame;
	private JPanel form;
	private JPanel localButtons;
	private JButton backButton;
	private JButton logoutButton;
	private JButton applyButton;
	private JComboBox<String> progressDropdown;
	private Student student;
	
	public Progress(PrimaryFrame pf) {
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
	
	public void setStudent(Student stu) {
		student = stu;
	}
	
	/**
	 * getYearProgression()
	 * Gets the new details of the student (after the teacher has made all the changes on year progression).
	 * @return the student object, with all the required changes being made to it.
	 */
	public Student getYearProgression() {
		student.setProgress((String)progressDropdown.getSelectedItem());
		return student;
	}

	/**
	 * loadUI()
	 * Loads the UI, with all the fields, boxes and buttons needed for the teacher 
	 * to be able to select year progression of a student.
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
		
		// Module code
		JPanel progressPanel = new JPanel();
		JLabel progressLabel = new JLabel("Year Progression: ");
		progressDropdown = new JComboBox<String>(new String[] {"Progressing","Repeating","Graduating","Fail","null"});
		progressDropdown.setSelectedItem(student.getProgress());
		formConstraints.gridx = 0;
		progressPanel.add(progressLabel, formConstraints);
		formConstraints.gridx = 1;
		progressPanel.add(progressDropdown, formConstraints);
		formLeft.add(progressPanel); 
		
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