import controller.ApplicationController;
import java.io.IOException;

/**
 * MatchThree game.
 */
public final class MatchThree
{
	/** Default game width. */
	private static final int GAME_SIZE = 6;
	
	/** Port number for network play. */
	private static final int PORT_NUMBER = 3333;
	
	/**
	 * Create a `MatchThree`.
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
		// Create application //
		new ApplicationController();
	}
}
