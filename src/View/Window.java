package View;

import GameModes.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import View.MatchThreeUI;
import View.MainMenu;

@SuppressWarnings("serial")
public class Window
	extends JFrame
{
	private static final String WINDOW_TITLE = "MatchThree";
	
	private JPanel    content  = null;
	private JMenuItem newItem  = null;
	private JMenuItem openItem = null;
	private JMenuItem quitItem = null;
	private JMenuItem saveItem = null;
	
	public Window(JPanel content)
	{
		// Validate arguments //
		if (content == null) {
			throw new NullPointerException();
		}
		
		this.content = content;
		
		// Set application properties //
		// TODO: Avoid calling global state changing method from instance
		//       method.
		setProperties();
		
		// Set window properties //
		setTitle(WINDOW_TITLE);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationByPlatform(true);
		setPreferredSize(new Dimension(650, 650));
		setResizable(true);
		
		// Add content //
		add(content);
		
		// Set menu bar //
		JMenuBar menuBar = createMenuBar(this);
		setJMenuBar(menuBar);
		
		// Update window with content //
		pack();
		
		// Make window visible //
		centerWindow();
		setVisible(true);
	}
	
	/**
	 * Display an error message.
	 *
	 * @param message The message to display.
	 */
	public void showError(String message)
	{
		// Validate argument //
		if (message == null) {
			throw new NullPointerException();
		}
		
		JOptionPane.showMessageDialog(this,
		                              message,
		                              message,
		                              JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Display an informative message.
	 *
	 * @param message The message to display.
	 */
	public void showMessage(String message)
	{
		// Validate argument //
		if (message == null) {
			throw new NullPointerException();
		}
		
		JOptionPane.showMessageDialog(this,
		                              message,
		                              message,
		                              JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Centers the frame
	 */
	private void centerWindow()
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-getSize().width/2, dim.height/2-getSize().height/2);
	}
	
	/**
	 * Create window menu bar.
	 *
	 * @param self View to apply menu bar to.
	 * @return     Menu bar object.
	 */
	// TODO: Research use of `self` parameter name.
	private JMenuBar createMenuBar(Window self)
	{
		// Validate argument //
		if (self == null) {
			throw new NullPointerException();
		}
		
		// Create menu bar //
		JMenuBar menuBar = new JMenuBar();
		
		// Create menus //
		JMenu fileMenu = new JMenu("File");
		
		// Create "New" menu item //
		self.newItem = new JMenuItem("New");
		fileMenu.add(self.newItem);
		
		// Create "Open" menu item //
		self.openItem = new JMenuItem("Open");
		fileMenu.add(self.openItem);
		
		// Create "Save" menu item //
		self.saveItem = new JMenuItem("Save");
		fileMenu.add(self.saveItem);
		
		// Create "Quit" menu item //
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
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (ClassNotFoundException |
		         InstantiationException |
		         IllegalAccessException |
		         UnsupportedLookAndFeelException e)
		{
			System.err.println(e);
		}
	}
}
