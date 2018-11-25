package Views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Window;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import Controllers.TableColumnAdjuster;
import Models.Classification;
import Models.Grades;
import Models.GraduateType;
import Models.Module;
import Models.Student;

public class StudentView extends JPanel {
	PrimaryFrame frame;
	Object[][] data;
	Classification classi;
	JPanel studentInfo;
	Student student;
	
	float[] yearAverages;
	
	
	public StudentView(PrimaryFrame pf) {
		frame = pf;
	}
	
	public PrimaryFrame getFrame() {
		return frame;
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
	
	
	
	public void viewChange() {
		frame.getContentPane().removeAll();
	}
	
	public void removeUI() {
		frame.remove(studentInfo);
	}
	
	public void loadUI() {
		String stuName = student.getTitle() + " " + student.getFirstName() + " " + student.getSecondName();
		int regNumber = student.getRegNumber();
		String degree = student.getDegree();
		int year = student.getLevel();
		String email = student.getEmail();
		String tutor = student.getTutor();
		
		JPanel studentDetails = new JPanel();
		studentDetails.setLayout(new GridLayout(5,1));
		
		JLabel nameLabel = new JLabel("Name: "+stuName);
		JLabel regLabel = new JLabel("Registration Number: "+regNumber);
		JLabel degLabel = new JLabel("Degree: "+degree);
		JLabel yearLabel = new JLabel("Year: "+year);
		JLabel emailLabel = new JLabel("Email: "+email);
		JLabel tutorLabel = new JLabel("Tutor: "+tutor);
		
		studentDetails.add(nameLabel);
		studentDetails.add(regLabel);
		studentDetails.add(degLabel);
		studentDetails.add(yearLabel);
		studentDetails.add(emailLabel);
		studentDetails.add(tutorLabel);
		
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

		studentInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
		
		
		
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
		studentDetails.setLayout(new GridLayout((1+(yearAverages.length)),1));
		
		for (int y = 0; y < student.getLevel(); y++) {
			JLabel yearResultsLabel = new JLabel("Year "+(y+1)+" Result: "+yearAverages[y]);
			studentResults.add(yearResultsLabel);
		}
		
		JLabel resultLabel = new JLabel("Overall Result: "+classi);
		studentResults.add(resultLabel);
		
		stuConstraints.insets = new Insets(5,5,5,5);
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
