package multiplayer;

import multiplayer.Message.MessageType;

/**
 * Message sent between users over a DatagramSocket. (Game finished).
 */
@SuppressWarnings("serial")
public class UpdateGameFinished
	extends Message
{
	/**
	 * 
	 */
	public UpdateGameFinished() {
		super(MessageType.GAME_FINISHED);
	}
	
}
