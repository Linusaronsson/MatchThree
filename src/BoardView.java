import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.*;

/**
 * ...
 */
class BoardView
	extends JFrame
{
	// Constants //
	private static final String WINDOW_TITLE = "MatchThree";
	
	// DI fields //
	private BoardModel model;
	
	// Default fields //
	private JButton    button    = new JButton("Confirm");
	private JTextField textField = new JTextField(20);
	
	// Reference fields //
	private JMenuItem openItem;
	private JMenuItem quitItem;
	
	// TODO: Break into multiple methods?
	/**
	 * ...
	 */
	public BoardView(BoardModel model)
	{
		this.model = model;
		
		// Set application properties //
		// TODO: Avoid calling global state changing method from instance
		//       method.
		setProperties();
		
		// Set window properties //
		this.setTitle(WINDOW_TITLE);
		// TODO: Pass close event to controller instead.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationByPlatform(true);
		
		// Set menu bar //
		JMenuBar menuBar = createMenuBar(this);
		this.setJMenuBar(menuBar);
		
		// Initialize components //
		textField.setText(model.getValue());
		textField.setEditable(false);
		
		// Create content pane //
		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		
		// Construct header //
		JPanel header = new JPanel(new FlowLayout());
		header.add(new JLabel("Label"));
		header.add(textField);
		header.add(button);
		content.add(header);
		
		// Construct grid //
		JPanel grid = new JPanel(new GridLayout(4, 4));
		grid.setBackground(new Color(0x11, 0x11, 0x11));
		for (int i = 0; i < 4 * 4; i++) {
			// Create button //
			JButton button = new JButton();
			
			// Set button properties //
			button.setOpaque(true);
			button.setBorderPainted(false);
			button.setEnabled(true);
			button.setContentAreaFilled(false);
			button.setFocusPainted(false);
			button.setHorizontalAlignment(SwingConstants.CENTER);
			button.setVerticalAlignment(SwingConstants.CENTER);
			button.setForeground(new Color(0xEE, 0xEE, 0xEE));
			button.setBackground(Color.BLACK);
			button.setPreferredSize(new Dimension(80, 80));
			button.setFont(new Font("Helvetica Neue", Font.PLAIN, 24));
			
			// Generate a random value //
			if (i == 6) {
				button.setBackground(new Color(0xEE, 0xEE, 0xEE));
				button.setForeground(new Color(0x11, 0x11, 0x11));
				button.setContentAreaFilled(true);
			}
			int value = ThreadLocalRandom.current().nextInt(4);
			button.setText("" + value);
			
			// Add button to grid //
			grid.add(button);
		}
		content.add(grid);
		
		// Update window with content //
		this.setContentPane(content);
		this.pack();
	}
	
	// TODO: Research use of `self` parameter name.
	/**
	 * Create window menu bar.
	 */
	private static JMenuBar createMenuBar(BoardView self)
	{
		// Create menu bar //
		JMenuBar menuBar = new JMenuBar();
		
		// Create menus //
		JMenu fileMenu = new JMenu("File");
		
		// Create “Open” menu item //
		self.openItem = new JMenuItem("Open");
		fileMenu.add(self.openItem);
		
		// Create “Quit” menu item //
		self.quitItem = new JMenuItem("Quit");
		fileMenu.add(self.quitItem);
		
		// Add menus to menu bar //
		menuBar.add(fileMenu);
		
		return menuBar;
	}
	
	/**
	 * Set application properties.
	 */
	private static void setProperties()
	{
		// Use native menu bar on macOS/OS X //
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		// TODO: Does not appear important or is misused.
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Test");
		
		// Set look and feel //
		String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
		try { UIManager.setLookAndFeel(lookAndFeel); }
		catch (ClassNotFoundException e) {}
		catch (InstantiationException e) {}
		catch (IllegalAccessException e) {}
		catch (UnsupportedLookAndFeelException e) {}
	}
	
	/**
	 * ...
	 */
	public void addOpenListener(ActionListener listener)
	{
		openItem.addActionListener(listener);
	}
	
	/**
	 * ...
	 */
	public void addQuitListener(ActionListener listener)
	{
		quitItem.addActionListener(listener);
	}
	
	/**
	 * ...
	 */
	public void addButtonListener(ActionListener listener)
	{
		button.addActionListener(listener);
	}
	
	/**
	 * ...
	 */
	public void showMessage(String message)
	{
		JOptionPane.showMessageDialog(this,
		                              message,
		                              message,
		                              JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * ...
	 */
	public void showError(String message)
	{
		JOptionPane.showMessageDialog(this,
		                              message,
		                              message,
		                              JOptionPane.ERROR_MESSAGE);
	}
}
