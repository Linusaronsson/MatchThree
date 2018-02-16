package controller;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.MainMenuView;

public class MainMenuViewController
{
	private UIController uiController = null;
	
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
		mainMenuView.addMultiplayerListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent event) {
				uiController.changeView(UIController.View.MULTIPLAYER_MENU);
			}
		});
		mainMenuView.addSingleplayerListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent event) {
				uiController.changeView(UIController.View.SINGLEPLAYER_GAME);
			}
		});
		
		// Add view to parent //
		parent.add(mainMenuView);
	}
}
