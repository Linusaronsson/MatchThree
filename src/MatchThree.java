import GUI.GUI;
import java.io.IOException;

/**
 * MatchThree game.
 */
public class MatchThree {
	/**
	 * Program entry point.
	 *
	 * @param args Program arguments.
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		// Initialize GUI //
		GUI ui = new GUI();
		
		// Setup server listener //
		Server server = new Server(3333, ui);
		server.start();
	}
}
