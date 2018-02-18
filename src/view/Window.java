package view;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

/**
 * Application main window.
 */
@SuppressWarnings("serial")
public class Window
	extends JFrame
{
	/** Window content pane. */
	private JPanel content = null;
	
	/**
	 * Create `Window`.
	 *
	 * @param title   Window title.
	 * @param menuBar Window menu bar, if any.
	 * @param content Window content pane.
	 */
	public Window(
		final String   title,
		final JMenuBar menuBar,
		final JPanel   content
	) {
		// Validate arguments //
		// TODO: Verify that `menuBar` may be null.
		if (title == null) {
			throw new NullPointerException();
		}
		if (content == null) {
			throw new NullPointerException();
		}
		
		this.content = content;
		
		// Set window properties //
		setTitle(title);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationByPlatform(true);
		setResizable(true);
		
		// Set menu bar //
		setJMenuBar(menuBar);
		
		// Add content //
		setContentPane(content);
		
		// Automatically resize window //
		pack();
		
		// Center window //
		centerWindow();
		
		// Make window visible //
		setVisible(true);
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
	 * Center the frame.
	 */
	public void centerWindow() {
		// Get screen dimensions //
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		// Calculate coordinates //
		int x = screen.width  / 2 - getSize().width  / 2;
		int y = screen.height / 2 - getSize().height / 2;
		
		// Move window //
		setLocation(x, y);
	}
}
