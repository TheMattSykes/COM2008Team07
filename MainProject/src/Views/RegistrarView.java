package Views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Controllers.TableColumnAdjuster;

public class RegistrarView extends JPanel {
	private static final long serialVersionUID = 1L;
	PrimaryFrame frame;
	Object[][] studentsData;
	JPanel studentInfo;
	private JPanel registrarButtons;
	private JButton addStudent;
	private JButton editStudent;
	private JButton modulesButton;
	
	public RegistrarView(PrimaryFrame pf) {
		frame = pf;
	}
	
	public PrimaryFrame getFrame() {
		return frame;
	}
	
	public void setStudentsData(Object[][] d) {
		studentsData = d;
	}
	
	public void viewChange() {
		frame.getContentPane().removeAll();
	}
	
	public void removeUI() {
		if (studentInfo != null)
			frame.remove(studentInfo);
		if (registrarButtons != null)
			frame.menuBar.remove(registrarButtons);
	}
	
	public JButton getAddButton() {
		return addStudent;
	}
	
	public JButton getEditButton() {
		return editStudent;
	}
	
	public JButton getModulesButton() {
		return modulesButton;
	}
	
	public void loadUI() throws Exception {
		String regName = "Freddie Mercury";
		
		JPanel studentDetails = new JPanel();
		studentDetails.setLayout(new GridLayout(5,1));
		
		JLabel nameLabel = new JLabel("Name: "+regName);
		
		studentDetails.add(nameLabel);
		
		String[] columnNames = {
				"Reg. Number",
                "Title",
                "First Name",
                "Second Name",
                "Degree",
                "Email",
                "Tutor",
                "Period",
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
		
		
		JTable table = new JTable(studentsData, columnNames) {
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
		registrarButtons = new JPanel();
		registrarButtons.setLayout(new GridBagLayout());
		
		addStudent = new JButton("Add Student");
		editStudent = new JButton("Edit Student");
		editStudent.setEnabled(false);
		modulesButton = new JButton("Add/Edit Modules");
		modulesButton.setEnabled(false);
		JButton deleteStudent = new JButton("Delete Student");
		deleteStudent.setEnabled(false);
		
		GridBagConstraints menuConstraints = new GridBagConstraints();
		menuConstraints.insets = new Insets(0,5,0,5);		
		menuConstraints.fill = GridBagConstraints.HORIZONTAL;
		menuConstraints.gridy = 0;
		
		menuConstraints.gridx = 0;
		registrarButtons.add(addStudent, menuConstraints);
		menuConstraints.gridx = 1;
		registrarButtons.add(editStudent, menuConstraints);
		menuConstraints.gridx = 2;
		registrarButtons.add(deleteStudent, menuConstraints);
		menuConstraints.gridx = 3;
		registrarButtons.add(modulesButton, menuConstraints);
		menuConstraints.gridx = 0;
		frame.menuBar.add(registrarButtons, menuConstraints);
		
		// Row selection listener
		table.getSelectionModel().addListSelectionListener(
			new ListSelectionListener() {
		        public void valueChanged(ListSelectionEvent event) {
		            //System.out.println(table.getValueAt(table.getSelectedRow(), 0).toString());
		        	if (!editStudent.isEnabled())
		        		editStudent.setEnabled(true);
		        	if (!deleteStudent.isEnabled())
		        		deleteStudent.setEnabled(true);
		        	if (!modulesButton.isEnabled())
		        		modulesButton.setEnabled(true);
		        }
			}
	    );
		
		stuConstraints.insets = new Insets(5,5,5,5);
		stuConstraints.fill = GridBagConstraints.HORIZONTAL;
		stuConstraints.gridx = 0;
		stuConstraints.gridy = 1;
		studentInfo.add(scrollPane, stuConstraints);
		
		frame.add(studentInfo, BorderLayout.CENTER);
		frame.showMenuBar();
		frame.revalidate();
		frame.repaint();
	}
}
