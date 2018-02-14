import controller.WindowController;
import multiplayer.Server;

import java.io.IOException;
import model.MatchThreeModel;
import view.SwapView;
import view.Window;

/**
 * MatchThree game.
 */
public final class MatchThree
{
	/** ... */
	private static final int GAME_SIZE = 6;
	
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
		// Create game //
		MatchThreeModel matchThreeModel = new MatchThreeModel(GAME_SIZE);
		SwapView swapView = new SwapView();
		Window   window   = new Window(swapView);
		WindowController windowController = new WindowController(
			window,
			swapView,
			matchThreeModel
		);
		swapView.setWindow(window);
		
		// Setup server listener //
		Server server = new Server(swapView, PORT_NUMBER);
		server.start();
	}
}
