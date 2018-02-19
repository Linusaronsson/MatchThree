package view;

import controller.MatchThreeController;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import model.MatchThreeModel;

/**
 * ...
 */
@SuppressWarnings("serial")
public class MultiplayerView2
	extends JPanel
{
	/** ... */
	private static final int GAME_SIZE = 6;
	
	/** ... */
	private static final int GAP_HORIZONTAL = 10;
	
	/** ... */
	private static final int GAP_VERTICAL = 10;
	
	/** ... */
	private GridView gridView = null;
	
	/** ... */
	private MatchThreeController matchThreeController = null;
	
	/** ... */
	private MatchThreeUI matchThreeUI = null;
	
	/**
	 * ...
	 *
	 * @param matchThreeModel ...
	 */
	public MultiplayerView2(
			final MatchThreeModel matchThreeModel,
			final int jewelVersion
	) {
		// Set layout //
		setLayout(new FlowLayout());
		
		// Set properties //
		setBackground(Color.BLACK);
		
		// Create game //
		gridView = new GridView(matchThreeModel, jewelVersion);
		matchThreeUI = new MatchThreeUI(matchThreeModel, gridView);
		matchThreeController = new MatchThreeController(
			matchThreeModel,
			matchThreeUI,
			gridView
		);
		
		// Create model //
		new MatchThreeModel(GAME_SIZE);
		
		// Set layout //
		FlowLayout layout = new FlowLayout(
			FlowLayout.CENTER,
			GAP_HORIZONTAL,
			GAP_VERTICAL
		);
		setLayout(layout);
		
		// Set properties //
		setBackground(Color.BLACK);
		setBorder(BorderFactory.createLineBorder(Color.WHITE));
		
		// Assemble view //
		add(matchThreeUI);
		add(new ButtonPanel());
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
