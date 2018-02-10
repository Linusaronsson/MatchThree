import Controller.WindowController;
import Multiplayer.Server;

import java.io.IOException;
import View.SwapView;
import View.Window;

/**
 * MatchThree game.
 */
public final class MatchThree
{
	/** Port number for network play. */
	private static final int PORT_NUMBER = 3333;
	
	/**
	 * ...
	 */
	private MatchThree() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Program entry point.
	 *
	 * @param args Program arguments.
	 * @throws IOException On file-system access errors.
	 */
	public static void main(final String[] args)
	throws IOException {
		// Create GUI //
		SwapView swapView = new SwapView();
		Window   window   = new Window(swapView);
		WindowController windowController = new WindowController(
			window,
			swapView
		);
		swapView.setWindow(window);
		
		// Setup server listener //
		Server server = new Server(swapView, PORT_NUMBER);
		server.start();
	}
}
