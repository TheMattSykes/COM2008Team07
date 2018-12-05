/**
 * StudentViews
 * 
 * Defines the UI for the student when logged in.
 * Consists of: their information, their modules/results and their year averages/classifications.
 */

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
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Controllers.TableColumnAdjuster;
import Models.Classification;
import Models.Student;

/**
 * StudentView
 * Defines the default view for all students, which they see after logging in
 */
public class StudentView extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private PrimaryFrame frame;
	private Object[][] data;
	private Classification classi;
	private JPanel studentInfo;
	private Student student;
	
	float[] yearAverages;
	
	
	public StudentView(PrimaryFrame pf) {
		frame = pf;
	}
	
	public void viewChange() {
		frame.getContentPane().removeAll();
	}
	
	public void removeUI() {
		frame.remove(studentInfo);
	}
	
	// Get/Set methods
	public PrimaryFrame getFrame() {
		return frame;
	}
	
	
	// Allow exterior classes to set data
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
	
	/**
	 * loadUI
	 * Define the UI elements and their content.
	 */
	public void loadUI() {
		
		// Retrieve student information
		String stuName = student.getTitle() + " " + student.getFirstName() + " " + student.getSecondName();
		int regNumber = student.getRegNumber();
		String degree = student.getDegree();
		int year = student.getLevel();
		String email = student.getEmail();
		String tutor = student.getTutor();
		String registration = student.getRegistered();
		String progress = student.getProgress();
		
		JPanel studentDetails = new JPanel();
		studentDetails.setLayout(new GridLayout(4,2));
		
		// Student information
		JLabel nameLabel = new JLabel("Name: "+stuName);
		JLabel regLabel = new JLabel("Registration Number: "+regNumber);
		JLabel degLabel = new JLabel("Degree: "+degree);
		JLabel yearLabel = new JLabel("Year: "+year);
		JLabel emailLabel = new JLabel("Email: "+email);
		JLabel tutorLabel = new JLabel("Tutor: "+tutor);
		JLabel statusLabel = new JLabel("Status: "+registration);
		JLabel progressLabel = new JLabel("Year Progress: "+progress);
		
		studentDetails.add(nameLabel);
		studentDetails.add(regLabel);
		studentDetails.add(degLabel);
		studentDetails.add(yearLabel);
		studentDetails.add(emailLabel);
		studentDetails.add(tutorLabel);
		studentDetails.add(statusLabel);
		studentDetails.add(progressLabel);
		
		
		// Define column names for table
		String[] columnNames = {
				"Code",
                "Name",
                "Credits",
                "Result 1",
                "Grade 1",
                "Result 2",
                "Grade 2",
                "Level"
        };
		
		
		studentInfo = new JPanel();
		
		studentInfo.setLayout(new GridBagLayout());
		GridBagConstraints stuConstraints = new GridBagConstraints();
		stuConstraints.weightx = 1.0;
		
		
		
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
		
		scrollPane.setPreferredSize(new Dimension(getWidth(),350));
		
		stuConstraints.insets = new Insets(5,5,5,5);
		stuConstraints.fill = GridBagConstraints.HORIZONTAL;
		stuConstraints.gridx = 0;
		stuConstraints.gridy = 1;
		studentInfo.add(scrollPane, stuConstraints);
		
		
		
		
		JPanel studentResults = new JPanel();
		
		studentResults.setLayout(new GridBagLayout());
		
		GridBagConstraints resConstraints = new GridBagConstraints();
		
		// Round values
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
		
		// Display overall result
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
		
		
		
		frame.add(studentInfo, BorderLayout.CENTER);
		frame.showMenuBar();
		frame.revalidate();
		frame.repaint();
	}
}
