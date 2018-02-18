package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JPanel;
import view.ErrorDialog;
import view.MainMenuBar;
import view.Window;

/**
 * Main window controller.
 */
public class MainWindowController
{
	/** ... */
	private static final String WINDOW_TITLE = "MatchThree";
	
	/** ... */
	private MatchThreeController matchThreeController = null;
	
	/** ... */
	private Window window = null;
	
	/**
	 * Listens for "New" menu item.
	 */
	class NewListener
		implements ActionListener
	{
		@Override
		public void actionPerformed(final ActionEvent event) {
			// Restart the game //
			matchThreeController.restartGame();
		}
	}
	
	/**
	 * Listens for "Open" menu item.
	 */
	class OpenListener
		implements ActionListener
	{
		@Override
		public void actionPerformed(final ActionEvent event) {
			new ErrorDialog("Function not implemented", "Open not implemented");
		}
	}
	
	/**
	 * Listens for "Save" menu item.
	 */
	class SaveListener
		implements ActionListener
	{
		@Override
		public void actionPerformed(final ActionEvent event) {
			// Save the game //
			matchThreeController.saveGame();
		}
	}
	
	/**
	 * Listens for "Quit" menu item.
	 */
	class QuitListener
		implements ActionListener
	{
		@Override
		public void actionPerformed(final ActionEvent event) {
			// Close main window //
			closeWindow();
		}
	}
	
	/**
	 * Listens for window events.
	 */
	class WindowListener
		extends WindowAdapter
	{
		@Override
		public void windowActivated(final WindowEvent event) { }
		
		@Override
		public void windowClosed(final WindowEvent event) {
			// TODO: Avoid directly closing the application.
			// TODO: Use constant for exit code.
			System.exit(0);
		}
		
		@Override
		public void windowClosing(final WindowEvent event) {
			// Close windows and free its resources //
			// TODO: Is it necessary to use `view.setVisible(false)` as well?
			window.dispose();
		}
		
		@Override
		public void windowDeactivated(final WindowEvent event) { }
		
		@Override
		public void windowDeiconified(final WindowEvent event) { }
		
		@Override
		public void windowGainedFocus(final WindowEvent event) { }
		
		@Override
		public void windowIconified(final WindowEvent event) { }
		
		@Override
		public void windowLostFocus(final WindowEvent event) { }
		
		@Override
		public void windowOpened(final WindowEvent event) { }
		
		@Override
		public void windowStateChanged(final WindowEvent event) { }
	}
	
	/**
	 * ...
	 *
	 * @param window          ...
	 * @param swapView        ...
	 * @param matchThreeModel ...
	 */
	public MainWindowController() {
		// Create main window //
		// TODO: Consider removing this panel.
		JPanel panel = new JPanel();
		MainMenuBar menuBar = new MainMenuBar();
		window = new Window(WINDOW_TITLE, menuBar, panel);
		
		// Create UI //
		UIController uiController = new UIController(panel, this);
		
		// Resize window //
		pack();
		
		// Register event listeners //
		menuBar.addNewListener(new NewListener());
		menuBar.addOpenListener(new OpenListener());
		menuBar.addQuitListener(new QuitListener());
		menuBar.addSaveListener(new SaveListener());
		window.addWindowListener(new WindowListener());
		
		// TODO: Fix this.
		this.matchThreeController = null;
	}
	
	/**
	 * Close main window.
	 */
	private void closeWindow() {
		WindowEvent e = new WindowEvent(window, WindowEvent.WINDOW_CLOSING);
		window.dispatchEvent(e);
	}
	
	public void pack() {
		window.pack();
	}
	
	public void centerWindow() {
		window.centerWindow();
	}
}
