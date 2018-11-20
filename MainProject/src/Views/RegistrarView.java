package Views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import Controllers.DatabaseController;
import Models.Grades;
import Models.GraduateType;
import Models.Module;

public class RegistrarView extends JPanel {
	PrimaryFrame frame;
	Object[][] data;
	JPanel studentInfo;
	DatabaseController dc = new DatabaseController();
	
	public RegistrarView(PrimaryFrame pf) {
		frame = pf;
	}
	
	public PrimaryFrame getFrame() {
		return frame;
	}
	
	public void setData(Object[][] d) {
		data = d;
	}
	
	public void viewChange() {
		frame.getContentPane().removeAll();
	}
	
	public void removeUI() {
		frame.remove(studentInfo);
	}
	
	public void loadUI() throws Exception {
		String stuName = "Freddie Mercury";
		
		JPanel studentDetails = new JPanel();
		studentDetails.setLayout(new GridLayout(5,1));
		
		JLabel nameLabel = new JLabel("Name: "+stuName);
		
		studentDetails.add(nameLabel);
		
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

		
		
		JTable table = new JTable(data, columnNames);
		
		DefaultTableModel model = new DefaultTableModel(new String[]{"Class Name", "Home work", "Due Date"}, 0);
		
		String query = "SELECT * FROM students WHERE reg_number = ?";
		String[][] values = new String[1][3];
		values[0][0] = "12345678";
		values[0][1] = "12345679";
		values[0][2] = "true";
		String[] results = dc.executeQuery(query, values);
		
		for (String result : results) {
			System.out.println(result);
		}
		
		// table.getColumnModel().getColumn(7).setPreferredWidth(5);
		// table.getColumnModel().getColumn(1).setPreferredWidth(40);
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
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
