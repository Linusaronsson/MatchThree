package multiplayer;

import multiplayer.Message.MessageType;

/**
 * Message sent between users over a DatagramSocket. (The entire board).
 */
@SuppressWarnings("serial")
public class UpdateMovesLeft
	extends Message
{
	/** ... */
	int moves;
	
	/**
	 * Constructor.
	 *
	 * @param moves ...
	 */
	public UpdateMovesLeft(final int moves) {
		super(MessageType.MOVES_UPDATE);
		this.moves = moves;
	}
	
	/**
	 * ...
	 *
	 * @return ...
	 */
	public int getMoves() {
		return moves;
	}
}
