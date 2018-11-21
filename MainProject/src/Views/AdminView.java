package Views;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
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
	JPanel accountsPanel;
	JPanel departmentsPanel;
	JPanel degreesPanel;
	JPanel modulesPanel;
	
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
	
	public void viewChange() {
		
	}
	
	public void removeUI() {
		frame.remove(adminMenu);
	}
	
	public void loadUI() {
		JLabel menuTitle = new JLabel("Admin Menu");
		adminMenu = new JPanel();
		adminMenu.setLayout(new GridLayout(5,3));
		adminMenu.add(menuTitle);
		
		frame.add(adminMenu, BorderLayout.CENTER);
		frame.showMenuBar();
		frame.revalidate();
		frame.repaint();
	}
}
