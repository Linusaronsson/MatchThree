package matchthree.controller;

import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Font;
import matchthree.model.Settings;
import matchthree.view.Button;
import matchthree.view.MainMenuView;

/**
 * Controller for main menu view.
 *
 * @author Erik Tran
 * @author Erik Selstam
 */
public class MainMenuViewController
	implements ViewController
{
	/** ... */
	private static final Font BUTTON_FONT = new Font("Impact", Font.PLAIN, 30);
	
	/** ... */
	private static final Font BUTTON_FONT_HOVER =
		new Font("Impact", Font.PLAIN, 36);
	
	/** Reference to UI controller. */
	private UIController uiController = null;
	
	/**
	 * ...
	 *
	 * @author Erik Tran
	 * @author Erik Selstam
	 */
	private final class HoverButtonListener
		implements MouseListener
	{
		/** ... */
		private Button button = null;
		
		/**
		 * ...
		 *
		 * @author Erik Tran
		 * @param button ...
		 */
		private HoverButtonListener(final Button button) {
			this.button = button;
		}
		
		@Override public void mouseClicked(final MouseEvent e) { }
		
		@Override public void mouseEntered(final MouseEvent e) {
			//AssetManager.playAudio(AssetManager.Audio.MOUSEOVER);
			button.setFont(BUTTON_FONT_HOVER);
		}
		
		@Override public void mouseExited(final MouseEvent e) {
			button.setFont(BUTTON_FONT);
		}
		
		@Override public void mousePressed(final MouseEvent e) { }
		
		@Override public void mouseReleased(final MouseEvent e) { }
	}
	
	/**
	 * Create `MainMenuViewController`.
	 *
	 * @author Erik Tran
	 * @author Erik Selstam
	 * @param parent       Parent container view to use.
	 * @param uiController Reference to UI controller for navigation.
	 * @param settings     ...
	 */
	public MainMenuViewController(
		final Container    parent,
		final UIController uiController,
		final Settings     settings)
	{
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
		mainMenuView.addLoadListener(event -> {
			// Load game //
			uiController.changeView(UIController.View.LOAD);
		});
		mainMenuView.addHighscoreListener(event -> {
			// Open highscore menu //
			uiController.changeView(UIController.View.SCORE_MENU);
		});
		mainMenuView.addSettingsListener(event -> {
			// Open settings menu //
			uiController.changeView(UIController.View.SETTINGS);
		});
		mainMenuView.addCreditsListener(event -> {
			// Show credits //
			uiController.changeView(UIController.View.CREDITS);
		});
		mainMenuView.addQuitListener(event -> {
			// Quit the program //
			uiController.changeView(UIController.View.QUIT);
		});
		
		for (final Button button : mainMenuView.getButtons()) {
			button.addMouseListener(new HoverButtonListener(button));
		}
		
		// Add view to parent //
		parent.add(mainMenuView);
	}
	
	@Override
	public void closeView() { }
}
