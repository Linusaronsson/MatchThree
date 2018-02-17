package controller;

import java.awt.Container;
import model.MatchThreeModel;
import view.SingleplayerView;

/**
 * Controller for singleplayer game screen.
 */
public class SingleplayerViewController
{
	/** Default game size. */
	private static final int GAME_SIZE = 6;
	
	/** Reference to UI controller. */
	private UIController uiController = null;
	
	/**
	 * Create `SingleplayerViewController`.
	 *
	 * @param uiController UI controller to use for navigation.
	 * @param parent       Parent container view to use.
	 */
	public SingleplayerViewController(
		final UIController uiController,
		final Container    parent
	) {
		// Validate arguments //
		if (uiController == null) {
			throw new NullPointerException();
		}
		if (parent == null) {
			throw new NullPointerException();
		}
		
		this.uiController = uiController;
		
		// Create singleplayer view //
		MatchThreeModel matchThreeModel = new MatchThreeModel(GAME_SIZE);
		SingleplayerView singlePlayerView
			= new SingleplayerView(matchThreeModel);
		
		// Add event listeners //
		singlePlayerView.addBackListener(event -> {
			// Go to main menu //
			uiController.changeView(UIController.View.MAIN_MENU);
		});
		
		// Add view to parent //
		parent.add(singlePlayerView);
	}
}
