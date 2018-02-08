package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import Model.MatchThreeModel;
import View.SwapView;
import View.Window;

public class WindowController
{
	private SwapView swapView = null;
	private Window   window   = null;
	
	/**
	 * Listens for "New" menu item.
	 */
	class NewListener
		implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
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
		public void actionPerformed(ActionEvent event)
		{
			window.showError("“Open” not implemented");
		}
	}
	
	/**
	 * Listens for "Save" menu item.
	 */
	class SaveListener
		implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			window.showError("“Save” not implemented");
		}
	}
	
	/**
	 * Listens for "Quit" menu item.
	 */
	class QuitListener
		implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
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
		public void windowActivated(WindowEvent event) {}
		
		public void windowClosed(WindowEvent event) {}
		
		public void windowClosing(WindowEvent event)
		{
			// Close windows and free its resources //
			// TODO: Is it necessary to use `view.setVisible(false)` as well?
			window.dispose();
		}
		
		public void windowDeactivated(WindowEvent event) {}
		
		public void windowDeiconified(WindowEvent event) {}
		
		public void windowGainedFocus(WindowEvent event) {}
		
		public void windowIconified(WindowEvent event) {}
		
		public void windowLostFocus(WindowEvent event) {}
		
		public void windowOpened(WindowEvent event) {}
		
		public void windowStateChanged(WindowEvent event) {}
	}
	
	public WindowController(
		Window   window,
		SwapView swapView
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
