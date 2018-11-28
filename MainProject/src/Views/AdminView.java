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

import Controllers.TableColumnAdjuster;

public class AdminView extends JPanel {
	private static final long serialVersionUID = 1L;

	PrimaryFrame frame;
	
	Object[][] accountsData;
	Object[][] departmentsData;
	Object[][] degreesData;
	Object[][] modulesData;
	
	JPanel adminMenu;
	JButton menuAccount;
	JButton menuDepartment;
	JButton menuDegree;
	JButton menuModule;
	JButton back;
		
	JPanel accountPanel;
	JPanel accountButtons;
	JButton accountAdd;
	JButton accountDelete;
	JTable accountTable;
	
	JPanel departmentPanel;
	JPanel departmentButtons;
	JButton departmentAdd;
	JButton departmentDelete;
	JTable departmentTable;
	
	JPanel degreePanel;
	JPanel degreeButtons;
	JButton degreeAdd;
	JButton degreeDelete;
	JTable degreeTable;
	
	JPanel modulePanel;
	JPanel moduleButtons;
	JButton moduleAdd;
	JButton moduleDelete;
	JTable moduleTable;
	
	public AdminView(PrimaryFrame pf) {
		frame = pf;
	}
	
	public void setDataAccounts(Object[][] a) {
		accountsData = a;
	}
	
	public Object[][] getDataAccounts() {
		return accountsData;
	}
	
	public void setDataDepartments(Object[][] d) {
		departmentsData = d;
	}
	
	public Object[][] getDataDepartments() {
		return departmentsData;
	}
	
	public void setDataDegrees(Object[][] d) {
		degreesData = d;
	}
	
	public Object[][] getDataDegrees() {
		return degreesData;
	}
	
	public void setDataModules(Object[][] m) {
		modulesData = m;
	}
	
	public Object[][] getDataModules() {
		return modulesData;
	}
	
	public PrimaryFrame getFrame() { 
		return frame;
	}
	
	// Menu buttons
	public JButton getAccountButton() {
		return menuAccount;
	}
	
	public JButton getDeptartmentButton() {
		return menuDepartment;
	}
	
	public JButton getDegreeButton() {
		return menuDegree;
	}
	
	public JButton getModuleButton() {
		return menuModule;
	}

	public JButton getBackButton() {
		return back;
	}
	
	// Get functions for Account UI Components
	public JButton getAccountAdd() {
		return accountAdd;
	}
	
	public JButton getAccountDelete() {
		return accountDelete;
	}
	
	public JTable getAccountTable() {
		return accountTable;
	}
	
	// Get functions for Department UI Components
	public JButton getDepartmentAdd() {
		return departmentAdd;
	}
	
	public JButton getDepartmentDelete() {
		return departmentDelete;
	}
	
	public JTable getDepartmentTable() {
		return departmentTable;
	}
	// Get functions for Degree UI Components
	public JButton getDegreeAdd() {
		return degreeAdd;
	}
	
	public JButton getDegreeDelete() {
		return degreeDelete;
	}
	
	public JTable getDegreeTable() {
		return degreeTable;
	}
	// Get functions for Module UI Components
	public JButton getModuleAdd() {
		return moduleAdd;
	}
	
	public JButton getModuleDelete() {
		return moduleDelete;
	}
	
	public JTable getModuleTable() {
		return moduleTable;
	}
	
	public void viewChange() {
		frame.getContentPane().removeAll();
	}
	
	public void removeUI() {
		if (adminMenu != null) {
			frame.remove(adminMenu);
		}
		if (accountPanel != null) {
			frame.remove(accountPanel);
		}
		if (accountButtons != null) {
			frame.menuBar.remove(accountButtons);
		}
		if (departmentPanel != null) {
			frame.remove(departmentPanel);
		}
		if (departmentButtons != null) {
			frame.menuBar.remove(departmentButtons);
		}
		if (degreePanel != null) {
			frame.remove(degreePanel);
		}
		if (degreeButtons != null) {
			frame.menuBar.remove(degreeButtons);
		}
		if (modulePanel != null) {
			frame.remove(modulePanel);
		}
		if (moduleButtons != null) {
			frame.menuBar.remove(moduleButtons);
		}
	}
	
