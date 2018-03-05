package matchthree.message;

/**
 * Move counter update event.
 *
 * @author Linus Aronsson
 */
public class MovesLeftEvent
	extends LabelEvent
{
	/**
	 * Constructor.
	 *
	 * @author Linus Aronsson
	 * @param value Move counter to embed.
	 */
	public MovesLeftEvent(final int value) {
		super(value);
	}
}
