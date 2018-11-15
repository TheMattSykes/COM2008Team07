package Views;
import java.awt.*;
import javax.swing.*;

public class PrimaryFrame extends JFrame {
	
	private Container mainContainer = getContentPane();
	
	public PrimaryFrame(String windowTitle) {
		super(windowTitle);
		
		// Set frame properties
		setSize(1000,800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		setLocation(new Point(50,50));
		
		setLayout(new BorderLayout());
		
		JLabel banner = new JLabel();
		banner.setPreferredSize(new Dimension(1000,160));
		banner.setIcon(new ImageIcon("img/banner.png"));
		
		JButton test = new JButton("Test");
		test.setToolTipText("Test");
		
		JToolBar toolbar = new JToolBar();
		toolbar.add(test);
		
		mainContainer.add(banner, BorderLayout.NORTH);
		mainContainer.add(toolbar, BorderLayout.SOUTH);
		
		pack();
	}
	
	public void updateView(JPanel newContent) {
		mainContainer.add(newContent, BorderLayout.CENTER);
	}
}
