package multiplayer;

import java.awt.Container;

import controller.GridViewController;
import controller.MatchThreeHeaderController;
import controller.UIController;
import model.MatchThreeModel;
import model.Settings;
import view.GridView;
import view.MatchThreeUI;

/**
 * ...
 */
public class OpponentUIController
{
	
	/** Opponent Controller (for receiving data from opponent). */
	private OpponentController opponentController = null;
	
	/** Default game size. */
	private static final int GAME_SIZE = Settings.getGameSize();
	
	/**
	 * Constructor.
	 *
	 * @param parent          ...
	 * @param uiController    ...
	 * @param settings        ...
	 * @param matchThreeModel ...
	 */
	public OpponentUIController(
		final Container       parent,
		final UIController    uiController,
		final Settings        settings,
		final OpponentModel opponentModel,
		final int port
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
		if (settings == null) {
			throw new IllegalArgumentException("`settings` must not be null");
		}

		// Create view //
		MatchThreeUI matchThreeUI = new MatchThreeUI();
		
		// Create grid //
		Container gridView = matchThreeUI.getGrid();
		gridView.add(new GridView(opponentModel, settings.getStyle()));

		
		// Create header //
		Container headerView = matchThreeUI.getHeader();
		new MatchThreeHeaderController(
			headerView,
			uiController,
			settings,
			opponentModel
		);
		
		// Start opponent controller (UDP listener) //
		opponentController = new OpponentController(
				uiController,
				settings,
				opponentModel,
				port
		);
		
		// Start listening for data on UDP port //
		opponentController.start();
		
		// Add view to parent //
		parent.add(matchThreeUI);
	}
	
	public void close() {
		opponentController.close();
	}
	
}
