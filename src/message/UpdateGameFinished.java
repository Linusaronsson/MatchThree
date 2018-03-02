package matchthree.message;

/**
 * Game closure message.
 *
 * @author Linus
 */
@SuppressWarnings("serial")
public class UpdateGameFinished
	extends Message
{
	/**
	 * Constructor.
	 */
	public UpdateGameFinished() {
		super(MessageType.GAME_FINISHED);
	}
	
}
