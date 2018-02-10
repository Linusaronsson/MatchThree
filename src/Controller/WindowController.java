package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import View.SwapView;
import View.Window;

/**
 * Main window controller.
 */
public class WindowController
{
	/** ... */
	private SwapView swapView = null;
	
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
			MatchThreeController matchThreeController =
				swapView.getMatchThreeController();
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
			window.showError("Open not implemented");
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
			window.showError("Save not implemented");
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
			// Close window //
			WindowEvent e = new WindowEvent(window, WindowEvent.WINDOW_CLOSING);
			window.dispatchEvent(e);
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
	 * @param window   ...
	 * @param swapView ...
	 */
	public WindowController(
		final Window   window,
		final SwapView swapView
	) {
		// Register event listeners //
		window.addNewListener(new NewListener());
		window.addOpenListener(new OpenListener());
		window.addQuitListener(new QuitListener());
		window.addSaveListener(new SaveListener());
		window.addWindowListener(new WindowListener());
		
		this.swapView = swapView;
		this.window   = window;
	}
}
