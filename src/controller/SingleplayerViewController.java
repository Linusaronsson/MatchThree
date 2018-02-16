package controller;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.MatchThreeModel;
import view.SingleplayerView;

public class SingleplayerViewController
{
	private static final int GAME_SIZE = 6;
	
	private UIController uiController = null;
	
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
		singlePlayerView.addBackListener(e -> {
			uiController.changeView(UIController.View.MAIN_MENU);
		});
		
		// Add view to parent //
		parent.add(singlePlayerView);
	}
}
