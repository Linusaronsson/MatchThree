package matchthree.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.FlowLayout;
import matchthree.model.Settings;

/**
 * Multiplayer game screen.
 *
 * @author Linus Aronsson
 * @author Erik Selstam
 */
@SuppressWarnings("serial")
public class MultiplayerView
	extends Panel
{
	/** ... */
	private static final int GAP_HORIZONTAL = 30;
	
	/** ... */
	private static final int GAP_VERTICAL = 30;
	
	/** Default port number. */
	private static final int PORT_NUMBER = Settings.getPortNumber();
	
	/** Background color. */
	private static final Color COLOR_BACKGROUND = new Color(0x22, 0x22, 0x22);
	
	/** Back button container. */
	private ButtonPanel buttonPanel = new ButtonPanel();
	
	/** Player 1 container. */
	private Container player1View = new SubPanel();
	
	/** Player 2 container. */
	private Container player2View = new SubPanel();
	
	/**
	 * Constructor.
	 *
	 * @author Linus Aronsson
	 * @author Erik Selstam
	 */
	public MultiplayerView() {
		// Set layout //
		setLayout(new FlowLayout());
		
		// Set background //
		setBackground(COLOR_BACKGROUND);
		player1View.setBackground(COLOR_BACKGROUND);
		player2View.setBackground(COLOR_BACKGROUND);
		
		// Assemble view //
		add(player1View);
		add(player2View);
		add(buttonPanel);
	}
	
	/**
	 * Add action listener for back button.
	 *
	 * @author Linus Aronsson
	 * @param listener Event listener to use.
	 */
	public void addBackListener(final ActionListener listener) {
		buttonPanel.addBackListener(listener);
	}
	
	/**
	 * Add mouse listener for back button.
	 *
	 * @author Linus Aronsson
	 * @author Erik Selstam
	 * @param listener Event listener to use.
	 */
	public void addBackListener(final MouseListener listener) {
		buttonPanel.addBackListener(listener);
	}
	
	/**
	 * Get reference to back button.
	 *
	 * @author Linus Aronsson
	 * @author Erik Selstam
	 * @return Back button reference.
	 */
	public BackButton getBackButton() {
		return buttonPanel.getBackButton();
	}
	
	/**
	 * Get player 1 container.
	 *
	 * @author Erik Selstam
	 * @return Player 1 container.
	 */
	public Container getPlayer1() {
		return player1View;
	}
	
	/**
	 * Get player 2 container.
	 *
	 * @author Erik Selstam
	 * @return Player 2 container.
	 */
	public Container getPlayer2() {
		return player2View;
	}
}
