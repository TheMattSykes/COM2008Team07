package Views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import Controllers.TableColumnAdjuster;
import Models.Module;
import Models.Student;

public class RegistrarModules extends JPanel {
	private static final long serialVersionUID = 1L;
	
	PrimaryFrame frame;
	Student student;
	private JPanel mainPanel;
	private JTable currentModulesTable;
	private JTable availableModulesTable;
	private DefaultTableModel currentModulesTableModel;
	private DefaultTableModel availableModulesTableModel;
	private JLabel totalCreditsLabel2 = new JLabel();
	private	JButton addModuleButton;
	private JButton removeModuleButton;
	private JPanel localButtons;
	private JButton backButton;
	private JButton applyButton = new JButton ("Apply");
	private JButton logoutButton;
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
	
	public JTable getCurrentModulesTable() {
		return currentModulesTable;
	}
	
	public JTable getAvailableModulesTable() {
		return availableModulesTable;
	}
	
	public DefaultTableModel getCurrentModulesTableModel() {
		return currentModulesTableModel;
	}
	
	public DefaultTableModel getAvailableModulesTableModel() {
		return availableModulesTableModel;
	}
	
	public JLabel getCreditsLabel() {
		return totalCreditsLabel2;
	}
	
	public JButton getRemoveModuleButton() {
		return removeModuleButton;
	}
	
	public JButton getAddModuleButton() {
		return addModuleButton;
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
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));
		
		GridBagConstraints formConstraints = new GridBagConstraints();
		formConstraints.weightx = 1.0;
		formConstraints.fill = GridBagConstraints.HORIZONTAL;
		
		mainPanel.setLayout(new BorderLayout(20,50));
		
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridBagLayout());
		
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
		
		northPanel.add(studentDetails, formConstraints);
		mainPanel.add(northPanel, BorderLayout.NORTH);
		
		// Table of currently enrolled modules
		String[] currentModulesColumnNames = {
				"Module Code",
                "Module Name",
                "Grade achieved",
                "Credits",
                "Level",
                "Core"
        };
		
		Object[][] currentModulesData = new Object[currentModules.size()][6];
		for (int i = 0; i < currentModules.size(); i++) {
			currentModulesData[i][0] = currentModules.get(i).getCode();
			currentModulesData[i][1] = currentModules.get(i).getName();
			currentModulesData[i][2] = currentModules.get(i).getMaxGrade();
			currentModulesData[i][3] = currentModules.get(i).getCredits();
			currentModulesData[i][4] = currentModules.get(i).getLevel();
			currentModulesData[i][5] = currentModules.get(i).isCore();
		}
		
		currentModulesTableModel = new DefaultTableModel(currentModulesData, currentModulesColumnNames);		
		currentModulesTable = new JTable(currentModulesTableModel) {
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
	    TableColumnAdjuster tca = new TableColumnAdjuster(currentModulesTable);
	    tca.adjustColumns();
	    
	    currentModulesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    
	    JScrollPane currentModulesScrollPane = new JScrollPane(currentModulesTable);
	    currentModulesTable.setFillsViewportHeight(true);
	    
	    JPanel twoColumns = new JPanel(new GridBagLayout());
	    GridBagConstraints twoColumnsConst = new GridBagConstraints();
	    twoColumnsConst.weightx = 1.0;
	    twoColumnsConst.fill = GridBagConstraints.HORIZONTAL;
	    twoColumnsConst.gridx = 0;
	    twoColumnsConst.gridy = 0;
		
		JLabel currentModulesTableTitle = new JLabel("Current Modules:");
		twoColumns.add(currentModulesTableTitle, twoColumnsConst);
		
		twoColumnsConst.gridx = 1;
		JLabel availableModulesTableTitle = new JLabel("Available Modules:");
		twoColumns.add(availableModulesTableTitle, twoColumnsConst);
		
		twoColumnsConst.gridx = 0;
	    twoColumnsConst.gridy = 1;
		currentModulesScrollPane.setPreferredSize(new Dimension(getWidth()/2,300));
		twoColumns.add(currentModulesScrollPane, twoColumnsConst);
		
		// Table of available (optional) modules
		String[] availableModulesColumnNames = {
				"Module Code",
                "Module Name",
                "Credits",
                "Level",
                "Core"
        };
		
		Object[][] availableModulesData = new Object[availableModules.size()][5];
		for (int i = 0; i < availableModules.size(); i++) {
			availableModulesData[i][0] = availableModules.get(i).getCode();
			availableModulesData[i][1] = availableModules.get(i).getName();
			availableModulesData[i][2] = availableModules.get(i).getCredits();
			availableModulesData[i][3] = availableModules.get(i).getLevel();
			availableModulesData[i][4] = availableModules.get(i).isCore();
		}
		
		availableModulesTableModel = new DefaultTableModel(availableModulesData, availableModulesColumnNames);		
		availableModulesTable = new JTable(availableModulesTableModel) {
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
	    TableColumnAdjuster tcaAvailable = new TableColumnAdjuster(availableModulesTable);
	    tcaAvailable.adjustColumns();
	    
	    availableModulesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    
	    JScrollPane availableModulesScrollPane = new JScrollPane(availableModulesTable);
	    availableModulesTable.setFillsViewportHeight(true);
	    
	    twoColumnsConst.gridx = 1;
	    availableModulesScrollPane.setPreferredSize(new Dimension(getWidth()/2,300));
		twoColumns.add(availableModulesScrollPane, twoColumnsConst);
		
		// Total number of credits for this year
		JPanel totalCreditsPanel = new JPanel();
		JLabel totalCreditsLabel1 = new JLabel("Total number of credits for level "+student.getLevel()+": ");
		twoColumnsConst.gridx = 0;
		twoColumnsConst.gridy = 0;
		totalCreditsPanel.add(totalCreditsLabel1);
		twoColumnsConst.gridx = 1;
		totalCreditsPanel.add(totalCreditsLabel2);
		twoColumnsConst.gridx = 0;
		twoColumnsConst.gridy = 2;
		twoColumns.add(totalCreditsPanel, twoColumnsConst);
		
		// Remove Module Button
		removeModuleButton = new JButton("Remove module");
		removeModuleButton.setEnabled(false);
		twoColumnsConst.gridy = 3;
		twoColumns.add(removeModuleButton, twoColumnsConst);
		
		// Add Module Button
		addModuleButton = new JButton("Add module");
		addModuleButton.setEnabled(false);
		twoColumnsConst.gridx = 1;
		twoColumnsConst.gridy = 3;
		twoColumns.add(addModuleButton, twoColumnsConst);
		
		mainPanel.add(twoColumns, BorderLayout.CENTER);
		
		GridBagConstraints menuConstraints = new GridBagConstraints();
		menuConstraints.insets = new Insets(0,5,0,5);		
		menuConstraints.fill = GridBagConstraints.HORIZONTAL;
		menuConstraints.gridy = 0;
		menuConstraints.gridx = 0;
		
		localButtons = new JPanel();
		localButtons.add(applyButton, menuConstraints);
		backButton = new JButton("Back");
		menuConstraints.gridx = 1;
		localButtons.add(backButton, menuConstraints);
		
		logoutButton = (JButton) frame.menuBar.getComponent(0);
		
		menuConstraints.gridx = 0;
		frame.menuBar.add(localButtons, menuConstraints);
		
		frame.add(mainPanel);
		frame.revalidate();
		frame.repaint();
	}
}