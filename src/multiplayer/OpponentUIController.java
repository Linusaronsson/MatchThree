package multiplayer;

import controller.MatchThreeHeaderController;
import controller.UIController;
import java.awt.Container;
import model.Settings;
import view.GridView;
import view.MatchThreeUI;

/**
 * @author Linus
 * 
 * Opponent UI controller. This is responsible for constructing the opponent
 * view and initializing the OpponentController that in turn listens for
 * datagram packets from the opponent.
 * 
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
	 * @param parent          The parent that holds the opponent view.
	 * @param uiController    UIController
	 * @param settings        Settings
	 * @param opponentModel   OpponentModel
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
		MatchThreeUI matchThreeUI = new MatchThreeUI(opponentModel);
		
		// Create grid //
		Container gridView = matchThreeUI.getGrid();
		gridView.add(new GridView(opponentModel, settings.getStyle()));

		// Create header //
		//Container headerView = matchThreeUI.getHeader();
		new MatchThreeHeaderController(
			matchThreeUI,
			uiController,
			settings,
			opponentModel
		);
		
		// Start opponent controller (UDP listener) //
		opponentController = new OpponentController(
				uiController,
				settings,
				opponentModel,
				port,
				gridView
		);
		
		// Start listening for data on UDP port //
		opponentController.start();
		
		// Add view to parent //
		parent.add(matchThreeUI);
	}
	
	/**
	 * Closes the opponent controller thread + datagram socket.
	 */
	public void close() {
		opponentController.close();
	}
	
}
