package matchthree.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

/**
 * ...
 *
 * @author Erik Selstam
 * @author Linus Aronsson
 * @author Erik Tran
 */
@SuppressWarnings("serial")
public class SingleplayerView
	extends Panel
{
	/** Background color. */
	private static final Color COLOR_BACKGROUND = new Color(0x22, 0x22, 0x22);
	
	/** Button panel. */
	private SubPanel buttonPanel = new SubPanel();
	
	/** Game view. */
	private SubPanel gameView = new SubPanel();
	
	/**
	 * Create `SingleplayerView`.
	 *
	 * @author Linus Aronsson
	 * @author Erik Selstam
	 * @author Erik Tran
	 */
	public SingleplayerView() {
		// Set properties //
		setBackground(COLOR_BACKGROUND);
		
		// Set sub-panel properties //
		buttonPanel.setPadding(40);
		
		// Assemble view //
		add(gameView,    BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.EAST);
	}
	
	/**
	 * Get button panel container.
	 *
	 * @author Erik Selstam
	 * @return Button panel container.
	 */
	public Container getButtonPanel() {
		return buttonPanel;
	}
	
	/**
	 * Get game container.
	 *
	 * @author Erik Selstam
	 * @return Game container.
	 */
	public Container getGame() {
		return gameView;
	}
}
