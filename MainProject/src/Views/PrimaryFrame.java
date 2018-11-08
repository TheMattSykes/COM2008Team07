package Views;
import java.awt.*;
import javax.swing.*;

public class PrimaryFrame extends JFrame {
	
	public PrimaryFrame(String windowTitle, JPanel centerContent) {
		super(windowTitle);
		
		setLayout(new BorderLayout());
		GridBagConstraints mainConstraints = new GridBagConstraints();
		
		JTextField textField = new JTextField();

		JLabel banner = new JLabel();
		banner.setPreferredSize(new Dimension(1000,160));
		banner.setIcon(new ImageIcon("img/banner.png"));
		
		Container mainContainer = getContentPane();
		
		mainContainer.add(banner, BorderLayout.NORTH);
		
		mainContainer.add(centerContent, BorderLayout.CENTER);
		
		pack();
	}
}
