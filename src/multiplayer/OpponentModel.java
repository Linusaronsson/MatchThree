package multiplayer;

import model.Jewel;
import model.MatchThreeModel;

/**
 * Extension of MatchThreeModel used for the player view in multiplayer mode
 * for sending datagrams to the opponent.
 *
 * @author Linus
 */
public class OpponentModel
	extends MatchThreeModel
{
	/**
	 * Constructor.
	 *
	 * @param board Board to use. If not provided a new one will be generated.
	 * @param width The width of the board in number of cells.
	 */
	public OpponentModel(final Jewel[] board, final int width) {
		super(board, width);
	}
	
	/**
	 * Set score.
	 *
	 * @param score New score.
	 */
	public void setScore(final int score) {
		setChanged();
		notifyObservers(new ScoreEvent(score));
	}
	
	/**
	 * Set the move counter.
	 *
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
