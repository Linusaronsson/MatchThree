package controller;

import java.awt.Container;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import model.Jewel;
import model.MatchThreeModel;
import model.Settings;
import model.Settings.Style;
import view.ScoreMenuView;

/**
 * UI controller. Manages application navigation between views.
 */
public class UIController
{
	/** Default view. */
	private static final View DEFAULT_VIEW = View.MAIN_MENU;
	
	/** Current game settings. */
	private Settings settings = new Settings();
	
	/** Container to control. */
	private Container view = null;
	
	/** Reference to window controller for window updates. */
	private MainWindowController windowController = null;
	
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
		SINGLEPLAYER_GAME,
		
		/** Settings menu. */
		SETTINGS_MENU,
		
		/** Credits menu. */
		CREDITS_MENU,
		
		/** Quit program. */
		QUIT
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
		// Validate arguments //
		if (view == null) {
			throw new IllegalArgumentException("`view` must not be null");
		}
		if (windowController == null) {
			throw new IllegalArgumentException(
				"`windowController` must not be null"
			);
		}
		
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
				new MainMenuViewController(view, this, settings);
				break;
			case MULTIPLAYER_GAME:
				// TODO: This binding has temporarily been moved to
				//       `startMultiplayer`.
				throw new IllegalStateException("Old binding");
				//new MultiplayerViewController(view, this);
				//break;
			case MULTIPLAYER_MENU:
				new MultiplayerSetupController(view, this, settings);
				break;
			case SETTINGS_MENU:
				//new 
				break;
			case CREDITS_MENU:
				//new 
				break;
			case SCORE_MENU:
				new ScoreMenuView();
				break;
			case SINGLEPLAYER_GAME:
				new SingleplayerViewController(view, this, settings);
				break;
			case QUIT:
				windowController.closeWindow();
				break;
			default:
				throw new IllegalStateException("Unknown value for `View`");
		}
		
		// Update window //
		windowController.pack();
	}
	
	/**
	 * ...
	 *
	 * @param style ...
	 */
	public void setStyle(final Style style) {
		this.settings.setStyle(style);
	}
	
	/**
	 * Start a multiplayer game session.
	 *
	 * @param board ...
	 * @param host  ...
	 * @param port  ...
	 */
	// HACK: This function exists to transfer connection state from setup view
	//       to game view.
	public void startMultiplayer(
		final Jewel[]     board,
		final InetAddress host,
		final int         port
	) {
		// Remove previous view //
		view.removeAll();
		
		new MultiplayerViewController(view, this, settings, board, host, port);
		
		// Update window //
		windowController.pack();
	}
}
