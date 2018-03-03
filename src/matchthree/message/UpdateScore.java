package matchthree.message;

/**
 * Score update message.
 *
 * @author Linus Aronsson
 * @author Erik Selstam
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
	 * @author Linus Aronsson
	 * @param score Score to embed.
	 */
	public UpdateScore(final int score) {
		super(MessageType.SCORE_UPDATE);
		this.score = score;
	}
	
	/**
	 * Get score.
	 *
	 * @author Linus Aronsson
	 * @return The score.
	 */
	public int getScore() {
		return score;
	}
}
