package message;

/**
 * Score update message.
 *
 * @author Linus
 */
@SuppressWarnings("serial")
public class UpdateScore
	extends Message
{
	/** Score. */
	int score;
	
	/**
	 * Constructor.
	 *
	 * @param score Score to embed.
	 */
	public UpdateScore(final int score) {
		super(MessageType.SCORE_UPDATE);
		this.score = score;
	}
	
	/**
	 * Get score.
	 *
	 * @return The score.
	 */
	public int getScore() {
		return score;
	}
}
