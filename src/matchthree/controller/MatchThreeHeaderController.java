package matchthree.controller;

import java.awt.Container;
import matchthree.model.MatchThreeModel;
import matchthree.model.Settings;
import matchthree.view.MatchThreeHeader;
import matchthree.view.MovesLeft;
import matchthree.view.Score;

/**
 * MatchThree header controller.
 */
public class MatchThreeHeaderController
{
	/**
	 * Constructor.
	 *
	 * @param parent          ...
	 * @param uiController    ...
	 * @param settings        ...
	 * @param matchThreeModel ...
	 */
	// TODO: Do not allow this controller to access `MatchThreeUI`.
	public MatchThreeHeaderController(
		final Container       parent,
		final UIController    uiController,
		final Settings        settings,
		final MatchThreeModel matchThreeModel
	) {
		// Validate arguments //
		if (parent == null) {
			throw new IllegalArgumentException("`parent` must not be null");
		}
		if (uiController == null) {
			throw new IllegalArgumentException(
				"`uiController` must not be null"
			);
		}
		if (settings == null) {
			throw new IllegalArgumentException("`settings` must not be null");
		}
		if (matchThreeModel == null) {
			throw new IllegalArgumentException(
				"`matchThreeModel` must not be null"
			);
		}
		
		// Create view //
		MatchThreeHeader header = new MatchThreeHeader();
		header.getScore().add(new Score(matchThreeModel));
		header.getMoves().add(new MovesLeft(matchThreeModel));
		
		// Add view to parent //
		parent.add(header);
	}
}
