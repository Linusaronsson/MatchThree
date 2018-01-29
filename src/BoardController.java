import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
			// Get cell coordinates //
			Cell       cell        = (Cell) event.getSource();
			Coordinate clickedCell = cell.getPosition();
			
			// Activate cell if appropriate //
			if (activeCell == null) {
				activeCell = clickedCell;
				view.setCellState(activeCell, true);
				return;
			}
			
			// Deactivate and swap cells //
			view.setCellState(activeCell, false);
			swapCells(activeCell, clickedCell);
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
	 * Listens for “Open” menu item.
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
	 * Listens for “Quit” menu item.
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
		this.model = model;
		this.view  = view;
		
		view.addBoardListener(new BoardListener());
		view.addButtonListener(new ButtonListener());
		view.addOpenListener(new OpenListener());
		view.addQuitListener(new QuitListener());
		view.addWindowListener(new WindowListener());
	}
	
	/**
	 * Swap two cells.
	 *
	 * @param position1 Coordinates of first cell.
	 * @param position2 Coordinates of second cell.
	 */
	private void swapCells(Coordinate position1, Coordinate position2)
	{
		// Swap cells //
		switch (model.swap(position1, position2)) {
			case OK:     break;
			case BAD:    view.showError("Invalid move"); break;
			case CANCEL: break;
			default:     break; // TODO: Throw exception.
		}
		
		// Update view //
		view.updateCell(position1);
		view.updateCell(position2);
	}
}
