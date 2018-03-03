package matchthree.util;

import java.awt.Color;

/**
 * ...
 *
 * @author Erik Tran
 */
public final class Properties
{
	/** ... */
	private static final String BUTTON_FONT_NAME = "Impact";
	
	/** ... */
	private static final int BUTTON_FONT_SIZE = 30;
	
	/** ... */
	private static final Color COLOR_BACKGROUND =
		Color.DARK_GRAY.darker().darker();
	
	/**
	 * Forbidden constructor.
	 *
	 * @author Erik Tran
	 */
	private Properties() {
		throw new IllegalStateException();
	}
	
	/**
	 * ...
	 *
	 * @author Erik Tran
	 * @return ...
	 */
	public static String getButtonFontName() {
		return BUTTON_FONT_NAME;
	}
	
	/**
	 * ...
	 *
	 * @author Erik Tran
	 * @return ...
	 */
	public static int getButtonFontSize() {
		return BUTTON_FONT_SIZE;
	}
	
	/**
	 * ...
	 *
	 * @author Erik Tran
	 * @return ...
	 */
	public static Color getColorBackground() {
		return COLOR_BACKGROUND;
	}
}
