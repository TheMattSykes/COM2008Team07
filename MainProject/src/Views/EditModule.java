package Views;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Models.Degree;
import Models.GraduateType;
import Models.Module;

public class EditModule extends JPanel {
	private static final long serialVersionUID = 1L;
	PrimaryFrame frame;
	private JPanel panel;
	private JPanel localButtons;
	private JButton backButton;
	private JButton applyButton;
	private JButton deleteButton;
	private Degree[] availableDegrees;
	private Object[][] tableData;
	private Module selectedModule;
	private JComboBox<String> degreesList;
	private JComboBox<Integer> levelList;
	private JCheckBox core;
	
	public EditModule(PrimaryFrame pf) {
		frame = pf;
	}
	
	public PrimaryFrame getFrame() {
		return frame;
	}
	
	public void viewChange() {
		frame.getContentPane().removeAll();
	}
	
	public void removeUI() {
		if (panel != null)
			frame.remove(panel);
		if (localButtons != null)
			frame.menuBar.remove(localButtons);
	}
	
	public JButton getBackButton() {
		return backButton;
	}
	
	public JButton getApplyButton() {
		return applyButton;
	}
	
	public JButton getDeleteButton() {
		return deleteButton;
	}
	
	public void setDegrees(Degree[] d) {
		availableDegrees = d;
	}
	
	public void setModule(Module m) {
		selectedModule = m;
	}
	
	public void setTableData(Object[][] d) {
		tableData = d;
	}
	
	public void loadUI() throws Exception {
		
	}
}