	public void loadMenuUI() {
		// Clear the existing UI
		removeUI();
		// Setting up buttons and panel
		menuAccount = new JButton("Manage Accounts");
		menuDepartment = new JButton("Manage Departments");
		menuDegree = new JButton("Manage Degrees");
		menuModule = new JButton("Manage Modules");
		adminMenu = new JPanel();
		adminMenu.setLayout(new GridLayout(5,3));
		adminMenu.setBorder(BorderFactory.createEmptyBorder(50,200,50,200));
		// Adding the buttons to the panel
		GridBagConstraints menuConstraints = new GridBagConstraints();
		menuConstraints.insets = new Insets(100,500,100,500);
		menuConstraints.fill = GridBagConstraints.VERTICAL;
		menuConstraints.gridy = 0;
		adminMenu.add(menuAccount, menuConstraints);
		menuConstraints.gridy = 1;
		adminMenu.add(menuDepartment, menuConstraints);
		menuConstraints.gridy = 2;
		adminMenu.add(menuDegree, menuConstraints);
		menuConstraints.gridy = 3;
		adminMenu.add(menuModule, menuConstraints);
		// Adding panel to the frame
		frame.add(adminMenu, BorderLayout.CENTER);
		frame.showMenuBar();
		frame.revalidate();
		frame.repaint();
	}
	
