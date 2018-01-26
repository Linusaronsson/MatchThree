import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * ...
 */
class BoardController
{
	private BoardModel model;
	private BoardView  view;
	
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
}
