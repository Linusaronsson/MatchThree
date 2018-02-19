package util;

import java.awt.Color;

public final class Properties
{
	/** ... */
	private static final Color COLOR_BACKGROUND = Color.DARK_GRAY.darker();
	
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
	
	/** ... */
	public static final Color getColorBackground() {
		return COLOR_BACKGROUND;
	}
	
	/** ... */
	public static final String getButtonFontName() {
		return BUTTON_FONT_NAME;
	}
	
	/** ... */
	public static final int getButtonFontSize() {
		return BUTTON_FONT_SIZE;
	}
}
