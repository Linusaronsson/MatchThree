package matchthree.message;

/**
 * Game completed event.
 *
 * @author Linus Aronsson
 */
public class GameFinishedEvent
	extends LabelEvent
{
	/**
	 * Constructor.
	 *
	 * @author Linus Aronsson
	 * @param value ...
	 */
	public GameFinishedEvent(final int value) {
		super(value);
	}
}
