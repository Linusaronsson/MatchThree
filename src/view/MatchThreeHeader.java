package view;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import model.MatchThreeModel;
import util.Properties;

/**
 * MatchThree game header.
 */
@SuppressWarnings("serial")
public class MatchThreeHeader
	extends JPanel
{
	/** ... */
	private static final Color COLOR_BACKGROUND = Properties.getColorBackground();
	
	/** ... */
	private static final Color COLOR_PANEL = COLOR_BACKGROUND.brighter().brighter();
	
	/**
	 * Create `MatchThreeHeader`.
	 *
	 * @param matchThreeModel MatchThree model to use.
	 */
	public MatchThreeHeader(final MatchThreeModel matchThreeModel) {
		// Validate argument //
		if (matchThreeModel == null) {
			throw new IllegalStateException();
		}
		
		// Set layout //
		setLayout(new FlowLayout());
		setBackground(COLOR_PANEL);
		
		// Assemble view //
		add(new Score(matchThreeModel));
	}
}
