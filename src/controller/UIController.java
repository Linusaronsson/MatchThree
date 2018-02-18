package controller;

import java.awt.Container;
import model.MatchThreeModel;
import view.ErrorDialog;

/**
 * UI controller. Manages application navigation between views.
 */
public class UIController
{
	/** Default view. */
	private static final View DEFAULT_VIEW = View.MAIN_MENU;
	
	/** Default game size. */
	private static final int GAME_SIZE = 6;
	
	/** Container to control. */
	private Container view = null;
	
	/** Reference to window controller for window updates. */
	private MainWindowController windowController = null;
	
	private int jewelVersion = 1;
	
	/**
	 * View selection.
	 */
	public enum View
	{
		/** Main menu. */
		MAIN_MENU,
		
		/** Multiplayer game screen. */
		MULTIPLAYER_GAME,
		
		/** Multiplayer menu. */
		MULTIPLAYER_MENU,
		
		/** Score menu. */
		SCORE_MENU,
		
		/** Singleplayer game screen. */
		SINGLEPLAYER_GAME
	}
	
	/**
	 * Create `UIController`.
	 *
	 * @param view             Container view to control.
	 * @param windowController Window controller to use for updating window.
	 */
	public UIController(
		final Container            view,
		final MainWindowController windowController
	) {
		this.view             = view;
		this.windowController = windowController;
		
		// Set default view //
		changeView(DEFAULT_VIEW);
	}
	
	/**
	 * Change the active view.
	 *
	 * @param newView View to navigate to.
	 */
	public void changeView(final View newView) {
		// Remove previous view //
		view.removeAll();
		
		// Add new view //
		MatchThreeModel matchThreeModel = null;
		switch (newView) {
			case MAIN_MENU:
				new MainMenuViewController(this, view);
				break;
			case MULTIPLAYER_GAME:
				new MultiplayerViewController(this, view, jewelVersion);
				break;
			case MULTIPLAYER_MENU:
				new MultiplayerSetupController(this, view);
				break;
			case SCORE_MENU:
				new ErrorDialog(
					"Not Implemented",
					"This binding is not implemented"
				);
				break;
			case SINGLEPLAYER_GAME:
				new SingleplayerViewController(this, view, jewelVersion);
				break;
			default:
				throw new IllegalStateException();
		}
		
		// Update view //
		windowController.pack();
		windowController.centerWindow();
	}
	
	public void setVersion(final int jewelVersion) {
		this.jewelVersion = jewelVersion;
	}
}
