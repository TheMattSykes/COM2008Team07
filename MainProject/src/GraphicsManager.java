import javax.swing.*;
import java.awt.*;

public class GraphicsManager {
	
	public void runUI() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Instantiate objects
				LoginView loginView = new LoginView();
				JPanel loginUI = loginView.loginUI();
				
				// Create main frame for UI
				PrimaryFrame mainFrame = new PrimaryFrame("University System", loginUI);
				
				// Set frame properties
				mainFrame.setSize(1000,800);
				mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				mainFrame.setVisible(true);
				mainFrame.setResizable(false);
				
				Toolkit tk = Toolkit.getDefaultToolkit();
				Dimension screenSize = tk.getScreenSize();
				mainFrame.setLocation(new Point(screenSize.width/4,screenSize.height/8));
			}
		});
	}
}
