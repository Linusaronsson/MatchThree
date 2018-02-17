package view;

import java.awt.FlowLayout;
import javax.swing.JPanel;
import model.MatchThreeModel;

/**
 * MatchThree game header.
 */
@SuppressWarnings("serial")
public class MatchThreeHeader
	extends JPanel
{
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
		
		// Assemble view //
		add(new Score(matchThreeModel));
	}
}
