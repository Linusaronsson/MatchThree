package controller;

import java.awt.Container;
import view.MainMenuView;

/**
 * Controller for main menu view.
 */
public class MainMenuViewController
{
	/** Reference to UI controller. */
	private UIController uiController = null;
	
	/**
	 * Create `MainMenuViewController`.
	 *
	 * @param uiController Reference to UI controller for navigation.
	 * @param parent       Parent container view to use.
	 */
	public MainMenuViewController(
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
		
		// Create main menu view //
		MainMenuView mainMenuView = new MainMenuView();
		
		// Add event listeners //
		mainMenuView.addMultiplayerListener(event -> {
			// Go to multiplayer menu //
			uiController.changeView(UIController.View.MULTIPLAYER_MENU);
		});
		mainMenuView.addSingleplayerListener(event -> {
			// Go to singleplayer menu //
			uiController.changeView(UIController.View.SINGLEPLAYER_GAME);
		});
		
		// Add view to parent //
		parent.add(mainMenuView);
	}
}
