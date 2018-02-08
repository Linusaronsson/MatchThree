import java.io.IOException;
import View.Window;

/**
 * MatchThree game.
 */
public class MatchThree
{
	private static final int PORT_NUMBER = 3333;
	
	/**
	 * Program entry point.
	 *
	 * @param args Program arguments.
	 * @throws IOException
	 */
	public static void main(String[] args)
		throws IOException
	{
		// Initialize GUI //
		Window window = new Window();
		
		// Setup server listener //
		Server server = new Server(PORT_NUMBER, window);
		server.start();
	}
}
