package matchthree.controller;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import matchthree.model.MatchThreeModel;
import matchthree.model.Settings;
import matchthree.model.Settings.Style;
import matchthree.util.AssetManager;
import matchthree.view.Button;
import matchthree.view.ButtonPanel;

/**
 * View controller for `ButtonPanel`.
 *
 * @author Erik Selstam
 */
public class ButtonPanelController
{
	/** Background color. */
	private static final Color COLOR_BACKGROUND = new Color(0x22, 0x22, 0x22);
	
	/** Reference to GridViewController controller. */
	private GridViewController gridViewController = null;
	
	/** Reference to UI controller. */
	private UIController uiController = null;
	
	/**
	 * Changes button state on hover.
	 *
	 * @author Erik Tran
	 * @author Erik Selstam
	 */
	final class HoverListener
		implements MouseListener
	{
		/** ... */
		private Button target = null;
		
		/**
		 * Constructor.
		 *
		 * @author Erik Selstam
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
	 * @author Erik Selstam
	 * @param parent             Parent view to use.
	 * @param uiController       UI controller to use.
	 * @param gridViewController `GridViewController`. Used for changing style.
	 * @param settings           Application settings.
	 * @param model              Game model to use.
	 */
	// TODO: Should this class have access to `MatchThreeModel`?
	public ButtonPanelController(
		final Container          parent,
		final UIController       uiController,
		final GridViewController gridViewController,
		final Settings           settings,
		final MatchThreeModel    model)
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
		buttonPanel.addBackListener(new HoverListener(buttonBack));
		buttonPanel.addBackListener(event -> {
			// Go to main menu //
			uiController.changeView(UIController.View.MAIN_MENU);
		});
		if (buttonPanel.getV1Button() != null
			&& buttonPanel.getV2Button() != null)
		{
			Button buttonV1   = buttonPanel.getV1Button();
			Button buttonV2   = buttonPanel.getV2Button();
			buttonPanel.addButtonV1Listener(new HoverListener(buttonV1));
			buttonPanel.addButtonV2Listener(new HoverListener(buttonV2));
			buttonPanel.addButtonV1Listener(event -> {
				// Set visual style //
				setStyle(Style.CLASSIC);
			});
			buttonPanel.addButtonV2Listener(event -> {
				// Set visual style //
				setStyle(Style.STEEL);
			});
		}
		
		// Add view to parent //
		parent.add(buttonPanel);
	}
	
	/**
	 * Set the visual style.
	 *
	 * @author Erik Tran
	 * @author Erik Selstam
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
