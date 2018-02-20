package model;

/**
 * Settings storage.
 */
public class Settings
{
	/** Default game size. */
	private static final int GAME_SIZE = 6;
	
	/** Port number for network play. */
	private static final int PORT_NUMBER = 3333;
	
	/** ... */
	private Style style = Style.CLASSIC;
	/** Graphical style. */
	// TODO: Use enum instead.
	/**
	 *
	 */
	public enum Style {
		/** ... */
		CLASSIC,
		
		/** ... */
		STEEL
	}
	
	/**
	 * ...
	 */
	public Settings() { }
	
	/**
	 * 
	 * @param style
	 */
	public void setStyle(Style style) {
		this.style = style;
	}
	
	/**
	 * 
	 * @return
	 */
	public Style getStyle() {
		return style;
	}
	
	/**
	 * 
	 * @return
	 */
	public static int getGameSize() {
		return GAME_SIZE;
	}
	
	/**
	 * 
	 * @return
	 */
	public static int getPortNumber() {
		return PORT_NUMBER;
	}
}
