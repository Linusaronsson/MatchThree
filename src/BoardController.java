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
		}
	}
}
