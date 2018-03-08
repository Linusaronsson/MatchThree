package matchthree.view;

import java.awt.Color;
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
	/** Foreground color. */
	private static final Color COLOR_FOREGROUND = Color.WHITE;
	
	/** Font. */
	private static final Font FONT = new Font("Impact", Font.PLAIN, 20);
	
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
		setFont(FONT);
		setForeground(COLOR_FOREGROUND);
	}
}
