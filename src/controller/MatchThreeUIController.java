package controller;

import java.awt.Container;
import model.MatchThreeModel;
import model.Settings;
import view.MatchThreeHeader;
import view.MatchThreeUI;
import view.Score;

/**
 * ...
 */
public class MatchThreeUIController
{
	/** Default game size. */
	private static final int GAME_SIZE = Settings.getGameSize();
	
	/** Reference to GridViewController. */
	private GridViewController gridViewController = null;

	/** Reference to MatchThreeModel. */
	private MatchThreeModel model = null;
	
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
		// NOTE: `matchThreeModel` may be null.
		
		// Create or reuse MatchThree game model //
		MatchThreeModel model = null;
		if (matchThreeModel == null) {
			model = new MatchThreeModel(GAME_SIZE);
		} else {
			model = matchThreeModel;
		}
		
		this.model = model;
		
		// Create view //
		MatchThreeUI matchThreeUI = new MatchThreeUI(model);
		
		// Create grid //
		Container gridView = matchThreeUI.getGrid();
		gridViewController = new GridViewController(
			gridView,
			uiController,
			settings,
			model,
			matchThreeUI
		);
		
		// Create header //
		//Container headerView = matchThreeUI.getHeader();
		new MatchThreeHeaderController(
			matchThreeUI,
			uiController,
			settings,
			model
		);
		
		// Add view to parent //
		parent.add(matchThreeUI);
	}
	
	/**
	 * ...
	 *
	 * @return gridViewController reference
	 */
	public GridViewController getGridViewController() {
		return gridViewController;
	}

	public MatchThreeModel getModel() {
		// TODO Auto-generated method stub
		return model;
	}
}
