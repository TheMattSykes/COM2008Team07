package Views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Controllers.TableColumnAdjuster;

/**
 * The RegistrarView is the default view all users of type teacher see, once logged in.
 * It displays a table of all the students in the database and their details and has links to all other parts of the system,
 * accessible by all users of type teacher.
 * @author Amira Abraham
 */
public class TeacherView extends JPanel {
	private static final long serialVersionUID = 1L;
	PrimaryFrame frame;
	Object[][] studentsData;
	private JPanel teacherInfo;
	private JPanel teacherButtons;
	private JButton viewGrades;
	private JButton logoutButton;
	private JTable table;
	
	public TeacherView(PrimaryFrame pf) {
		frame = pf;
	}
	
	public PrimaryFrame getFrame() {
		return frame;
	}
	
	public JTable getTable() {
		return table;
	}
	
	public void setStudentsData(Object[][] d) {
		studentsData = d;
	}
	
	public void viewChange() {
		frame.getContentPane().removeAll();
		
		frame.showMenuBar();
		frame.revalidate();
		frame.repaint();
	}
	
	public void removeUI() {
		if (teacherInfo != null)
			frame.remove(teacherInfo);
		if (teacherButtons != null)
			frame.menuBar.remove(teacherButtons);
		
		frame.showMenuBar();
		frame.revalidate();
		frame.repaint();
	}
	
	public JButton getViewButton() {
		return viewGrades;
	}
	
	public JButton getLogoutButton() {
		return logoutButton;
	}
	
	/**
	 * loadUI()
	 * Loads the UI, with the table and all the necessary buttons, for the teacher to see.
	 * @throws Exception
	 */
	public void loadUI() throws Exception {
		String regName = "Teacher";
		
		JPanel registrarDetails = new JPanel();
		registrarDetails.setLayout(new GridLayout(5,1));
		
		JLabel nameLabel = new JLabel("Name: "+regName);
		
		registrarDetails.add(nameLabel);
		
		String[] columnNames = {
				"Reg. Number",
                "Title",
                "Surname",
                "Forename",
                "Degree",
                "Email",
                "Tutor",
                "Period",
                "Level",
                "Year Progression"
        };
		
		
		teacherInfo = new JPanel();
		
		teacherInfo.setLayout(new GridBagLayout());
		GridBagConstraints regConstraints = new GridBagConstraints();
		regConstraints.weightx = 1.0;

		// teacherInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
		
		
		
		regConstraints.insets = new Insets(5,5,5,5);
		
		regConstraints.fill = GridBagConstraints.HORIZONTAL;
		regConstraints.gridx = 0;
		regConstraints.gridy = 0;
		teacherInfo.add(registrarDetails, regConstraints);
		
		
		table = new JTable(studentsData, columnNames) {
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
		
		// Add, edit and delete student buttons
		teacherButtons = new JPanel();
		teacherButtons.setLayout(new GridBagLayout());
		
		viewGrades = new JButton("View Grades");
		viewGrades.setEnabled(false);
		
		GridBagConstraints menuConstraints = new GridBagConstraints();
		menuConstraints.insets = new Insets(0,5,0,5);		
		menuConstraints.fill = GridBagConstraints.HORIZONTAL;
		menuConstraints.gridy = 0;
		
		menuConstraints.gridx = 0;
		teacherButtons.add(viewGrades, menuConstraints);
		menuConstraints.gridx = 1;
		
		logoutButton = (JButton) frame.menuBar.getComponent(0);
		
		menuConstraints.gridx = 0;
		frame.menuBar.add(teacherButtons, menuConstraints);
		
		// Row selection listener
		table.getSelectionModel().addListSelectionListener(
			new ListSelectionListener() {
		        public void valueChanged(ListSelectionEvent event) {
		        	if (!viewGrades.isEnabled())
		        		viewGrades.setEnabled(true);
		        }
			}
	    );
		
		regConstraints.insets = new Insets(5,5,5,5);
		regConstraints.fill = GridBagConstraints.HORIZONTAL;
		regConstraints.gridx = 0;
		regConstraints.gridy = 1;
		
		scrollPane.setPreferredSize(new Dimension(getWidth(),350));
		teacherInfo.add(scrollPane, regConstraints);
		
		frame.add(teacherInfo, BorderLayout.CENTER);
		frame.showMenuBar();
		frame.revalidate();
		frame.repaint();
	}
}
