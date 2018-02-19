package util;

/**
 * ...
 */
public final class Info
{
	/** Default game size. */
	private static final int GAME_SIZE = 6;
	
	/** Port number for network play. */
	private static final int PORT_NUMBER = 3333;
	
	/**
	 * Forbidden constructor.
	 */
	private Info() {
		throw new IllegalStateException();
	}
	
	/**
	 * ...
	 *
	 * @return ...
	 */
	public static int getGameSize() {
		return GAME_SIZE;
	}
	
	/**
	 * ...
	 *
	 * @return ...
	 */
	public static int getPortNumber() {
		return PORT_NUMBER;
	}
}
