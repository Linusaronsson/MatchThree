package controller;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import util.AssetManager;
import util.Properties;
import view.Button;
import view.MainMenuView;

/**
 * Controller for main menu view.
 */
public class MainMenuViewController
{
	/** Reference to UI controller. */
	private UIController uiController = null;
	
	/** ... */
	private static final String BUTTON_FONT_NAME = Properties.getButtonFontName();
	
	/** ... */
	private static final int BUTTON_FONT_SIZE = Properties.getButtonFontSize();
	
	/** ... */
	private static final Font HOVERFONT = new Font(BUTTON_FONT_NAME, Font.PLAIN, BUTTON_FONT_SIZE+5);
	
	/** ... */
	private static final Font FONT = new Font(BUTTON_FONT_NAME, Font.PLAIN, BUTTON_FONT_SIZE);
	
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
		
		/**
		 * 
		 */
		class HoverButtonListener
		implements MouseListener {
			
			/** ... */
			private Button button = null;
			
			public HoverButtonListener(final Button button) {
				// TODO Auto-generated constructor stub
				this.button = button;
			}

			@Override
			public void mouseClicked(final MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(final MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(final MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(final MouseEvent e) {
				// TODO Auto-generated method stub
				//button.setLabelForeground(Color.GREEN, 1f);
				AssetManager.playAudio(AssetManager.Audio.MOUSEOVER);
				button.setBorderPainted(true);
				button.setFont(HOVERFONT);
			}

			@Override
			public void mouseExited(final MouseEvent e) {
				// TODO Auto-generated method stub
				//button.setLabelForeground(Color.WHITE, 1f);
				button.setBorderPainted(false);
				button.setFont(FONT);
			}
		}
		
		for(Button button : mainMenuView.getButtons()) {
			button.addMouseListener(new HoverButtonListener(button));
		}
		
		// Add view to parent //
		parent.add(mainMenuView);
	}
}
