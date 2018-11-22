package Views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddStudent extends JPanel {
	private static final long serialVersionUID = 1L;
	PrimaryFrame frame;
	JPanel form;
	private JPanel formButtons;
	
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
		frame.remove(form);
	}
	
	public void loadUI() throws Exception {
		formButtons = new JPanel();
		formButtons.setLayout(new GridBagLayout());
		form.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
		
		GridBagConstraints formConstraints = new GridBagConstraints();
		formConstraints.weightx = 1.0;
		formConstraints.insets = new Insets(5,5,5,5);
		formConstraints.fill = GridBagConstraints.HORIZONTAL;
		
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
	}
}