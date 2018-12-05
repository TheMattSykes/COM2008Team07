package Views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
import Models.Approval;
import Models.Degree;
import Models.GraduateType;
import Models.Module;

public class EditModule extends JPanel {
	private static final long serialVersionUID = 1L;
	PrimaryFrame frame;
	private JPanel mainPanel;
	private JPanel formPanel;
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
	
	public JTable getApprovalTable() {
		return approvalTable;
	}
	
	public Approval getNewApproval() {
		Approval newApproval = new Approval();
		for (Degree degree : availableDegrees) {
			if (degree.getName().equals(degreesList.getSelectedItem())) {
				newApproval.setDegreeCode(degree.getCode());
			}
		}
		newApproval.setModuleCode(selectedModule.getCode());
		newApproval.setLevel((int)levelList.getSelectedItem());
		newApproval.setCore(core.isSelected());
		return newApproval;
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
		mainPanel = new JPanel();
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));
		
		GridBagConstraints formConstraints = new GridBagConstraints();
		formConstraints.weightx = 1.0;
		formConstraints.fill = GridBagConstraints.HORIZONTAL;
		
		mainPanel.setLayout(new BorderLayout(20,50));
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridBagLayout());
		
		JPanel moduleDetails = new JPanel();
		moduleDetails.setLayout(new GridLayout(1,2));
		
		JLabel codeLabel = new JLabel("Module Code: "+selectedModule.getCode());
		JLabel nameLabel = new JLabel("Module Name: "+selectedModule.getName());
		moduleDetails.add(codeLabel);
		moduleDetails.add(nameLabel);
		
		northPanel.add(moduleDetails);
		mainPanel.add(moduleDetails, BorderLayout.NORTH);
		
		// Table of degrees the selected module is approved for
		String[] columnNames = {
				"Degree Code",
				"Module Code",
				"Core",
				"Level"
		};
		
		Object[][] approvalsData = new Object[tableData.length][4];
		for (int i=0; i < tableData.length; i++) {
			approvalsData[i][0] = tableData[i][0];
			approvalsData[i][1] = tableData[i][1];
			if ( Integer.parseInt((String)tableData[i][2]) == 1) {
				approvalsData[i][2] = "true";
			} else if ( Integer.parseInt((String)tableData[i][2]) == 0) {
				approvalsData[i][2] = "false";
			}
			approvalsData[i][3] = tableData[i][3];
		}
		
		approvalTable = new JTable(approvalsData, columnNames) {
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
	    
	    JScrollPane approvalScrollPane = new JScrollPane(approvalTable);
	    approvalTable.setFillsViewportHeight(true);
	    
	    JPanel twoColumns = new JPanel(new GridBagLayout());
	    GridBagConstraints twoColumnConst = new GridBagConstraints();
	    twoColumnConst.weightx = 1.0;
	    twoColumnConst.fill = GridBagConstraints.HORIZONTAL;
	    twoColumnConst.gridx = 0;
	    twoColumnConst.gridy = 0;
	    
	    JLabel currentApprovalTitle = new JLabel("Currently approved on: ");
	    twoColumns.add(currentApprovalTitle, twoColumnConst);
	    
	    twoColumnConst.gridx = 1;
	    JLabel formTitle = new JLabel("Add module to a different degree: ");
	    twoColumns.add(formTitle, twoColumnConst);
	    
	    twoColumnConst.gridx = 0;
	    twoColumnConst.gridy = 1;
	    approvalScrollPane.setPreferredSize(new Dimension(getWidth()/2, 275));
	    twoColumns.add(approvalScrollPane, twoColumnConst);
	    
	    formPanel = new JPanel(new GridLayout(3,1));
	    formPanel.setPreferredSize(new Dimension(getWidth()/2, 275));
	    GridBagConstraints formConst = new GridBagConstraints();
	    
	    
	    // Degree Choice
	    String[] degreeNames = new String[availableDegrees.length];
	    int i = 0;
	    for (Degree degree : availableDegrees) {
	    	degreeNames[i] = degree.getName();
	    	i++;
	    }
	    
	    JPanel degreePanel = new JPanel();
	    JLabel degreeLabel = new JLabel("Degrees: ");
	    degreesList = new JComboBox<String>(degreeNames);
	    formConst.gridx = 0;
	    formConst.gridy = 0;
	    degreePanel.add(degreeLabel, formConst);
	    formConst.gridx = 1;
	    degreePanel.add(degreesList, formConst);
	    formPanel.add(degreePanel);
	    
	    // Level Choice
	    JPanel levelPanel = new JPanel();
	    JLabel levelLabel = new JLabel("Level: ");
	    Integer[] levels = {1,2,3,4,5,6};
	    levelList = new JComboBox<Integer>(levels);
	    formConst.gridx = 0;
	    formConst.gridy = 0;
	    levelPanel.add(levelLabel, formConst);
	    formConst.gridx = 1;
	    levelPanel.add(levelList, formConst);
	    formPanel.add(levelPanel);
	    
	    // Core?
	    JPanel corePanel = new JPanel();
	    core = new JCheckBox("Core module?");
	    corePanel.add(core, BorderLayout.CENTER);
	    formPanel.add(corePanel);
	    
	    twoColumnConst.gridx = 1;
	    twoColumnConst.gridy = 1;
	    twoColumns.add(formPanel, twoColumnConst);
	    mainPanel.add(twoColumns, BorderLayout.CENTER);
	    
	    // Delete approval button
	    deleteButton = new JButton("Remove approval");
	    deleteButton.setEnabled(false);
	    twoColumnConst.gridx = 0;
	    twoColumnConst.gridy = 2;
	    twoColumns.add(deleteButton, twoColumnConst);
	    
	    // Apply Button
	    applyButton = new JButton("Add approval");
	    twoColumnConst.gridx = 1;
	    twoColumns.add(applyButton, twoColumnConst);
	    
	    GridBagConstraints menuConstraints = new GridBagConstraints();
		menuConstraints.insets = new Insets(0,5,0,5);		
		menuConstraints.fill = GridBagConstraints.HORIZONTAL;
		menuConstraints.gridy = 0;
		menuConstraints.gridx = 0;
		
		localButtons = new JPanel();
		backButton = new JButton("Back");
		menuConstraints.gridx = 1;
		localButtons.add(backButton, menuConstraints);
		
		menuConstraints.gridx = 0;
		frame.menuBar.add(localButtons, menuConstraints);
		frame.add(mainPanel);
		frame.revalidate();
		frame.repaint();
	}
}