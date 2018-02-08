import java.io.IOException;
import View.SwapView;
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
		SwapView swapView = new SwapView();
		Window   window   = new Window(swapView);
		
		// Setup server listener //
		Server server = new Server(PORT_NUMBER, window);
		server.start();
	}
}
