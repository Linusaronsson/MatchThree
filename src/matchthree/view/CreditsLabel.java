package matchthree.view;

import java.awt.Font;
import javax.swing.JLabel;

/**
 * Credit screen label.
 *
 * @author Erik Selstam
 */
@SuppressWarnings("serial")
public class CreditsLabel
	extends JLabel
{
	/** Font size. */
	private static final String FONT_NAME = "Impact";
	
	/** Font size. */
	private static final int FONT_SIZE = 20;
	
	/** Font style. */
	private static final int FONT_STYLE = Font.PLAIN;
	
	/**
	 * Constructor.
	 *
	 * @author Erik Selstam
	 * @param label Label text.
	 */
	public CreditsLabel(final String label) {
		super(label);
		
		// Validate argument //
		if (label == null) {
			throw new IllegalArgumentException("`label` must not be null");
		}
		
		// Set properties //
		Font font = new Font(FONT_NAME, FONT_STYLE, FONT_SIZE);
		setFont(font);
	}
}
