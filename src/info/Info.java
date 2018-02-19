package info;

public final class Info
{
	/** ... */
	private static final int GAME_SIZE = 6;
	
	/** Port number for network play. */
	private static final int PORT_NUMBER = 3333;
	
	/**
	 * Forbidden constructor.
	 */
	private Info() {
		throw new IllegalStateException();
	}
	
	/** ... */
	public static final int getGameSize() {
		return GAME_SIZE;
	}
	
	/** ... */
	public static final int getPortNumber() {
		return PORT_NUMBER;
	}
}
