import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * MatchThree game.
 */
public class MatchThree
{
	/**
	 * Program entry point.
	 */
	public static void main(String[] args)
	{
		// Create MVC context //
		BoardModel      model      = new BoardModel();
		BoardView       view       = new BoardView(model);
		BoardController controller = new BoardController(model, view);
		
		// Show GUI //
		view.setVisible(true);
		
		// Exit the program (killing any live threads) //
		//System.exit(0);
	}
}
