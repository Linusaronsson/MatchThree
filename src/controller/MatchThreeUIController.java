package controller;

import java.awt.Container;
import model.MatchThreeModel;
import model.Settings;
import view.MatchThreeUI;

/**
 * ...
 */
public class MatchThreeUIController
{
	/** Default game size. */
	private static final int GAME_SIZE = 6;
	
	/**
	 * Constructor.
	 *
	 * @param parent          ...
	 * @param uiController    ...
	 * @param settings        ...
	 * @param matchThreeModel ...
	 */
	public MatchThreeUIController(
		final Container       parent,
		final UIController    uiController,
		final Settings        settings,
		final MatchThreeModel matchThreeModel
	) {
		// Validate arguments //
		if (parent == null) {
			throw new NullPointerException();
		}
		
		// Create or reuse MatchThree game model //
		MatchThreeModel model = null;
		if (matchThreeModel == null) {
			model = new MatchThreeModel(GAME_SIZE);
		} else {
			model = matchThreeModel;
		}
		
		// Create view //
		MatchThreeUI matchThreeUI = new MatchThreeUI();
		
		// Create grid //
		Container gridView = matchThreeUI.getGrid();
		new GridViewController(gridView, uiController, settings, model);
		
		// Create header //
		Container headerView = matchThreeUI.getHeader();
		new MatchThreeHeaderController(
			headerView,
			uiController,
			settings,
			model
		);
		
		// Add view to parent //
		parent.add(matchThreeUI);
	}
}
