import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * MVC controller.
 */
class BoardController
{
	private BoardModel model    = null;
	private Coordinate selected = null;
	private BoardView  view     = null;
	
	/**
	 * Listens for board cell actions (clicks).
	 */
	class BoardListener
		implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			// Get cell coordinates //
			Cell cell = (Cell) event.getSource();
			int x1 = cell.getPositionX();
			int y1 = cell.getPositionY();
			Coordinate position = new Coordinate(x1, y1);
			
			// Select cell if appropriate //
			if (selected == null) {
				selected = position;
				view.setCellState(x1, y1, true);
				return;
			}
			
			// Deactivate cell //
			int x2 = selected.x;
			int y2 = selected.y;
			view.setCellState(x2, y2, false);
			selected = null;
			
			// Swap the two cells //
			if (!swapCells(x1, y1, x2, y2)) {
				view.showError("Invalid move");
			}
			
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
	 * @param x1 X-coordinate of first cell.
	 * @param y1 Y-coordinate of first cell.
	 * @param x2 X-coordinate of second cell.
	 * @param y2 Y-coordinate of second cell.
	 * @return   Whether the swap was successful.
	 */
	private boolean swapCells(int x1, int y1, int x2, int y2)
	{
		if (!model.swap(x1, y1, x2, y2)) {
			return false;
		}
		
		view.updateCell(x1, y1);
		view.updateCell(x2, y2);
		return true;
	}
}
