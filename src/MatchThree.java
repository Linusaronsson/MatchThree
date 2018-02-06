/**
 * MatchThree game.
 */
public class MatchThree
{
	private static final int GAME_SIZE = 11;
	
	/**
	 * Program entry point.
	 *
	 * @param args Program arguments.
	 */
	public static void main(String[] args)
	{
		// Create MVC context //
		BoardModel      model      = new BoardModel(GAME_SIZE);
		BoardView       view       = new BoardView(model);
		BoardController controller = new BoardController(model, view);
		
		// Show GUI //
		// TODO: Move this to view?
		view.setVisible(true);
	}
}
