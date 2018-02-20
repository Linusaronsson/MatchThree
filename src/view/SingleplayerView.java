package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.LayoutManager;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import util.Properties;

/**
 * ...
 */
@SuppressWarnings("serial")
public class SingleplayerView
	extends JPanel
{
	/** ... */
	private static final Color COLOR_BACKGROUND =
			Properties.getColorBackground();
	
	/** ... */
	private static final Color COLOR_PANEL =
			COLOR_BACKGROUND.brighter().brighter();
	
	/** ... */
	private Container buttonPanel = new JPanel();
	
	/** ... */
	private Container gameView = new JPanel();
	
	/**
	 * Create `SingleplayerView`.
	 */
	public SingleplayerView() {
		// Set properties //
		// TODO: Stopgap hack.
		setBackground(COLOR_PANEL);
		buttonPanel.setBackground(COLOR_PANEL);
		gameView.setBackground(COLOR_PANEL);
		
		// Set layout //
		LayoutManager layout = new BoxLayout(this, BoxLayout.X_AXIS);
		setLayout(layout);
		
		// Assemble view //
		add(gameView);
		add(buttonPanel);
	}
	
	/**
	 * Get button panel container.
	 *
	 * @return Button panel container.
	 */
	public Container getButtonPanel() {
		return buttonPanel;
	}
	
	/**
	 * Get game container.
	 *
	 * @return Game container.
	 */
	public Container getGame() {
		return gameView;
	}
}
