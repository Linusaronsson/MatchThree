import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * ...
 */
class BoardController
{
	/**
	 * ...
	 */
	class Coordinate
	{
		public int x = 0;
		public int y = 0;
		
		/**
		 * ...
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
	 * ...
	 */
	public BoardController(BoardModel model, BoardView view)
	{
		this.model = model;
		this.view  = view;
		
		view.addOpenListener(new OpenListener());
		view.addQuitListener(new QuitListener());
		view.addButtonListener(new ButtonListener());
		view.addBoardListener(new BoardListener());
	}
	
	/**
	 * ...
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
	 * ...
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
	
	/**
	 * ...
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
	 * ...
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
	 * Swap two cells.
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
