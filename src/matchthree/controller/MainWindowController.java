package matchthree.controller;

import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import matchthree.view.ErrorDialog;
import matchthree.view.MainMenuBar;
import matchthree.view.MainPane;
import matchthree.view.Window;

/**
 * Main window controller.
 *
 * @author Erik Selstam
 * @author Erik Tran
 */
public class MainWindowController
{
	/** Exit code - Success. */
	private static final int EXIT_OK = 0;
	
	/** Default window title. */
	private static final String WINDOW_TITLE = "MatchThree";
	
	/** Reference to MatchThree controller. */
	private GridViewController gridViewController = null;
	
	/** Window view. */
	private Window window = null;
	
	/**
	 * Listens for window events.
	 *
	 * @author Erik Selstam
	 */
	class WindowListener
		extends WindowAdapter
	{
		/**
		 * Constructor.
		 */
		WindowListener() {
			super();
		}
		
		@Override
		public void windowActivated(final WindowEvent event) { }
		
		@Override
		public void windowClosed(final WindowEvent event) {
			// Close application //
			// TODO: Try to be more graceful.
			System.exit(EXIT_OK);
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
	 * Create `MainWindowController`.
	 *
	 * @author Erik Selstam
	 */
	public MainWindowController() {
		// TODO: Fix this.
		this.gridViewController = null;
		
		// Create main window //
		// TODO: Consider removing this panel.
		MainMenuBar menuBar = new MainMenuBar();
		Container   panel   = new MainPane();
		window = new Window(WINDOW_TITLE, menuBar, panel);
		
		// Create UI //
		UIController uiController = new UIController(panel, this);
		
		// Resize window //
		pack();
		
		// Register event listeners //
		menuBar.addNewListener(event -> {
			// Restart the game //
			gridViewController.restartGame();
		});
		menuBar.addOpenListener(event -> {
			// TODO: Not implemented.
			new ErrorDialog("Function not implemented", "Open not implemented");
		});
		menuBar.addQuitListener(event -> {
			// Close main window //
			closeWindow();
		});
		menuBar.addSaveListener(event -> {
			// Save the game //
			gridViewController.saveGame();
		});
		window.addWindowListener(new WindowListener());
	}
	
	/**
	 * ...
	 *
	 * @author Erik Tran
	 */
	public void centerWindow() {
		window.centerWindow();
	}
	
	/**
	 * Close main window.
	 *
	 * @author Erik Selstam
	 */
	protected void closeWindow() {
		WindowEvent event = new WindowEvent(window, WindowEvent.WINDOW_CLOSING);
		window.dispatchEvent(event);
	}
	
	/**
	 * Update window with content.
	 *
	 * @author Erik Selstam
	 */
	public void pack() {
		window.pack();
	}
}
