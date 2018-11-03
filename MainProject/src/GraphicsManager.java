import javax.swing.*;
import java.awt.*;

public class GraphicsManager {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				LoginSystem mainLogin = new LoginSystem();
				JPanel loginUI = mainLogin.loginUI();
				
				PrimaryFrame mainFrame = new PrimaryFrame("University System", loginUI);
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
