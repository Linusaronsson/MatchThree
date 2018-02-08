import Controller.WindowController;
import java.io.IOException;
import View.SwapView;
import View.Window;

/**
 * MatchThree game.
 */
public final class MatchThree
{
	private static final int PORT_NUMBER = 3333;
	
	/**
	 * ...
	 */
	private MatchThree()
	{
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Program entry point.
	 *
	 * @param args Program arguments.
	 * @throws IOException
	 */
	public static void main(String[] args)
		throws IOException
	{
		// Create GUI //
		SwapView swapView = new SwapView();
		Window   window   = new Window(swapView);
		WindowController windowController = new WindowController(
			window,
			swapView
		);
		swapView.setWindow(window);
		
		// Setup server listener //
		Server server = new Server(PORT_NUMBER, window);
		server.start();
	}
}
