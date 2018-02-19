package multiplayer;

/**
 * Message sent between users over a DatagramSocket. (The entire board).
 */
@SuppressWarnings("serial")
public class UpdateScore
	extends Message
{
	/** ... */
	int score;
	
	/**
	 * Constructor.
	 *
	 * @param score ...
	 */
	public UpdateScore(final int score) {
		super(MessageType.SCORE_UPDATE);
		this.score = score;
	}
	
	/**
	 * ...
	 *
	 * @return ...
	 */
	public int getScore() {
		return score;
	}
}
