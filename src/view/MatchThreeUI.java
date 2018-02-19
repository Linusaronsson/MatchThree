package view;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import model.MatchThreeModel;
import util.Properties;

/**
 * MatchThree game view.
 */
@SuppressWarnings("serial")
public class MatchThreeUI
	extends JPanel
{
	/** ... */
	private static final Color COLOR_BACKGROUND = Properties.getColorBackground();
	
	/**
	 * Constructor for `MatchThreeUI`.
	 *
	 * @param matchThreeModel MatchThree model to use.
	 * @param gridView        Grid view to use.
	 */
	// TODO: Call parent constructor?
	public MatchThreeUI(
		final MatchThreeModel matchThreeModel,
		final GridView        gridView
	) {
		// Validate argument //
		if (matchThreeModel == null) {
			throw new NullPointerException();
		}
		
		// Set properties //
		// TODO: Stopgap hack.
		setBackground(COLOR_BACKGROUND);
		
		// Set layout //
		setLayout(new BorderLayout());
		
		// Assemble view //
		MatchThreeHeader header = new MatchThreeHeader(matchThreeModel);
		add(header, BorderLayout.PAGE_START);
		add(gridView, BorderLayout.CENTER);
	}
}
