package View;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Application main window.
 */
@SuppressWarnings("serial")
public class Window
	extends JFrame
{
	/** ... */
	private static final String WINDOW_TITLE = "MatchThree";
	
	/** ... */
	private JPanel content = null;
	
	/** ... */
	private JMenuItem newItem = new JMenuItem("New game");
	
	/** ... */
	private JMenuItem openItem = new JMenuItem("Open…");
	
	/** ... */
	private JMenuItem quitItem = new JMenuItem("Quit");
	
	/** ... */
	private JMenuItem saveItem = new JMenuItem("Save…");
	
	/**
	 * ...
	 *
	 * @param content ...
	 */
	public Window(final JPanel content) {
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
		// TODO: Avoid magic numbers.
		setTitle(WINDOW_TITLE);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationByPlatform(true);
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
	 * Add listener for "New" menu item.
	 *
	 * @param listener Event handler.
	 */
	public void addNewListener(final ActionListener listener) {
			// Validate argument //
			if (listener == null) {
					throw new NullPointerException();
			}
			newItem.addActionListener(listener);
	}
	
	/**
	 * Add listener for "Open" menu item.
	 *
	 * @param listener Event handler.
	 */
	public void addOpenListener(final ActionListener listener) {
			// Validate argument //
			if (listener == null) {
					throw new NullPointerException();
			}
			openItem.addActionListener(listener);
	}
	
	/**
	 * Add listener for "Quit" menu item.
	 *
	 * @param listener Event handler.
	 */
	public void addQuitListener(final ActionListener listener) {
			// Validate argument //
			if (listener == null) {
					throw new NullPointerException();
			}
			quitItem.addActionListener(listener);
	}
	
	/**
	 * Add listener for "Save" menu item.
	 *
	 * @param listener Event handler.
	 */
	public void addSaveListener(final ActionListener listener) {
			// Validate argument //
			if (listener == null) {
					throw new NullPointerException();
			}
			saveItem.addActionListener(listener);
	}
	
	/**
	 * Add listener for board cell actions (clicks).
	 *
	 * @param listener Event handler.
	 */
	public void addWindowListener(final ActionListener listener) {
		// Validate argument //
		if (listener == null) {
			throw new NullPointerException();
		}
		
		addWindowListener(listener);
	}
	
	/**
	 * Display an error message.
	 *
	 * @param message The message to display.
	 */
	public void showError(final String message) {
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
	public void showMessage(final String message) {
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
	 * Center the frame.
	 */
	public void centerWindow() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(
			dim.width  / 2 - getSize().width  / 2,
			dim.height / 2 - getSize().height / 2
		);
	}
	
	/**
	 * Create window menu bar.
	 *
	 * @param self View to apply menu bar to.
	 * @return     Menu bar object.
	 */
	// TODO: Research use of `self` parameter name.
	private JMenuBar createMenuBar(final Window self) {
		// Validate argument //
		if (self == null) {
			throw new NullPointerException();
		}
		
		// Create "File" menu //
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(self.newItem);
		fileMenu.add(self.openItem);
		fileMenu.add(self.saveItem);
		fileMenu.addSeparator();
		fileMenu.add(self.quitItem);
		
		// Create menu bar //
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		
		return menuBar;
	}
	
	/**
	 * Set application properties.
	 */
	private static void setProperties() {
		// Use native menu bar on macOS/OS X //
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		// TODO: Does not appear important or is misused.
		System.setProperty(
			"com.apple.mrj.application.apple.menu.about.name",
			"Test"
		);
		
		// Set look and feel //
		String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (ClassNotFoundException
		| InstantiationException
		| IllegalAccessException
		| UnsupportedLookAndFeelException e) {
			System.err.println(e);
		}
	}
}
