package matchthree;

import java.io.IOException;
import matchthree.controller.ApplicationController;

/**
 * MatchThree game.
 *
 * @author Erik Selstam
 */
public final class MatchThree
{
	/**
	 * Forbidden constructor.
	 *
	 * @author Erik Selstam
	 */
	private MatchThree() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Program entry point.
	 *
	 * @author Erik Selstam
	 * @param args Program arguments.
	 * @throws IOException On file-system access errors.
	 */
	public static void main(final String[] args)
		throws IOException
	{
		// Create application //
		new ApplicationController();
	}
}
