package multiplayer;

import java.net.*;

import model.Coordinate;
import model.Jewel;
import model.MatchThreeModel;
import model.MatchThreeModel.MoveType;

/**
 * Extension of MatchThreeModel used for the player view to send datagrams to
 * opponent.
 */
public class OpponentModel
	extends MatchThreeModel
{	
	/**
	 * 
	 * @param board
	 * @param width
	 */
	public OpponentModel(final Jewel[] board, final int width) {
		super(board, width);
	}
	
	/**
	 * @param score
	 */
	public void setScore(final int score) {
		setChanged();
		notifyObservers(new ScoreEvent(score));
	}
	
}
