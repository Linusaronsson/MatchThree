package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import util.Properties;

/**
 * MatchThree game header.
 */
@SuppressWarnings("serial")
public class MatchThreeHeader
	extends JPanel
{
	/** ... */
	private static final Color COLOR_BACKGROUND =
		Properties.getColorBackground();
	
	/** ... */
	private static final Color COLOR_PANEL =
		COLOR_BACKGROUND.brighter().brighter();
	
	/** Score container. */
	private Container scoreView = new JPanel();
	
	/** Moves left container. */
	private Container movesLeftView = new JPanel();
	
	/**
	 * Constructor.
	 */
	public MatchThreeHeader() {
		// Set properties //
		setBackground(COLOR_PANEL);
		
		// Set layout //
		setLayout(new BorderLayout());
		
		// Set border //
		setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
		
		// Set background //
		scoreView.setBackground(Color.red);
		movesLeftView.setBackground(Color.red);
		
		// Assemble view //
		add(scoreView, BorderLayout.WEST);
		add(movesLeftView, BorderLayout.EAST);
	}
	
	/**
	 * Get score container.
	 *
	 * @return Score container.
	 */
	public Container getScore() {
		return scoreView;
	}
	
	/**
	 * Get MovesLeft container.
	 *
	 * @return MovesLeft container.
	 */
	public Container getMoves() {
		return movesLeftView;
	}
}
