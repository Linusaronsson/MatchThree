import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * MatchThree game.
 */
public class MatchThree
	implements ActionListener
{
	private static final String WINDOW_TITLE  = "MatchThree";
	private static final int    WINDOW_WIDTH  = 512;
	private static final int    WINDOW_HEIGHT = 512;
	
	private JFrame frame;
	
	public MatchThree()
	{
		frame = createWindow();
	}
	
	/**
	 * Create the application window.
	 */
	private JFrame createWindow()
	{
		// Create frame //
		JFrame frame = new JFrame(WINDOW_TITLE);
		
		// Set frame properties //
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setVisible(true);
		
		// Set menu bar //
		JMenuBar menuBar = createMenuBar();
		frame.setJMenuBar(menuBar);
		
		return frame;
	}
	
	/**
	 * Create window menu bar.
	 */
	private JMenuBar createMenuBar()
	{
		// Create menu bar //
		JMenuBar menuBar = new JMenuBar();
		
		// Create menus //
		JMenu fileMenu = new JMenu("File");
		
		// Create Open menu item //
		JMenuItem openItem = new JMenuItem("Open");
		openItem.addActionListener(this);
		fileMenu.add(openItem);
		
		// Create Quit menu item //
		JMenuItem quitItem = new JMenuItem("Quit");
		quitItem.addActionListener(this);
		fileMenu.add(quitItem);
		
		// Add menus to menu bar //
		menuBar.add(fileMenu);
		
		return menuBar;
	}
	
	/**
	 * Event handler.
	 */
	public void actionPerformed(ActionEvent event)
	{
		// TODO: Placeholder.
		System.out.println("[Menu item]: " + event.getActionCommand());
	}
	
	/**
	 * Set application properties.
	 */
	private static void setProperties()
	{
		// Use native menu bar on OS X/macOS //
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Test");
		
		// Set look and feel //
		String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		}
		catch (ClassNotFoundException e) {}
		catch (InstantiationException e) {}
		catch (IllegalAccessException e) {}
		catch (UnsupportedLookAndFeelException e) {}
	}
	
	/**
	 * Program entry point.
	 */
	public static void main(String[] args)
	{
		// Set application properties //
		setProperties();
		
		// Create main window //
		MatchThree matchThree = new MatchThree();
		
		// Run the game //
		//matchThree.run();
		
		// Exit the program (killing any live threads) //
		//System.exit(0);
	}
}
