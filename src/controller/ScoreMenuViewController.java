package matchthree.controller;

import java.awt.Container;
import matchthree.view.ScoreMenuView;
import matchthree.model.Settings;

/**
 * ...
 */
public class ScoreMenuViewController
    implements ViewController
{

    private UIController uiController = null;
	/**
	 * Constructor.
	 *
	 * @param parent Parent view to use.
	 */
    public ScoreMenuViewController(final Container parent,
				   final UIController uiController,
				   final Settings settings) {
		// Validate arguments //
		if (parent == null) {
			throw new IllegalArgumentException("`parent` must not be null");
		}
		
		if (uiController == null) {
			throw new IllegalArgumentException("`uiController` must not be null");
		}
		
		if (settings == null) {
			throw new IllegalArgumentException("`settings` must not be null");
		}

		this.uiController = uiController;

		// Create Score view //
		ScoreMenuView scoreMenuView = new ScoreMenuView();

		// Add event listeners //
		scoreMenuView.addRefreshListener(event -> {
			// Refresh scores //
			scoreMenuView.refresh();
		    });

		scoreMenuView.addMainMenuListener(event -> {
			// Return to main menu //
			uiController.changeView(UIController.View.MAIN_MENU); 
		    });
		
		// Add view to parent //
		parent.add(scoreMenuView);
	}
	
	@Override
	public void closeView() { }
}