	public void loadAccountUI() {
		// Clear the existing UI
		removeUI();
		//Table
		// Setting up buttons and the panel to hold them
		accountButtons = new JPanel();
		accountButtons.setLayout(new GridBagLayout());
		accountAdd = new JButton("Add");
		accountDelete = new JButton("Delete");
		accountDelete.setEnabled(false);
		back = new JButton("Back");
		// Adding the buttons to the panel
		GridBagConstraints buttonConstraints = new GridBagConstraints();
		buttonConstraints.fill = GridBagConstraints.HORIZONTAL;
		buttonConstraints.insets = new Insets(0,5,0,5);
		buttonConstraints.gridy = 0;
		buttonConstraints.gridx = 0;
		accountButtons.add(accountAdd, buttonConstraints);
		buttonConstraints.gridx = 1;
		accountButtons.add(accountDelete, buttonConstraints);
		buttonConstraints.gridx = 2;
		accountButtons.add(back, buttonConstraints);
		// Adding the buttons to the menu bar
		buttonConstraints.gridx = 0;
		frame.menuBar.add(accountButtons, buttonConstraints);
		

		String[] columnNames = {
				"User ID",
				"Username",
				"User Type"
		};
		
		accountPanel = new JPanel();
		accountPanel.setLayout(new GridBagLayout());
		GridBagConstraints accountConstraints = new GridBagConstraints();
		accountConstraints.weightx = 1.0;
		accountConstraints.insets = new Insets(5,5,5,5);
		accountPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
		
		accountTable = new JTable(accountsData, columnNames) {
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
		
		TableColumnAdjuster tca = new TableColumnAdjuster(accountTable);
		tca.adjustColumns();
		
		JScrollPane scrollPane = new JScrollPane(accountTable);
		accountTable.setFillsViewportHeight(true);
		accountConstraints.fill = GridBagConstraints.HORIZONTAL;
		accountConstraints.gridx = 0;
		accountConstraints.gridy = 1;
		accountPanel.add(scrollPane, accountConstraints);
		// Adding the panel to the frame
		frame.add(accountPanel, BorderLayout.CENTER);
		frame.showMenuBar();
		frame.revalidate();
		frame.repaint();
	}
	
	public void loadDepartmentUI() {
		// Clear the existing UI
		removeUI();
		//Table
		// Setting up buttons and the panel to hold them
		departmentButtons = new JPanel();
		departmentButtons.setLayout(new GridBagLayout());
		departmentAdd = new JButton("Add");
		departmentDelete = new JButton("Delete");
		departmentDelete.setEnabled(false);
		back = new JButton("Back");
		// Adding buttons to the panel
		GridBagConstraints buttonConstraints = new GridBagConstraints();
		buttonConstraints.fill = GridBagConstraints.HORIZONTAL;
		buttonConstraints.insets = new Insets(0,5,0,5);
		buttonConstraints.gridy = 0;
		buttonConstraints.gridx = 0;
		departmentButtons.add(departmentAdd, buttonConstraints);
		buttonConstraints.gridx = 1;
		departmentButtons.add(departmentDelete, buttonConstraints);
		buttonConstraints.gridx = 2;
		departmentButtons.add(back, buttonConstraints);
		// Adding the buttons to the menu bar
		buttonConstraints.gridx = 0;
		frame.menuBar.add(departmentButtons, buttonConstraints);

		// Setting up the main panel (currently placeholder)
		departmentPanel = new JPanel();
		departmentPanel.setLayout(new GridBagLayout());
		GridBagConstraints departmentConstraints = new GridBagConstraints();
		departmentConstraints.weightx = 1.0;
		departmentConstraints.insets = new Insets(5,5,5,5);
		
		String[] columnNames = {"Department Code", "Department Name"};
		
		departmentTable = new JTable(departmentsData, columnNames) {
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
		
		TableColumnAdjuster tca = new TableColumnAdjuster(departmentTable);
		tca.adjustColumns();
		
		JScrollPane scrollPane = new JScrollPane(departmentTable);
		departmentTable.setFillsViewportHeight(true);
		scrollPane.setPreferredSize(new Dimension(getWidth(),350));
		
		departmentConstraints.fill = GridBagConstraints.HORIZONTAL;
		departmentConstraints.gridx = 0;
		departmentConstraints.gridy = 1;
		departmentPanel.add(scrollPane, departmentConstraints);
		// Adding the panel to the frame
		frame.add(departmentPanel, BorderLayout.CENTER);
		frame.showMenuBar();
		frame.revalidate();
		frame.repaint();
	}
	
	public void loadDegreeUI() {
		// Clear the existing UI
		removeUI();
		//Table
		//Add/Delete/Back buttons
		degreeButtons = new JPanel();
		degreeButtons.setLayout(new GridBagLayout());
		
		degreeAdd = new JButton("Add");
		degreeDelete = new JButton("Delete");
		degreeDelete.setEnabled(false);
		back = new JButton("Back");
		
		GridBagConstraints buttonConstraints = new GridBagConstraints();
		buttonConstraints.fill = GridBagConstraints.HORIZONTAL;
		buttonConstraints.insets = new Insets(0,5,0,5);
		buttonConstraints.gridy = 0;
		
		buttonConstraints.gridx = 0;
		degreeButtons.add(degreeAdd, buttonConstraints);
		buttonConstraints.gridx = 1;
		degreeButtons.add(degreeDelete, buttonConstraints);
		buttonConstraints.gridx = 2;
		degreeButtons.add(back, buttonConstraints);
		buttonConstraints.gridx = 0;
		frame.menuBar.add(degreeButtons, buttonConstraints);
		
		degreePanel = new JPanel();
		degreePanel.setLayout(new GridBagLayout());
		GridBagConstraints degreeConstraints = new GridBagConstraints();
		degreeConstraints.gridy = 0;
		degreeConstraints.gridx = 1;
		JLabel title = new JLabel("Degree UI");
		degreePanel.add(title, degreeConstraints);
		
		frame.add(degreePanel, BorderLayout.CENTER);
		frame.showMenuBar();
		frame.revalidate();
		frame.repaint();
	}
	
	public void loadModuleUI() {
		// Clear the existing UI
		removeUI();
		//Table
		//Add/Delete/Back buttons
		moduleButtons = new JPanel();
		moduleButtons.setLayout(new GridBagLayout());
		
		moduleAdd = new JButton("Add");
		moduleDelete = new JButton("Delete");
		moduleDelete.setEnabled(false);
		back = new JButton("Back");
		
		GridBagConstraints buttonConstraints = new GridBagConstraints();
		buttonConstraints.fill = GridBagConstraints.HORIZONTAL;
		buttonConstraints.insets = new Insets(0,5,0,5);
		buttonConstraints.gridy = 0;
		
		buttonConstraints.gridx = 0;
		moduleButtons.add(moduleAdd, buttonConstraints);
		buttonConstraints.gridx = 1;
		moduleButtons.add(moduleDelete, buttonConstraints);
		buttonConstraints.gridx = 2;
		moduleButtons.add(back, buttonConstraints);
		buttonConstraints.gridx = 0;
		frame.menuBar.add(moduleButtons, buttonConstraints);
		
		modulePanel = new JPanel();
		modulePanel.setLayout(new GridBagLayout());
		GridBagConstraints moduleConstraints = new GridBagConstraints();
		moduleConstraints.weightx = 1.0;
		
		String[] columnNames = {
				"Module Code",
				"Module Name",
				"Credits",
				"Teaching Period",
				"Module Type"
		};
		
		moduleTable = new JTable(modulesData, columnNames) {
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
		
	    TableColumnAdjuster tca = new TableColumnAdjuster(moduleTable);
	    tca.adjustColumns();
		
		JScrollPane scrollPane = new JScrollPane(moduleTable);
		moduleTable.setFillsViewportHeight(true);
		
		scrollPane.setPreferredSize(new Dimension(getWidth(),350));
		
		moduleConstraints.insets = new Insets(5,5,5,5);
		moduleConstraints.fill = GridBagConstraints.HORIZONTAL;
		moduleConstraints.gridx = 0;
		moduleConstraints.gridy = 1;
		modulePanel.add(scrollPane, moduleConstraints);
		
		frame.add(modulePanel, BorderLayout.CENTER);
		frame.showMenuBar();
		frame.revalidate();
		frame.repaint();
	}
}
