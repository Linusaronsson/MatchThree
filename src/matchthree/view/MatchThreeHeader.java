package matchthree.view;

import java.awt.BorderLayout;
import java.awt.Container;

/**
 * MatchThree game header.
 *
 * @author Erik Selstam
 * @author Linus Aronsson
 */
@SuppressWarnings("serial")
public class MatchThreeHeader
	extends Panel
{
	/** Score container. */
	private Container scoreView = new SubPanel();
	
	/** Moves counter container. */
	private Container movesLeftView = new SubPanel();
	
	/**
	 * Constructor.
	 *
	 * @author Erik Selstam
	 * @author Linus Aronsson
	 */
	public MatchThreeHeader() {
		// Set layout //
		setLayout(new BorderLayout());
		
		// Assemble view //
		add(scoreView,     BorderLayout.WEST);
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
