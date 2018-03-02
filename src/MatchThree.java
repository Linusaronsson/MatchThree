package matchthree;

import java.io.IOException;
import matchthree.controller.ApplicationController;

/**
 * MatchThree game.
 */
public final class MatchThree
{
	/**
	 * Forbidden constructor.
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
