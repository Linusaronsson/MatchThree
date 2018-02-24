package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import javax.swing.JPanel;
import model.Settings;

/**
 * Multiplayer game screen.
 */
@SuppressWarnings("serial")
public class MultiplayerView
	extends JPanel
{
	/** ... */
	private static final int GAP_HORIZONTAL = 100;
	
	/** ... */
	private static final int GAP_VERTICAL = 150;
	
	/** Default port number. */
	private static final int PORT_NUMBER = Settings.getPortNumber();
	
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
		
		// Assemble view //
		add(panel, BorderLayout.CENTER);
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
