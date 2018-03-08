package matchthree.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import matchthree.util.AssetManager;

/**
 * Application main window.
 *
 * @author Erik Selstam
 * @author Linus Aronsson
 * @author Erik Tran
 */
@SuppressWarnings("serial")
public class Window
	extends JFrame
{
	/** ... */
	private static final ImageIcon ICON =
		new ImageIcon(AssetManager.loadImage("Logo3.png"));
	
	/**
	 * Create `Window`.
	 *
	 * @author Erik Selstam
	 * @author Linus Aronsson
	 * @author Erik Tran
	 * @param title   Window title.
	 * @param menuBar Window menu bar, if any.
	 * @param content Window content pane.
	 */
	public Window(
		final String    title,
		final JMenuBar  menuBar,
		final Container content)
	{
		// Validate arguments //
		// TODO: Verify that `menuBar` may be null.
		if (title == null) {
			throw new NullPointerException();
		}
		if (content == null) {
			throw new NullPointerException();
		}
		
		// Set content //
		setContentPane(content);
		
		// Set menu bar //
		setJMenuBar(menuBar);
		
		// Set properties //
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setIconImage(ICON.getImage());
		setLocationByPlatform(true);
		setResizable(true);
		setTitle(title);
		
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
	 * @author Erik Selstam
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
	 *
	 * @author Erik Tran
	 * @author Erik Selstam
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
