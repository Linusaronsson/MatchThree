package controller;

import java.awt.Container;
import model.Settings;
import util.Info;
import view.SingleplayerView;

/**
 * Controller for singleplayer game screen.
 */
public class SingleplayerViewController
{
	/** Default game size. */
	private static final int GAME_SIZE = Info.getGameSize();
	
	/** Reference to UI controller. */
	private UIController uiController = null;
	
	/**
	 * Create `SingleplayerViewController`.
	 *
	 * @param uiController UI controller to use for navigation.
	 * @param parent       Parent container view to use.
	 */
	public SingleplayerViewController(
		final Container    parent,
		final UIController uiController,
		final Settings     settings
	) {
		// Validate arguments //
		if (parent == null) {
			throw new NullPointerException();
		}
		if (uiController == null) {
			throw new NullPointerException();
		}
		
		this.uiController = uiController;
		
		// Create singleplayer view //
		// TODO: Add separate controller for button panel?
		SingleplayerView singleplayerView =
			new SingleplayerView();
		
		// Create MatchThree game //
		Container gameView = singleplayerView.getGame();
		MatchThreeUIController matchThreeUIController =
			new MatchThreeUIController(gameView, uiController, settings, null);
		
		// Create button panel //
		Container buttonPanel = singleplayerView.getButtonPanel();
		ButtonPanelController buttonPanelController =
			new ButtonPanelController(buttonPanel, uiController, settings);
		
		// Add view to parent //
		parent.add(singleplayerView);
	}
}
