package view;

import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class MainMenuBar
	extends JMenuBar
{
	/** ... */
	private JMenuItem newItem = new JMenuItem("New game");
	
	/** ... */
	private JMenuItem openItem = new JMenuItem("Open…");
	
	/** ... */
	private JMenuItem quitItem = new JMenuItem("Quit");
	
	/** ... */
	private JMenuItem saveItem = new JMenuItem("Save…");
	
	/**
	 * Create `MenuBar`.
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
}
