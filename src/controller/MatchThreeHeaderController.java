package controller;

import java.awt.Container;
import model.MatchThreeModel;
import model.Settings;
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
		// TODO: Consider separate controller.
		Container matchThreeUI = new Score(matchThreeModel);
		
		// Add view to parent //
		parent.add(matchThreeUI);
	}
}
