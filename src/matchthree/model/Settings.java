package matchthree.model;

/**
 * Settings storage.
 *
 * @author Erik Selstam
 * @author Erik Tran
 */
public class Settings
{
	/** Default game size. */
	private static final int GAME_SIZE = 6;
	
	/** Port number for network play. */
	private static final int PORT_NUMBER = 3333;
	
	/** ... */
	private Style style = Style.CLASSIC;
	
	/**
	 * Graphical style.
	 *
	 * @author Erik Tran
	 */
	public enum Style
	{
		/** ... */
		CLASSIC,
		
		/** ... */
		STEEL
	}
	
	/**
	 * ...
	 *
	 * @author Erik Selstam
	 */
	public Settings() { }
	
	/**
	 * ...
	 *
	 * @author Erik Tran
	 * @param style ...
	 */
	public void setStyle(final Style style) {
		this.style = style;
	}
	
	/**
	 * ...
	 *
	 * @author Erik Tran
	 * @return ...
	 */
	public Style getStyle() {
		return style;
	}
	
	/**
	 * ...
	 *
	 * @author Erik Tran
	 * @return ...
	 */
	public static int getGameSize() {
		return GAME_SIZE;
	}
	
	/**
	 * ...
	 *
	 * @author Erik Tran
	 * @return ...
	 */
	public static int getPortNumber() {
		return PORT_NUMBER;
	}
}
