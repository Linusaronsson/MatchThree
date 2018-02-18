package controller;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import model.MatchThreeModel;
import view.Button;
import view.GridView.Audio;
import view.SingleplayerView;

/**
 * Controller for singleplayer game screen.
 */
public class SingleplayerViewController
{
	/** Default game size. */
	private static final int GAME_SIZE = 6;
	
	/** Reference to UI controller. */
	private UIController uiController = null;
	
	/**
	 * Create `SingleplayerViewController`.
	 *
	 * @param uiController UI controller to use for navigation.
	 * @param parent       Parent container view to use.
	 */
	public SingleplayerViewController(
		final UIController uiController,
		final Container    parent,
		final int jewelVersion
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
			= new SingleplayerView(matchThreeModel, jewelVersion);
		
		/**
		 * 
		 * Class listener inside constructor because singlePlayerView local variable
		 * @author Erik
		 *
		 */
		final class ButtonHoverListener
			implements MouseListener
		{
		/** ... */
		private Button target = null;
		
		/**
		 * ...
		 *
		 * @param button ...
		 */
		private ButtonHoverListener(final Button target) {
			this.target = target;
		}
		
		@Override
		public void mouseClicked(final MouseEvent e) {
			switch(target.getMnemonic()) {
			case 0:
				singlePlayerView.playAudio(Audio.SELECT);
				// Go back to main menu //
				uiController.changeView(UIController.View.MAIN_MENU);
				break;
			case 1:
				singlePlayerView.playAudio(Audio.SELECT);
				singlePlayerView.changeSprites(1);
				uiController.setVersion(1);
				break;
			case 2:
				singlePlayerView.playAudio(Audio.SELECT);
				singlePlayerView.changeSprites(2);
				uiController.setVersion(2);
				break;
			default:
				break;
		}
		}
		
		@Override
		public void mouseEntered(final MouseEvent e) {
			singlePlayerView.playAudio(Audio.MOUSEOVER);
			target.setAlpha(0.5f);
		}
		
		@Override
		public void mouseExited(final MouseEvent e) {
			target.setAlpha(1.0f);
		}
		
		@Override
		public void mousePressed(final MouseEvent e) { }
		
		@Override
		public void mouseReleased(final MouseEvent e) { }
	}
		
		// Add event listeners //
		singlePlayerView.addBackListener(
				new ButtonHoverListener(singlePlayerView.getBackButton()));
		singlePlayerView.addButtonV1Listener(
				new ButtonHoverListener(singlePlayerView.getV1Button()));
		singlePlayerView.addButtonV2Listener(
				new ButtonHoverListener(singlePlayerView.getV2Button()));
		
		// Add view to parent //
		parent.add(singlePlayerView);
	}
}
