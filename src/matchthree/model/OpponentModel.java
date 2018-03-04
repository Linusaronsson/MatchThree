package matchthree.model;

/**
 * Extension of MatchThreeModel used for the player view in multiplayer mode
 * for sending datagrams to the opponent.
 *
 * @author Linus Aronsson
 * @author Erik Selstam
 */
public class OpponentModel
	extends MatchThreeModel
{
	/**
	 * Constructor.
	 *
	 * @author Linus Aronsson
	 * @param board Board to use. If not provided a new one will be generated.
	 * @param width The width of the board in number of cells.
	 */
	public OpponentModel(final Jewel[] board, final int width) {
		super(board, width);
	}
	
	/**
	 * Set score.
	 *
	 * @author Linus Aronsson
	 * @param score New score.
	 */
	public void setScore(final int score) {
		setChanged();
		notifyObservers(new ScoreEvent(score));
	}
	
	/**
	 * Set the move counter.
	 *
	 * @author Linus Aronsson
	 * @param moves Number of moves left.
	 */
	public void setMovesLeft(final int moves) {
		if (--movesLeft == 0) {
			setChanged();
			notifyObservers(new String("remove"));
		}
		setChanged();
		notifyObservers(new MovesLeftEvent(moves));
	}
	
}
