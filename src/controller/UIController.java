package controller;

import java.awt.Container;
import model.MatchThreeModel;
import multiplayer.Server;
import view.ErrorDialog;

public class UIController
{
	private static final View DEFAULT_VIEW = View.MAIN_MENU;
	
	private static final int GAME_SIZE = 6;
	
	private Container view = null;
	
	private MainWindowController windowController = null;
	
	/**
	 * ...
	 */
	public enum View
	{
		/** ... */
		MAIN_MENU,
		
		/** ... */
		MULTIPLAYER_GAME,
		
		/** ... */
		MULTIPLAYER_MENU,
		
		/** ... */
		SCORE_MENU,
		
		/** ... */
		SINGLEPLAYER_GAME
	}
	
	public UIController(
		final Container        view,
		final MainWindowController windowController
	) {
		this.view             = view;
		this.windowController = windowController;
		
		changeView(DEFAULT_VIEW);
	}
	
	/**
	 * ...
	 *
	 * @param state ...
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
				new MultiplayerViewController(this, view);
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
				new SingleplayerViewController(this, view);
				break;
			default:
				throw new IllegalStateException();
		}
		
		// Update view //
		//view.revalidate();
		//view.repaint();
		windowController.pack();
		//If a state change was issued during a multiplayer game,
		//then let the other user know that game ended.
		//if (viewState == WindowState.MULTIPLAYER_GAME) {
		//	Server.setInGame(false);
		//	mp.closeGame();
		
	}
}
