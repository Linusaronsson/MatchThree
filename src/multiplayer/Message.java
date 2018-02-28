package multiplayer;

import java.io.Serializable;

/**
 * @author Linus
 * 
 * The base class of all messages sent between two players.
 */
public class Message
	implements Serializable
{
	/** Serial ID  */
	private static final long serialVersionUID = 123456789L;
	
	/** Identifies the type of message */
	private MessageType type;
	
	/**
	 * Enum listing the possible message types
	 */
	public enum MessageType
	{
		/** 
		 * This is sent when the game was accepted. The UpdateBoard 
		 * subclass will always have this type since the player that
		 * accepts the game will create the initial board and send it
		 * over to the opponent.
		 */
		ACCEPTED_GAME,
		
		/** 
		 * Used for ending a game before it was finished normally.
		 * (Empty payload)
		 */
		END_GAME,

		/** The default type for the subclass UpdateGameFinished 
		 *  (Empty payload).
		*/
		GAME_FINISHED,
		
		/** Used for requesting a game. (Empty payload) */
		REQUESTED_GAME,

		/** The the default type for the subclass Updatecell. */
		CELL_UPDATE,
		
		/** The default type for the subclass UpdateScore. */
		SCORE_UPDATE,
		
		/** The default type for the subclass UpdateMovesLeft. */
		MOVES_UPDATE
	}
	
	/**
	 * Constructor
	 *
	 * @param type The type of the message.
	 */
	public Message(final MessageType type) {
		this.type = type;
	}
	
	/**
	 * Converts the message enum type to string.
	 *
	 * @param type The type of message to convert to string.
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
	 * Getter method for the message type.
	 *
	 * @return The message type.
	 */
	public MessageType getType() {
		return type;
	}
	
	/**
	 * toString() override.
	 * @return The string representation of the current message object.
	 */
	@Override
	public String toString() {
		return "Message type: " + asString(type) + "\n";
	}
}
