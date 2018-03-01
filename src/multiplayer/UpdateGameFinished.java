package multiplayer;

import multiplayer.Message.MessageType;

/**
 * @author Linus
 *
 * Message sent between users over a DatagramSocket. (Game finished).
 * This message has an empty payload. Only used for notifying that it
 * finished.
 */
@SuppressWarnings("serial")
public class UpdateGameFinished
	extends Message
{
	/**
	 * Constructor
	 */
	public UpdateGameFinished() {
		super(MessageType.GAME_FINISHED);
	}
	
}
