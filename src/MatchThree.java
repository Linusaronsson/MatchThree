import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * MatchThree game.
 */
public class MatchThree
{
	private static final int GAME_SIZE = 6;
	
	/**
	 * Program entry point.
	 *
	 * @param args Program arguments.
	 */
	public static void main(String[] args)
	{
		try {
			// Create MVC context //
			BoardModel      model      = new BoardModel(GAME_SIZE);
			BoardView       view       = new BoardView(model);
			BoardController controller = new BoardController(model, view);
			
			// Show GUI //
			// TODO: Move this to view?
			view.setVisible(true);
		} catch (IOException
		      | LineUnavailableException
		      | UnsupportedAudioFileException e)
		{
			System.err.println(e);
			System.exit(1);
		}
	}
}
