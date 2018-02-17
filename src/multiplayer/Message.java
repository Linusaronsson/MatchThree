package multiplayer;

import java.io.Serializable;

/**
 * Message sent between two users playing.
 */
public class Message
	implements Serializable
{
	private static final long serialVersionUID = 123456789L;
	private MessageType type;
	
	public enum MessageType
	{
		REQUESTED_GAME, //game request: (empty payload)
		ACCEPTED_GAME, //game accepted: notifies that game was accepted, and sends initial generated board.
		END_GAME,
		CELL_UPDATE,
		SCORE_UPDATE
	}
	
	/**
	 * ...
	 *
	 * @param type
	 */
	public Message(final MessageType type) {
		this.type = type;
	}
	
	/**
	 * ...
	 *
	 * @param type
	 */
	public String asString(final MessageType type) {
		switch (type) {
			case ACCEPTED_GAME:  return "ACCEPTED_GAME";
			case CELL_UPDATE:    return "CELL_UPDATE";
			case END_GAME:       return "END_GAME";
			case REQUESTED_GAME: return "REQUESTED_GAME";
			case SCORE_UPDATE:   return "SCORE_UPDATE";
			default: throw new IllegalStateException();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public MessageType getType() {
		return type;
	}
	
	/**
	 * 
	 */
	@Override
	public String toString() {
		return "Message type: " + asString(type) + "\n";
	}
}
