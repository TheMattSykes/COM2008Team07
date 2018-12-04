package Views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Controllers.TableColumnAdjuster;
import Models.Classification;
import Models.Student;

/**
 * The ViewGrades view is for the teacher to view all details about the enrolled modules by a student
 * (including module name, code, credits, results, grades, level and period). 
 * @author Amira Abraham
 */

public class ViewGrades extends JPanel {
	private static final long serialVersionUID = 1L;
	PrimaryFrame frame;
	Object[][] data;
	Classification classi;
	JPanel studentInfo;
	Student student;
	private JPanel teacherButtons;
	private JButton yearProg;
	private JButton editGrades;
	private JButton backButton;
	private JTable table;
	
	float[] yearAverages;
	
	
	public ViewGrades(PrimaryFrame pf) {
		frame = pf;
	}
	
	public PrimaryFrame getFrame() {
		return frame;
	}
	
	public JTable getTable() {
		return table;
	}
	
	
	public void setData(Object[][] d) {
		data = d;
	}
	
	public void setClassification(Classification c) {
		classi = c;
	}
	
	public void setStudent(Student s) {
		student = s;
	}
	
	public void setYearAverages(float[] av) {
		yearAverages = av;
	}
	
	public JButton getProgButton() {
		return yearProg;
	}
	
	public JButton getEditButton() {
		return editGrades;
	}
	
	public JButton getBackButton() {
		return backButton;
	}
	
	public void viewChange() {
		frame.getContentPane().removeAll();
	}
	
	public void removeUI() {
		frame.remove(studentInfo);
		frame.menuBar.remove(teacherButtons);
		if (studentInfo != null)
			frame.remove(studentInfo);
		if (teacherButtons != null)
			frame.remove(teacherButtons);
	}
	
