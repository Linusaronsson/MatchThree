package multiplayer;

import multiplayer.Message.MessageType;

/**
 * @author Linus
 * 
 * Message sent between users over a DatagramSocket. (The moves left).
 */
@SuppressWarnings("serial")
public class UpdateMovesLeft
	extends Message
{
	/** Moves left */
	int moves;
	
	/**
	 * Constructor.
	 *
	 * @param moves Moves left
	 */
	public UpdateMovesLeft(final int moves) {
		super(MessageType.MOVES_UPDATE);
		this.moves = moves;
	}
	
	/**
	 * Moves left getter
	 *
	 * @return Moves left
	 */
	public int getMoves() {
		return moves;
	}
}
