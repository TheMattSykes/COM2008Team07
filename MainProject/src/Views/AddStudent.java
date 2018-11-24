package Views;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddStudent extends JPanel {
	private static final long serialVersionUID = 1L;
	PrimaryFrame frame;
	private RegistrarView backView = null;
	JPanel form;
	private JPanel formButtons;
	private JButton backButton;
	
	public AddStudent(PrimaryFrame pf) {
		frame = pf;
	}
	
	public PrimaryFrame getFrame() {
		return frame;
	}
	
	public void viewChange() {
		frame.getContentPane().removeAll();
	}
	
	public void removeUI() {
		if (form != null)
			frame.remove(form);
		if (backButton != null)
			frame.menuBar.remove(backButton);
	}
	
	public JButton getBackButton() {
		return backButton;
	}
	
	public void loadUI() throws Exception {
		formButtons = new JPanel();
		formButtons.setLayout(new GridBagLayout());
		form = new JPanel();
		form.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		
		JPanel formLeft = new JPanel();
		JPanel formRight = new JPanel();
		
		GridBagConstraints formConstraints = new GridBagConstraints();
		formConstraints.weightx = 1.0;
		formConstraints.insets = new Insets(20,20,20,20);
		formConstraints.fill = GridBagConstraints.HORIZONTAL;
		
		GridLayout oneColumn = new GridLayout (0, 1);
		GridLayout twoColumns = new GridLayout(0, 2);
		
		JPanel regPanel = new JPanel();
		JLabel regLabel = new JLabel("Reg. Number:");
		JTextField regTextField = new JTextField();
		regTextField.setText("Reg. Numbers are automatically generated");
		regTextField.setEnabled(false);
		
		formConstraints.gridx = 0;
		formConstraints.gridy = 0;
		regPanel.add(regLabel, formConstraints);
		formConstraints.gridx = 1;
		regPanel.add(regTextField, formConstraints);
		formLeft.add(regPanel, oneColumn);
		
		JPanel titlePanel = new JPanel();
		JLabel titleLabel = new JLabel("Title:");
		JComboBox<String> titleDropdown = new JComboBox<String>(new String[] {"Mr","Ms"});
		formConstraints.gridx = 0;
		titlePanel.add(titleLabel, formConstraints);
		formConstraints.gridx = 1;
		titlePanel.add(titleDropdown, formConstraints);
		formRight.add(titlePanel, oneColumn);
		
		form.add(formLeft, twoColumns);
		form.add(formRight, twoColumns);
		
		GridBagConstraints menuConstraints = new GridBagConstraints();
		menuConstraints.insets = new Insets(0,5,0,5);		
		//menuConstraints.fill = GridBagConstraints.HORIZONTAL;
		menuConstraints.gridy = 0;
		menuConstraints.gridx = 0;
		backButton = new JButton("Back");
		frame.menuBar.add(backButton, menuConstraints);
		
		frame.add(form);
		frame.revalidate();
		frame.repaint();
	}
}