	/**
	 * loadUI()
	 * Loads the UI, with the table and all the necessary buttons, for the teacher to see.
	 * @throws Exception
	 */
	public void loadUI() {
		String stuName = student.getTitle() + " " + student.getFirstName() + " " + student.getSecondName();
		int regNumber = student.getRegNumber();
		String degree = student.getDegree();
		int year = student.getLevel();
		String email = student.getEmail();
		String tutor = student.getTutor();
		String progress = student.getProgress();
		
		JPanel studentDetails = new JPanel();
		studentDetails.setLayout(new GridLayout(5,1));
		
		JLabel nameLabel = new JLabel("Name: "+stuName);
		JLabel regLabel = new JLabel("Registration Number: "+regNumber);
		JLabel degLabel = new JLabel("Degree: "+degree);
		JLabel yearLabel = new JLabel("Year: "+year);
		JLabel emailLabel = new JLabel("Email: "+email);
		JLabel tutorLabel = new JLabel("Tutor: "+tutor);
		JLabel progressLabel = new JLabel("Year Progress: "+progress);
		
		studentDetails.add(nameLabel);
		studentDetails.add(regLabel);
		studentDetails.add(degLabel);
		studentDetails.add(yearLabel);
		studentDetails.add(emailLabel);
		studentDetails.add(tutorLabel);
		studentDetails.add(progressLabel);
		
		String[] columnNames = {
				"Code",
                "Name",
                "Credits",
                "Result 1",
                "Grade 1",
                "Result 2",
                "Grade 2",
                "Level",
                "Period",
        };
		
		
		studentInfo = new JPanel();
		
		studentInfo.setLayout(new GridBagLayout());
		GridBagConstraints stuConstraints = new GridBagConstraints();
		stuConstraints.weightx = 1.0;

		// studentInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));

		stuConstraints.insets = new Insets(5,5,5,5);
		
		stuConstraints.fill = GridBagConstraints.HORIZONTAL;
		stuConstraints.gridx = 0;
		stuConstraints.gridy = 0;
		studentInfo.add(studentDetails, stuConstraints);
		
		JTable table = new JTable(data, columnNames) {
	        private static final long serialVersionUID = 1L;

	        public boolean isCellEditable(int row, int column) {                
	        	return false;               
	        }
	        
	        @Override
	        public Dimension getPreferredScrollableViewportSize() {
	            Dimension dim = new Dimension(
	            		// Width will get changed later anyway
	                this.getColumnCount() * 100,
	                // Set height of table, so it fits on the page
	                this.getRowHeight() * 20);
	            return dim;
	        }
	    };
	    
	    // Adjust column widths, based on the data they contain (see java file for source)
	    TableColumnAdjuster tca = new TableColumnAdjuster(table);
	    tca.adjustColumns();
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		scrollPane.setPreferredSize(new Dimension(getWidth(),325));
		
		stuConstraints.insets = new Insets(5,5,5,5);
		stuConstraints.fill = GridBagConstraints.HORIZONTAL;
		stuConstraints.gridx = 0;
		stuConstraints.gridy = 1;
		studentInfo.add(scrollPane, stuConstraints);
		
		JPanel studentResults = new JPanel();
		// studentDetails.setLayout(new GridLayout((1+(yearAverages.length)),1));
		
		studentResults.setLayout(new GridBagLayout());
		
		GridBagConstraints resConstraints = new GridBagConstraints();
		
		DecimalFormat df = new DecimalFormat("#.00");
		
		for (int y = 0; y < student.getLevel(); y++) {
			String formattedAverage = df.format(yearAverages[y]);
			
			JLabel yearResultsLabel = new JLabel("Year "+(y+1)+" Average: "+formattedAverage);
			
			resConstraints.insets = new Insets(10,10,10,10);
			resConstraints.fill = GridBagConstraints.HORIZONTAL;
			resConstraints.gridx = y;
			resConstraints.gridy = 0;
			
			studentResults.add(yearResultsLabel, resConstraints);
		}
		
		JLabel resultLabel = new JLabel("Overall Result: "+classi);
		resConstraints.insets = new Insets(10,10,10,10);
		resConstraints.fill = GridBagConstraints.HORIZONTAL;
		resConstraints.gridx = 0;
		resConstraints.gridy = 1;
		studentResults.add(resultLabel, resConstraints);
		
		stuConstraints.insets = new Insets(5,15,5,15);
		stuConstraints.fill = GridBagConstraints.HORIZONTAL;
		stuConstraints.gridx = 0;
		stuConstraints.gridy = 2;
		studentInfo.add(studentResults, stuConstraints);
		
		// edit grades,year progress and back buttons
		teacherButtons = new JPanel();
		teacherButtons.setLayout(new GridBagLayout());
				
		editGrades = new JButton("Edit Grades");
		editGrades.setEnabled(false);
		yearProg = new JButton("Year Progression");
		backButton = new JButton("Back");
				
		GridBagConstraints menuConstraints = new GridBagConstraints();
		menuConstraints.insets = new Insets(0,5,0,5);		
		menuConstraints.fill = GridBagConstraints.HORIZONTAL;
		menuConstraints.gridy = 0;
				
		menuConstraints.gridx = 0;
		teacherButtons.add(editGrades, menuConstraints);
		menuConstraints.gridx = 1;
		teacherButtons.add(yearProg, menuConstraints);
		menuConstraints.gridx = 2;
		teacherButtons.add(backButton, menuConstraints);
		menuConstraints.gridx = 3;
		
		menuConstraints.gridx = 0;
		frame.menuBar.add(teacherButtons, menuConstraints);
				
		// Row selection listener
		table.getSelectionModel().addListSelectionListener(
			new ListSelectionListener() {
				        public void valueChanged(ListSelectionEvent event) {
				        	if (!editGrades.isEnabled())
				        		editGrades.setEnabled(true);
				        }
					}
			    );
		
		frame.add(studentInfo, BorderLayout.CENTER);
		frame.showMenuBar();
		frame.revalidate();
		frame.repaint();
	}
}
