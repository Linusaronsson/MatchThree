package matchthree.view;

import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Main menu bar.
 *
 * @author Erik Selstam
 */
@SuppressWarnings("serial")
public class MainMenuBar
	extends JMenuBar
{
	/** "New Game" item. */
	private JMenuItem newItem = new JMenuItem("New Game");
	
	/** "Open" item. */
	private JMenuItem openItem = new JMenuItem("Open…");
	
	/** "Quit" item. */
	private JMenuItem quitItem = new JMenuItem("Quit");
	
	/** "Save" item. */
	private JMenuItem saveItem = new JMenuItem("Save…");
	
	/**
	 * Create `MenuBar`.
	 *
	 * @author Erik Selstam
	 */
	public MainMenuBar() {
		// Create "File" menu //
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.addSeparator();
		fileMenu.add(quitItem);
		
		// Assemble menu bar //
		add(fileMenu);
	}
	
	/**
	 * Add listener for "New" menu item.
	 *
	 * @author Erik Selstam
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
	 * @author Erik Selstam
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
	 * @author Erik Selstam
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
	 * @author Erik Selstam
	 * @param listener Event handler.
	 */
	public void addSaveListener(final ActionListener listener) {
		// Validate argument //
		if (listener == null) {
			throw new NullPointerException();
		}
		saveItem.addActionListener(listener);
	}
}
