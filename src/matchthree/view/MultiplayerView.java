package matchthree.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import matchthree.model.Settings;
import matchthree.util.Properties;

/**
 * Multiplayer game screen.
 *
 * @author Linus Aronsson
 * @author Erik Selstam
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
	
	/** Background color. */
	private static final Color COLOR_BACKGROUND =
		Properties.getColorBackground();
	
	/** Back button container. */
	private ButtonPanel backPanel = new ButtonPanel();
	
	/** Player 1 container. */
	private Container player1View = new JPanel();
	
	/** Player 2 container. */
	private Container player2View = new JPanel();
	
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
		add(backPanel);
		
		// Set border //
		setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createLineBorder(Color.WHITE, 2),
			BorderFactory.createLineBorder(Color.BLACK, 2)
		));
	}
	
	/**
	 * Add listener for back button.
	 *
	 * @author Linus Aronsson
	 * @param listener Event listener to use.
	 */
	public void addBackListener(final ActionListener listener) {
		backPanel.addBackListener(listener);
	}
	
	/**
	 * ...
	 *
	 * @author Linus Aronsson
	 * @param listener ...
	 * @param target   ...
	 */
	public void addHoverListener(
		final MouseListener listener,
		final Button        target)
	{
		target.addMouseListener(listener);
	}
	
	/**
	 * ...
	 *
	 * @author Linus Aronsson
	 * @return ...
	 */
	public Button getBackButton() {
		return backPanel.getBackButton();
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
