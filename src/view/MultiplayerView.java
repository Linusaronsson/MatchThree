package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import javax.swing.JPanel;
import model.Settings;
import util.Properties;

/**
 * Multiplayer game screen.
 */
@SuppressWarnings("serial")
public class MultiplayerView
	extends JPanel
{
	/** ... */
	private static final int GAP_HORIZONTAL = 30;
	
	/** ... */
	private static final int GAP_VERTICAL = 30;
	
	/** Default port number. */
	private static final int PORT_NUMBER = Settings.getPortNumber();
	
	/** Background color */
	private static final Color COLOR_BACKGROUND = Properties.getColorBackground();
	
	/** Back button container. */
	private ButtonPanel backPanel = new ButtonPanel();
	
	/** Player 1 container. */
	private Container player1View = new JPanel();
	
	/** Player 2 container. */
	private Container player2View = new JPanel();
	
	/**
	 * Constructor.
	 */
	public MultiplayerView() {
		// Create split view //
		LayoutManager layout =
			new GridLayout(1, 2, GAP_HORIZONTAL, GAP_VERTICAL);
		JPanel panel = new JPanel(layout);
		
		// Assemble split view //
		panel.add(player1View);
		panel.add(player2View);
		
		// Set layout //
		setLayout(new BorderLayout());
		
		// Set background //
		setBackground(COLOR_BACKGROUND);
		player1View.setBackground(COLOR_BACKGROUND);
		player2View.setBackground(COLOR_BACKGROUND);
		panel.setBackground(COLOR_BACKGROUND);
		
		// Assemble view //
		add(panel, BorderLayout.CENTER);
		add(backPanel, BorderLayout.EAST);
	}
	
	/**
	 * Add listener for back button.
	 *
	 * @param listener Event listener to use.
	 */
	public void addBackListener(final ActionListener listener) {
		backPanel.addBackListener(listener);
	}
	
	/**
	 * ...
	 *
	 * @param listener ...
	 * @param target   ...
	 */
	public void addHoverListener(
		final MouseListener listener,
		final Button        target
	) {
		target.addMouseListener(listener);
	}
	
	/**
	 * ...
	 *
	 * @return ...
	 */
	public Button getBackButton() {
		return backPanel.getBackButton();
	}
	
	/**
	 * Get player 1 container.
	 *
	 * @return Player 1 container.
	 */
	public Container getPlayer1() {
		return player1View;
	}
	
	/**
	 * Get player 2 container.
	 *
	 * @return Player 2 container.
	 */
	public Container getPlayer2() {
		return player2View;
	}
}
