package Views;

import java.awt.BorderLayout;
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

public class AdminView extends JPanel {
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
	
	JPanel departmentPanel;
	JPanel departmentButtons;
	JButton departmentAdd;
	JButton departmentDelete;
	
	JPanel degreePanel;
	JPanel degreeButtons;
	JButton degreeAdd;
	JButton degreeDelete;
	
	JPanel modulePanel;
	JPanel moduleButtons;
	JButton moduleAdd;
	JButton moduleDelete;
	
	public AdminView(PrimaryFrame pf) {
		frame = pf;
	}
	
	public PrimaryFrame getFrame() {
		return frame;
	}
	
	public void setDataAccounts(Object[][] a) {
		accountsData = a;
	}
	
	public void setDataDepartments(Object[][] d) {
		departmentsData = d;
	}
	
	public void setDataDegrees(Object[][] d) {
		degreesData = d;
	}
	
	public void setDataModules(Object[][] m) {
		modulesData = m;
	}
	
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
		menuAccount = new JButton("Manage Accounts");
		menuDepartment = new JButton("Manage Departments");
		menuDegree = new JButton("Manage Departments");
		menuModule = new JButton("Manage Modules");
		
		adminMenu = new JPanel();
		adminMenu.setLayout(new GridLayout(5,3));
		adminMenu.setBorder(BorderFactory.createEmptyBorder(50,200,50,200));
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
		
		frame.add(adminMenu, BorderLayout.CENTER);
		frame.showMenuBar();
		frame.revalidate();
		frame.repaint();
	}
	
	public void loadAccountUI() {
		//Table
		accountButtons = new JPanel();
		accountButtons.setLayout(new GridBagLayout());
		
		accountAdd = new JButton("Add");
		accountDelete = new JButton("Delete");
		accountDelete.setEnabled(false);
		back = new JButton("Back");
		
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
		buttonConstraints.gridx = 0;
		frame.menuBar.add(accountButtons, buttonConstraints);
		
		accountPanel = new JPanel();
		accountPanel.setLayout(new GridBagLayout());
		GridBagConstraints accountConstraints = new GridBagConstraints();
		accountConstraints.gridy = 0;
		accountConstraints.gridx = 1;
		JLabel title = new JLabel("Account UI");
		accountPanel.add(title, accountConstraints);
		
		frame.add(accountPanel, BorderLayout.CENTER);
		frame.showMenuBar();
		frame.revalidate();
		frame.repaint();
	}
	
	public void loadDepartmentUI() {
		//Table
		departmentButtons = new JPanel();
		departmentButtons.setLayout(new GridBagLayout());
		
		departmentAdd = new JButton("Add");
		departmentDelete = new JButton("Delete");
		departmentDelete.setEnabled(false);
		back = new JButton("Back");
		
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
		buttonConstraints.gridx = 0;
		frame.menuBar.add(departmentButtons, buttonConstraints);

		departmentPanel = new JPanel();
		departmentPanel.setLayout(new GridBagLayout());
		GridBagConstraints departmentConstraints = new GridBagConstraints();
		departmentConstraints.gridy = 0;
		departmentConstraints.gridx = 1;
		JLabel title = new JLabel("Department UI");
		departmentPanel.add(title, departmentConstraints);
		
		frame.add(departmentPanel, BorderLayout.CENTER);
		frame.showMenuBar();
		frame.revalidate();
		frame.repaint();
	}
	
	public void loadDegreeUI() {
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
		moduleConstraints.gridy = 0;
		moduleConstraints.gridx = 1;
		JLabel title = new JLabel("Module UI");
		modulePanel.add(title,moduleConstraints);
		
		frame.add(modulePanel, BorderLayout.CENTER);
		frame.showMenuBar();
		frame.revalidate();
		frame.repaint();
	}
}
