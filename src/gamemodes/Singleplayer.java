package gamemodes;

import controller.MatchThreeController;
import javax.swing.JPanel;
import model.MatchThreeModel;
import view.MatchThreeUI;
import view.Window;

/**
 * ...
 */
@SuppressWarnings("serial")
public class Singleplayer
	extends JPanel
{
	/** ... */
	private MatchThreeController matchThreeController = null;
	
	/** ... */
	private MatchThreeUI matchThreeUI = null;
	
	/**
	 * ...
	 *
	 * @param matchThreeModel ...
	 */
	public Singleplayer(final MatchThreeModel matchThreeModel) {
		// Create game //
		matchThreeUI = new MatchThreeUI(matchThreeModel);
		matchThreeController = new MatchThreeController(
			matchThreeModel,
			matchThreeUI
		);
		
		// Add view to panel //
		add(matchThreeUI);
		
		this.matchThreeController = matchThreeController;
		this.matchThreeUI         = matchThreeUI;
	}
	
	/**
	 * ...
	 *
	 * @return ...
	 */
	// TODO: Try to remove this method.
	public MatchThreeController getMatchThreeController() {
		return matchThreeController;
	}
	
	/**
	 * ...
	 *
	 * @return ...
	 */
	// TODO: Remove this method.
	public MatchThreeUI getView() {
		return matchThreeUI;
	}
	
	/**
	 * Set reference to parent window.
	 *
	 * @param window The parent window.
	 */
	public void setWindow(final Window window) {
		matchThreeController.setWindow(window);
	}
}
