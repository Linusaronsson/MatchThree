package util;

import java.awt.Color;

/**
 * ...
 */
public final class Properties
{
	/** ... */
	private static final Color COLOR_BACKGROUND = Color.DARK_GRAY.darker().darker();
	
	/** ... */
	private static final String BUTTON_FONT_NAME = "Impact";
	
	/** ... */
	private static final int BUTTON_FONT_SIZE = 30;
	
	/**
	 * Forbidden constructor.
	 */
	private Properties() {
		throw new IllegalStateException();
	}
	
	/**
	 * ...
	 *
	 * @return ...
	 */
	public static Color getColorBackground() {
		return COLOR_BACKGROUND;
	}
	
	/**
	 * ...
	 *
	 * @return ...
	 */
	public static String getButtonFontName() {
		return BUTTON_FONT_NAME;
	}
	
	/**
	 * ...
	 *
	 * @return ...
	 */
	public static int getButtonFontSize() {
		return BUTTON_FONT_SIZE;
	}
}
