/**
 * MatchThree game.
 */
public class MatchThree
{
	/**
	 * Program entry point.
	 *
	 * @param args Program arguments.
	 */
	public static void main(String[] args)
	{
		// Create MVC context //
		BoardModel      model      = new BoardModel(4);
		BoardView       view       = new BoardView(model);
		BoardController controller = new BoardController(model, view);
		
		// Show GUI //
		view.setVisible(true);
	}
}
