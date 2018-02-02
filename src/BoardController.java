import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * MVC controller.
 */
class BoardController
{
	private Coordinate activeCell = null;
	private BoardModel model      = null;
	private BoardView  view       = null;
	
	/**
	 * Listens for board cell actions (clicks).
	 */
	class BoardListener
		implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			// Validate argument //
			if (event == null) {
				throw new NullPointerException();
			}
			
			// Get cell coordinates //
			// TODO: Assert event values?
			Cell       cell        = (Cell) event.getSource();
			Coordinate tmp         = cell.getPosition(); // TODO: Implement `clone`.
			Coordinate clickedCell = new Coordinate(tmp.x, tmp.y);
			
			// Activate cell if appropriate //
			if (activeCell == null) {
				activeCell = clickedCell;
				view.setCellState(activeCell, true);
				return;
			}
			
			// Deactivate and swap cells //
			view.setCellState(activeCell, false);
			try {
				moveCell(activeCell, clickedCell);
			} catch (IOException
			      | LineUnavailableException
			      | UnsupportedAudioFileException e)
			{
			    System.out.println("An IO-related error occurred. Exiting.");
			    System.exit(1);
			}
			activeCell = null;
			
			// Update score //
			view.updateScore();
		}
	}
	
	/**
	 * Listens for top button press.
	 */
	class ButtonListener
		implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			view.showMessage("Button pressed");
		}
	}
	
	/**
	 * Listens for "New" menu item.
	 */
	class NewListener
		implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			view.showError("“New” not implemented");
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
	 * Listens for "Quit" menu item.
	 */
	class QuitListener
		implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			// Close window //
			WindowEvent e = new WindowEvent(view, WindowEvent.WINDOW_CLOSING);
			view.dispatchEvent(e);
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
	 * Listens for window events.
	 */
	class WindowListener
		extends WindowAdapter
	{
		public void windowActivated(WindowEvent event) {}
		
		public void windowClosed(WindowEvent event) {}
		
		public void windowClosing(WindowEvent event) {
			// Close windows and free its resources //
			// TODO: Is it necessary to use `view.setVisible(false)` as well?
			view.dispose();
		}
		
		public void windowDeactivated(WindowEvent event) {}
		
		public void windowDeiconified(WindowEvent event) {}
		
		public void windowGainedFocus(WindowEvent event) {}
		
		public void windowIconified(WindowEvent event) {}
		
		public void windowLostFocus(WindowEvent event) {}
		
		public void windowOpened(WindowEvent event) {}
		
		public void windowStateChanged(WindowEvent event) {}
	}
	
	/**
	 * Constructor for `BoardController` MVC controller.
	 *
	 * @param model Model to use.
	 * @param view  View to use.
	 */
	public BoardController(BoardModel model, BoardView view)
	{
		// Validate arguments //
		if (model == null || view == null) {
			throw new NullPointerException();
		}
		
		this.model = model;
		this.view  = view;
		
		// Register event listeners //
		view.addBoardListener(new BoardListener());
		view.addButtonListener(new ButtonListener());
		view.addNewListener(new NewListener());
		view.addOpenListener(new OpenListener());
		view.addQuitListener(new QuitListener());
		view.addSaveListener(new SaveListener());
		view.addWindowListener(new WindowListener());
	}
	
	/**
	 * Swap two cells.
	 *
	 * @param position1 Coordinates of first cell.
	 * @param position2 Coordinates of second cell.
	 */
	private void moveCell(Coordinate from, Coordinate to)
		throws IOException,
		       LineUnavailableException,
		       UnsupportedAudioFileException
	{
		// Validate arguments //
		// TODO: Do bounds-checking on coordinates?
		if (from == null || to == null) {
			throw new NullPointerException();
		}
		
		// Swap cells //
		switch (model.move(from, to)) {
			case OK:     break;
			case BAD:    view.showError("Invalid move"); break;
			case CANCEL: break;
			default:     break; // TODO: Throw exception.
		}
		
		// Play swap audio //
		view.prepareAudio();
		view.playAudioSwap();
		
		// Update view //
		view.update();
	}
}
