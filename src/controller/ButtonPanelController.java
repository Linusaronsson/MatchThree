package controller;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import model.Settings;
import util.AssetManager.Audio;
import view.Button;
import view.ButtonPanel;

/**
 * ...
 */
public class ButtonPanelController
{
	/** Button panel view. */
	private ButtonPanel buttonPanel = new ButtonPanel();
	
	/** Reference to UI controller. */
	private UIController uiController = null;
	
	/**
	 * Handles button actions.
	 */
	final class ButtonListener
		implements ActionListener
	{
		/** ... */
		private Button target = null;
		
		/**
		 * Constructor.
		 *
		 * @param target Target to bind to.
		 */
		private ButtonListener(final Button target) {
			this.target = target;
		}
		
		@Override
		public void actionPerformed(final ActionEvent event) {
			// TODO: Do not use mnemonic for this!
			switch (target.getMnemonic()) {
				case 0:
					// Go to main menu //
					uiController.changeView(UIController.View.MAIN_MENU);
					break;
				case 1:
					// Change graphical style //
					uiController.setVersion(1);
					break;
				case 2:
					// Change graphical style //
					uiController.setVersion(2);
					break;
				default:
					throw new IllegalStateException(
						"Unknown value for button mnemonic"
					);
			}
		}
	}
	
	/**
	 * Plays audio on button press.
	 */
	final class ClickListener
		implements MouseListener
	{
		/** ... */
		private Button target = null;
		
		/**
		 * Constructor.
		 *
		 * @param target Target to bind to.
		 */
		private ClickListener(final Button target) {
			this.target = target;
		}
		
		@Override
		public void mouseClicked(final MouseEvent e) {
			// TODO: Do not use mnemonic for this!
			switch (target.getMnemonic()) {
				case 0:
				case 1:
				case 2:
					buttonPanel.playAudio(Audio.SELECT);
					break;
				default:
					throw new IllegalStateException(
						"Unknown value for button mnemonic"
					);
			}
		}
		
		@Override
		public void mouseEntered(final MouseEvent e) { }
		
		@Override
		public void mouseExited(final MouseEvent e) { }
		
		@Override
		public void mousePressed(final MouseEvent e) { }
		
		@Override
		public void mouseReleased(final MouseEvent e) { }
	}
	
	/**
	 * Changes button state on hover.
	 */
	final class HoverListener
		implements MouseListener
	{
		/** ... */
		private Button target = null;
		
		/**
		 * Constructor.
		 *
		 * @param target Target to bind to.
		 */
		private HoverListener(final Button target) {
			this.target = target;
		}
		
		@Override
		public void mouseClicked(final MouseEvent e) { }
		
		@Override
		public void mouseEntered(final MouseEvent e) {
			// Set opacity //
			target.setAlpha(0.5f);
			
			// Play audio //
			buttonPanel.playAudio(Audio.MOUSEOVER);
		}
		
		@Override
		public void mouseExited(final MouseEvent e) {
			// Set opacity //
			target.setAlpha(1.0f);
		}
		
		@Override
		public void mousePressed(final MouseEvent e) { }
		
		@Override
		public void mouseReleased(final MouseEvent e) { }
	}
	
	/**
	 * Constructor.
	 */
	public ButtonPanelController(
		final Container    parent,
		final UIController uiController,
		final Settings     settings
	) {
		// TODO: Validate arguments.
		
		this.uiController = uiController;
		
		// Create view //
		ButtonPanel buttonPanel = new ButtonPanel();
		
		// Register event listeners //
		Button buttonBack = buttonPanel.getBackButton();
		Button buttonV1   = buttonPanel.getV1Button();
		Button buttonV2   = buttonPanel.getV2Button();
		buttonPanel.addBackListener(new ButtonListener(buttonBack));
		buttonPanel.addBackListener(new ClickListener(buttonBack));
		buttonPanel.addBackListener(new HoverListener(buttonBack));
		buttonPanel.addButtonV1Listener(new ButtonListener(buttonV1));
		buttonPanel.addButtonV1Listener(new ClickListener(buttonV1));
		buttonPanel.addButtonV1Listener(new HoverListener(buttonV1));
		buttonPanel.addButtonV2Listener(new ButtonListener(buttonV2));
		buttonPanel.addButtonV2Listener(new ClickListener(buttonV2));
		buttonPanel.addButtonV2Listener(new HoverListener(buttonV2));
		
		// Add view to parent //
		parent.add(buttonPanel);
	}
}
