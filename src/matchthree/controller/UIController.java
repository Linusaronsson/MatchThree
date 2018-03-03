package matchthree.controller;

import java.awt.Container;
import java.net.InetAddress;
import matchthree.model.Jewel;
import matchthree.model.MatchThreeModel;
import matchthree.model.Settings;
import matchthree.model.Settings.Style;
import matchthree.multiplayer.Server;

/**
 * UI controller. Manages application navigation between views.
 */
public class UIController
{
	/** Default view. */
	private static final View DEFAULT_VIEW = View.MAIN_MENU;
	
	/** Default port number. */
	private static final int PORT_NUMBER = 3333;
	
	/** Current game settings. */
	private Settings settings = new Settings();
	
	/** Container to control. */
	private Container view = null;
	
	/** Active view controller. */
	private ViewController viewController = null;
	
	/** Reference to window controller for window updates. */
	private MainWindowController windowController = null;
	
	/**
	 * View selection.
	 *
	 * @author Erik Selstam
	 * @author Erik Tran
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
		SETTINGS,
		
		/** Credits menu. */
		CREDITS,
		
		/** Quit program. */
		QUIT,
		
		/** Load game. */
		LOAD
	}
	
	/**
	 * Create `UIController`.
	 *
	 * @author Erik Selstam
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
		
		// Start server //
		// TODO: Avoid having server be active at all times.
		new Server(this, PORT_NUMBER).start();
	}
	
	/**
	 * Change the active view.
	 *
	 * @author Erik Selstam
	 * @author Erik Tran
	 * @param newView View to navigate to.
	 */
	public void changeView(final View newView) {
		// Remove previous view //
		view.removeAll();
		
		// Close view controller //
		if (viewController != null) {
			viewController.closeView();
		}
		
		// Add new view //
		MatchThreeModel matchThreeModel = null;
		switch (newView) {
			case MAIN_MENU:
				viewController =
					new MainMenuViewController(view, this, settings);
				break;
			case MULTIPLAYER_GAME:
				// TODO: This binding has temporarily been moved to
				//       `startMultiplayer`.
				throw new IllegalStateException("Old binding");
				//new MultiplayerViewController(view, this);
				//break;
			case MULTIPLAYER_MENU:
				viewController =
					new MultiplayerSetupController(view, this, settings);
				break;
			case SETTINGS:
				throw new IllegalStateException("Not implemented");
			case CREDITS:
				viewController = new CreditsViewController(view);
				break;
			case SCORE_MENU:
			    viewController =
			    	new ScoreMenuViewController(view, this, settings);
				break;
			case SINGLEPLAYER_GAME:
				viewController =
					new SingleplayerViewController(view, this, settings);
				break;
			case QUIT:
				windowController.closeWindow();
				break;
			case LOAD:
				throw new IllegalStateException("Not implemented");
			default:
				throw new IllegalStateException("Unknown value for `View`");
		}
		
		// Update window //
		windowController.pack();
	}
	
	/**
	 * ...
	 *
	 * @author Erik Tran
	 * @param style ...
	 */
	public void setStyle(final Style style) {
		this.settings.setStyle(style);
	}
	
	/**
	 * Start a multiplayer game session.
	 *
	 * @author Erik Selstam
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
		
		// Close view controller //
		if (viewController != null) {
			viewController.closeView();
		}
		
		// Create view controller //
		viewController = new MultiplayerViewController(
			view,
			this,
			settings,
			board,
			host,
			port
		);
		
		// Update window //
		windowController.pack();
	}
}
