package matchthree.message;

/**
 * Score update event.
 *
 * @author Linus Aronsson
 */
public class ScoreEvent
	extends LabelEvent
{
	/**
	 * Constructor.
	 *
	 * @author Linus Aronsson
	 * @param value Score to embed.
	 */
	public ScoreEvent(final int value) {
		super(value);
	}
}
