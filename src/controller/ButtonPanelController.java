package controller;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.MatchThreeModel;
import model.Settings;
import model.Settings.Style;
import util.AssetManager;
import util.Properties;
import view.Button;
import view.ButtonPanel;

/**
 * ...
 */
public class ButtonPanelController
{
	/** ... */
	private static final Color COLOR_BACKGROUND =
		Properties.getColorBackground();
	
	/** Reference to GridViewController controller. */
	private GridViewController gridViewController = null;
	
	/** Reference to UI controller. */
	private UIController uiController = null;
	
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
			// Set mask //
			target.setMask(COLOR_BACKGROUND, 0.5f);
			
			// Play audio //
			//AssetManager.playAudio(AssetManager.Audio.MOUSEOVER);
		}
		
		@Override
		public void mouseExited(final MouseEvent e) {
			// Set mask //
			target.setMask(COLOR_BACKGROUND, 0f);
		}
		
		@Override
		public void mousePressed(final MouseEvent e) {
			AssetManager.playAudio(AssetManager.Audio.SELECT);
		}
		
		@Override
		public void mouseReleased(final MouseEvent e) { }
	}
	
	/**
	 * Constructor.
	 *
	 * @param parent             ...
	 * @param uiController       ...
	 * @param gridViewController ...
	 * @param settings           ...
	 */
	public ButtonPanelController(
		final Container          parent,
		final UIController       uiController,
		final GridViewController gridViewController,
		final Settings           settings,
		final MatchThreeModel    model
	) {
		// Validate arguments //
		if (parent == null) {
			throw new IllegalArgumentException("`parent` must not be null");
		}
		if (uiController == null) {
			throw new IllegalArgumentException(
				"`uiController` must not be null"
			);
		}
		if (gridViewController == null) {
			throw new IllegalArgumentException(
				"`gridViewController` must not be null"
			);
		}
		if (settings == null) {
			throw new IllegalArgumentException("`settings` must not be null");
		}
		
		this.gridViewController = gridViewController;
		this.uiController       = uiController;
		
		/** Button panel view. */
		ButtonPanel buttonPanel = new ButtonPanel(model);
		// Register event listeners //
		Button buttonBack = buttonPanel.getBackButton();
		Button buttonV1   = buttonPanel.getV1Button();
		Button buttonV2   = buttonPanel.getV2Button();
		buttonPanel.addBackListener(new HoverListener(buttonBack));
		buttonPanel.addButtonV1Listener(new HoverListener(buttonV1));
		buttonPanel.addButtonV2Listener(new HoverListener(buttonV2));
		buttonPanel.addBackListener(event -> {
			// Go to main menu //
			uiController.changeView(UIController.View.MAIN_MENU);
		});
		buttonPanel.addButtonV1Listener(event -> {
			// Set visual style //
			setStyle(Style.CLASSIC);
		});
		buttonPanel.addButtonV2Listener(event -> {
			// Set visual style //
			setStyle(Style.STEEL);
		});
		
		// Add view to parent //
		parent.add(buttonPanel);
	}
	
	/**
	 * Set the graphical style.
	 *
	 * @param style Visual style.
	 */
	private void setStyle(final Style style) {
		// Update settings //
		uiController.setStyle(style);
		
		// Change graphical style //
		// TODO: Have this occur automatically when setting is changes, to
		//       prevent inconsistent states.
		gridViewController.changeSprites(style);
	}
}
