package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * MatchThree game header.
 */
@SuppressWarnings("serial")
public class MatchThreeHeader
	extends JPanel
{
	/** Light green */
	private static final Color COLOR_BACKGROUND = new Color(0x76,0xff,0x7f);
	
	/** ... */
	private static final Color COLOR_BORDER = Color.GREEN;
	
	/** Score container. */
	private Container scoreView = new JPanel();
	
	/** Moves left container. */
	private Container movesLeftView = new JPanel();
	
	/**
	 * Constructor.
	 */
	public MatchThreeHeader() {
		// Set properties /
		scoreView.setBackground(COLOR_BACKGROUND);
		movesLeftView.setBackground(COLOR_BACKGROUND);
		
		// Set layout //
		setLayout(new BorderLayout());
		
		// Set border //
		setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createLineBorder(COLOR_BORDER.darker(), 2),
			BorderFactory.createLineBorder(COLOR_BORDER, 1)));
		
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
