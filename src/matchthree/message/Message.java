package matchthree.message;

import java.io.Serializable;

/**
 * Message base class.
 *
 * @author Linus Aronsson
 * @author Erik Selstam
 */
public class Message
	implements Serializable
{
	/** Serial identifier. */
	private static final long serialVersionUID = 123456789L;
	
	/** Message type. */
	private MessageType type;
	
	/**
	 * Possible message types.
	 *
	 * @author Linus Aronsson
	 */
	public enum MessageType
	{
		/** Game request was accepted. */
		ACCEPTED_GAME,
		
		/** Close game. */
		END_GAME,

		/** Game completed. */
		GAME_FINISHED,
		
		/** Game request. */
		REQUESTED_GAME,

		/** Cell update. */
		CELL_UPDATE,
		
		/** Score update. */
		SCORE_UPDATE,
		
		/** Move counter update. */
		MOVES_UPDATE
	}
	
	/**
	 * Constructor.
	 *
	 * @author Linus Aronsson
	 * @param type Message type.
	 */
	public Message(final MessageType type) {
		this.type = type;
	}
	
	/**
	 * Get the string representation of a message type.
	 *
	 * @author Linus Aronsson
	 * @author Erik Selstam
	 * @param type Message type.
	 * @return     The string representation.
	 */
	public String asString(final MessageType type) {
		switch (type) {
			case ACCEPTED_GAME:  return "ACCEPTED_GAME";
			case CELL_UPDATE:    return "CELL_UPDATE";
			case END_GAME:       return "END_GAME";
			case REQUESTED_GAME: return "REQUESTED_GAME";
			case SCORE_UPDATE:   return "SCORE_UPDATE";
			case MOVES_UPDATE:   return "MOVES_UPDATE";
			case GAME_FINISHED:  return "GAME_FINISHED";
			default: throw new IllegalStateException();
		}
	}
	
	/**
	 * Get the type of the message.
	 *
	 * @author Linus Aronsson
	 * @author Erik Selstam
	 * @return The message type.
	 */
	public MessageType getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return "Message type: " + asString(type) + "\n";
	}
}
