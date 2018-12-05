package Views;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import Controllers.TableColumnAdjuster;
import Models.Degree;
import Models.GraduateType;
import Models.Module;

public class EditModule extends JPanel {
	private static final long serialVersionUID = 1L;
	PrimaryFrame frame;
	private JPanel mainPanel;
	private JPanel localButtons;
	private JButton backButton;
	private JButton applyButton;
	private JButton deleteButton;
	private JTable approvalTable;
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
		if (mainPanel != null)
			frame.remove(mainPanel);
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
		// Remove existing UI
		removeUI();
		// Setting up the Panels
		mainPanel = new JPanel();
		JPanel panelLeft = new JPanel();
		JPanel panelRight = new JPanel();
		BoxLayout twoColumns = new BoxLayout(mainPanel, BoxLayout.X_AXIS);
		BoxLayout leftColumn = new BoxLayout(panelLeft, BoxLayout.Y_AXIS);
		BoxLayout rightColumn = new BoxLayout(panelRight, BoxLayout.Y_AXIS);
		mainPanel.setLayout(twoColumns);
		panelLeft.setLayout(leftColumn);
		panelRight.setLayout(rightColumn);
		// Setting up Buttons
		localButtons = new JPanel();
		localButtons.setLayout(new GridBagLayout());
		applyButton = new JButton("Apply");
		deleteButton = new JButton("Delete");
		deleteButton.setEnabled(false);
		backButton = new JButton("Back");
		localButtons.add(backButton);
		// Setting up Table
		String[] columnNames = {
				"Degree Code",
				"Module Code",
				"Core",
				"Level"
		};
		
		GridBagConstraints leftConstraints = new GridBagConstraints();
		leftConstraints.weightx = 1.0;
		leftConstraints.insets = new Insets(5,5,5,5);
		panelLeft.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));
		
		approvalTable = new JTable(tableData, columnNames) {
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
		TableColumnAdjuster tca = new TableColumnAdjuster(approvalTable);
		tca.adjustColumns();
		
		JScrollPane scrollPane = new JScrollPane(approvalTable);
		approvalTable.setFillsViewportHeight(false);
		leftConstraints.fill = GridBagConstraints.HORIZONTAL;
		leftConstraints.gridx = 0;
		leftConstraints.gridy = 0;
		panelLeft.add(scrollPane, leftConstraints);
		
		deleteButton = new JButton("Delete");
		leftConstraints.gridx = 0;
		leftConstraints.gridy = 1;
		panelLeft.add(deleteButton, leftConstraints);
		
		applyButton = new JButton("Apply");
		panelRight.add(applyButton);
		
		backButton = new JButton("Back");
		frame.menuBar.add(localButtons);
		mainPanel.add(panelLeft);
		mainPanel.add(panelRight);
		
		frame.add(mainPanel);
		frame.showMenuBar();
		frame.revalidate();
		frame.repaint();
	}
}