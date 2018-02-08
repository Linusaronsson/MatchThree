package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import Model.*;
import Model.MatchThreeModel;
import View.*;

public class WindowController
{
	private MatchThreeController matchThree = null;
	private Window               view       = null;
	
	/**
	 * Listens for "New" menu item.
	 */
	class NewListener
		implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			// Restart the game //
			matchThree.restartGame();
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
			view.showError("“Open” not implemented");
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
			view.showError("“Save” not implemented");
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
			//WindowEvent e = new WindowEvent(view, WindowEvent.WINDOW_CLOSING);
			//view.dispatchEvent(e);
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
			//view.dispose();
		}
		
		public void windowDeactivated(WindowEvent event) {}
		
		public void windowDeiconified(WindowEvent event) {}
		
		public void windowGainedFocus(WindowEvent event) {}
		
		public void windowIconified(WindowEvent event) {}
		
		public void windowLostFocus(WindowEvent event) {}
		
		public void windowOpened(WindowEvent event) {}
		
		public void windowStateChanged(WindowEvent event) {}
	}
	
	public WindowController(Window view, MatchThreeController matchThree)
	{
		this.matchThree = matchThree;
		this.view       = view;
	}
}
