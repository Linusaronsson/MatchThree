package matchthree.view;

import java.awt.Color;

/**
 * Window main pane.
 *
 * @author Erik Selstam
 */
@SuppressWarnings("serial")
public class MainPane
	extends SubPanel
{
	/** Background color. */
	private static final Color COLOR_BACKGROUND = new Color(0x22, 0x22, 0x22);
	
	/**
	 * Constructor.
	 *
	 * @author Erik Selstam
	 */
	public MainPane() {
		super();
		
		// Set properties //
		setBackground(COLOR_BACKGROUND);
	}
}
