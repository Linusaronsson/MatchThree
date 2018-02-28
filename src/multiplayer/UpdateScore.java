package multiplayer;

/**
 * @author Linus
 * 
 * Message sent between users over a DatagramSocket. (Current score).
 */
@SuppressWarnings("serial")
public class UpdateScore
	extends Message
{
	/** Current score */
	int score;
	
	/**
	 * Constructor.
	 *
	 * @param score Score
	 */
	public UpdateScore(final int score) {
		super(MessageType.SCORE_UPDATE);
		this.score = score;
	}
	
	/**
	 * Score getter
	 *
	 * @return Score
	 */
	public int getScore() {
		return score;
	}
}
