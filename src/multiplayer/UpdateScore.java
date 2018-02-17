package multiplayer;

import model.Jewel;

/**
 * Message sent between users over a DatagramSocket. (The entire board).
 */
@SuppressWarnings("serial")
public class UpdateScore
	extends Message
{
	int score;
	
	/**
	 * ...
	 */
	public UpdateScore(final int score) {
		super(MessageType.SCORE_UPDATE);
		this.score = score;
	}
	
	/**
	 * ...
	 */
	public int getScore() {
		return score;
	}
}
