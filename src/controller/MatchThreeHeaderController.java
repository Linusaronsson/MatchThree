package controller;

import java.awt.Container;
import model.MatchThreeModel;
import model.Settings;
import view.MatchThreeHeader;
import view.MatchThreeUI;
import view.MovesLeft;
import view.Score;

/**
 * ...
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
	public MatchThreeHeaderController(
		final MatchThreeUI    ui,
		final UIController    uiController,
		final Settings        settings,
		final MatchThreeModel matchThreeModel
	) {
		// Validate arguments //
		if (ui == null) {
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
		Container parent = ui.getHeader();
		
		// Add view to parent //
		parent.add(header);
	}
}
