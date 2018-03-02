package matchthree.controller;

import java.awt.Container;
import matchthree.model.Settings;
import matchthree.view.SingleplayerView;

/**
 * Controller for singleplayer game screen.
 */
public class SingleplayerViewController
	implements ViewController
{
	/** Default game size. */
	private static final int GAME_SIZE = Settings.getGameSize();
	
	/** Reference to UI controller. */
	private UIController uiController = null;
	
	/**
	 * Constructor.
	 *
	 * @param parent       Parent container view to use.
	 * @param uiController UI controller to use for navigation.
	 * @param settings     ...
	 */
	public SingleplayerViewController(
		final Container    parent,
		final UIController uiController,
		final Settings     settings
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
		
		this.uiController = uiController;
		
		// Create singleplayer view //
		SingleplayerView singleplayerView = new SingleplayerView();
		
		// Create MatchThree game //
		Container gameView = singleplayerView.getGame();
		MatchThreeUIController matchThreeUIController =
			new MatchThreeUIController(gameView, uiController, settings, null);
		
		// Create button panel //
		Container buttonPanel = singleplayerView.getButtonPanel();
		GridViewController gridViewController =
			matchThreeUIController.getGridViewController();
		new ButtonPanelController(
			buttonPanel,
			uiController,
			gridViewController,
			settings,
			matchThreeUIController.getModel()
		);
		
		// Add view to parent //
		parent.add(singleplayerView);
	}
	
	@Override
	public void closeView() { }
}
