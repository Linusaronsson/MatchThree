import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * MVC controller.
 */
class BoardController
{
	/**
	 * Stores a two-dimensional coordinate.
	 */
	class Coordinate
	{
		public int x = 0;
		public int y = 0;
		
		/**
		 * Constructor for `Coordinate`.
		 *
		 * @param x X-coordinate.
		 * @param y Y-coordinate.
		 */
		public Coordinate(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
	}
	
	private BoardModel model;
	private BoardView  view;
	
	private Coordinate selected = null;
	
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
	
	/**
	 * Listens for board cell actions (clicks).
	 */
	class BoardListener
		implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			view.showMessage("You pressed: " + event.getActionCommand());
			
			// Swap two cells //
			if (!swapCells(0, 0, 0, 2)) {
				view.showError("Could not swap cells");
			}
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
			// TODO: Verify proper cleanup.
			// TODO: Perform proper shutdown.
			System.exit(0);
		}
	}
}
