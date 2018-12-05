package Views;
import java.awt.*;
import javax.swing.*;

/**
 * PrimaryFrame
 * Defines the primary frame of the application
 */
public class PrimaryFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private Container mainContainer = getContentPane();
	
	JPanel menuBar;
	
	private JButton logout;
	
	public PrimaryFrame(String windowTitle) {
		super(windowTitle);
		
		// Set frame properties
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		setSize(1000,800);
		setLocation(screenSize.width/2-getSize().width/2, screenSize.height/2-getSize().height/2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		
		setLayout(new BorderLayout());
		
		JLabel banner = new JLabel();
		banner.setPreferredSize(new Dimension(1000,160));
		banner.setIcon(new ImageIcon("img/banner.png"));
		
		menuBar = new JPanel();
		menuBar.setLayout(new GridBagLayout());
		
		GridBagConstraints menuConstraints = new GridBagConstraints();
		
		logout = new JButton("Log Out");
		
		menuConstraints.insets = new Insets(10,10,10,10);
		
		menuConstraints.fill = GridBagConstraints.HORIZONTAL;
		menuConstraints.gridx = 0;
		menuConstraints.gridy = 1;
		menuBar.add(logout, menuConstraints);
		
		mainContainer.add(banner, BorderLayout.NORTH);
		
		pack();
	}
	
	public void showMenuBar() {
		mainContainer.add(menuBar, BorderLayout.SOUTH);
		revalidate();
		repaint();
	}
	
	public void hideMenuBar() {
		mainContainer.remove(menuBar);
		revalidate();
		repaint();
	}
	
	public JButton getLogoutButton() {
		return logout;
	}
}
