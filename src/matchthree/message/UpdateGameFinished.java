package matchthree.message;

/**
 * Game closure message.
 *
 * @author Linus Aronsson
 * @author Erik Selstam
 */
@SuppressWarnings("serial")
public class UpdateGameFinished
	extends Message
{
	/**
	 * Constructor.
	 *
	 * @author Linus Aronsson
	 */
	public UpdateGameFinished() {
		super(MessageType.GAME_FINISHED);
	}
	
}
