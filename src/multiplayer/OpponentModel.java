package multiplayer;

import model.Jewel;
import model.MatchThreeModel;

/**
 * @author Linus
 *
 * Extension of MatchThreeModel used for the player view in multiplayer mode
 * for sending datagrams to the opponent.
 */
public class OpponentModel
	extends MatchThreeModel
{
	/**
	 * Constructor.
	 *
	 * @param board The board to paint to the View. If this is null a new Board
	 * will be generated.
	 * @param width The size of the board.
	 */
	public OpponentModel(final Jewel[] board, final int width) {
		super(board, width);
	}
	
	/**
	 * Sets the score received from the opponent.
	 *
	 * @param score New score of the opponent board.
	 */
	public void setScore(final int score) {
		setChanged();
		notifyObservers(new ScoreEvent(score));
	}
	
	/**
	 * Sets the moves left for the opponent
	 *
	 * @param moves Moves left
	 */
	public void setMovesLeft(final int moves) {
		if (--moves_left == 0) {
			setChanged();
			notifyObservers(new String("remove"));
		}
		setChanged();
		notifyObservers(new MovesLeftEvent(moves));
	}
	
}
