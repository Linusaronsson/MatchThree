package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import Model.Jewel;
import Model.MatchThreeModel;
import Model.Serialize;
import View.SwapView;
import View.Window;

/**
 * Main window controller.
 */
public class WindowController
{
	/** ... */
	private MatchThreeModel matchThreeModel = null;
	
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
			restartGame();
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
			// Save the game //
			saveGame();
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
	public WindowController(
		final Window          window,
		final SwapView        swapView,
		final MatchThreeModel matchThreeModel
	) {
		// Register event listeners //
		window.addNewListener(new NewListener());
		window.addOpenListener(new OpenListener());
		window.addQuitListener(new QuitListener());
		window.addSaveListener(new SaveListener());
		window.addWindowListener(new WindowListener());
		
		this.matchThreeModel = matchThreeModel;
		this.swapView        = swapView;
		this.window          = window;
	}
	
	/**
	 * Close main window.
	 */
	private void closeWindow() {
		WindowEvent e = new WindowEvent(window, WindowEvent.WINDOW_CLOSING);
		window.dispatchEvent(e);
	}
	
	/**
	 * Save the game.
	 */
	private void saveGame() {
		// Serialize board content //
		Jewel[] board = matchThreeModel.getBoard();
		String serial = null;
		try {
			serial = Serialize.serialize(board);
		} catch (Serialize.UnsupportedTypeException e) {
			window.showError("Could not serialize model contents");
			return;
		}
		
		// Get score //
		int score = matchThreeModel.getScore();
		
		// Get width //
		int width = matchThreeModel.getWidth();
		
		// Get save destination //
		File f = window.showSaveDialog();
		
		// Cancel if no file was chosen //
		if (f == null) {
			return;
		}
		
		// Save game //
		System.out.printf(
			"Saving game to \"%s\"...%s",
			f.toString(),
			System.lineSeparator()
		);
		System.out.printf(
			"> score: %d%s",
			score,
			System.lineSeparator()
		);
		System.out.printf(
			"> width: %d%s",
			width,
			System.lineSeparator()
		);
		System.out.printf(
			"> board: %s%s",
			serial,
			System.lineSeparator()
		);
		
		// Display confirmation //
		window.showMessage("Game saved");
	}
	
	/**
	 * Restart the game.
	 */
	private void restartGame() {
		MatchThreeController matchThreeController =
			swapView.getMatchThreeController();
		matchThreeController.restartGame();
	}
}
