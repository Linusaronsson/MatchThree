package matchthree.controller;

import java.awt.Container;
import matchthree.view.ScoreMenuView;

/**
 * ...
 */
public class ScoreMenuViewController
    implements ViewController
{
	/**
	 * Constructor.
	 *
	 * @param parent Parent view to use.
	 */
	public ScoreMenuViewController(final Container parent) {
		// Validate arguments //
		if (parent == null) {
			throw new IllegalArgumentException("`parent` must not be null");
		}
		
		// Create Score view //
		ScoreMenuView scoreMenuView = new ScoreMenuView();
		
		// Add view to parent //
		parent.add(scoreMenuView);
	}
	
	@Override
	public void closeView() { }
}
