package matchthree.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * MatchThree game header.
 *
 * @author Erik Selstam
 * @author Linus Aronsson
 */
@SuppressWarnings("serial")
public class MatchThreeHeader
	extends JPanel
{
	/** Background color. */
	private static final Color COLOR_BACKGROUND = new Color(0x76, 0xFF, 0x7F);
	
	/** Border color. */
	private static final Color COLOR_BORDER = Color.GREEN;
	
	/** Score container. */
	private Container scoreView = new JPanel();
	
	/** Moves counter container. */
	private Container movesLeftView = new JPanel();
	
	/**
	 * Constructor.
	 *
	 * @author Erik Selstam
	 * @author Linus Aronsson
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
	 * @author Erik Selstam
	 * @return Score container.
	 */
	public Container getScore() {
		return scoreView;
	}
	
	/**
	 * Get move counter container.
	 *
	 * @author Linus Aronsson
	 * @return Move counter container.
	 */
	public Container getMoves() {
		return movesLeftView;
	}
}
