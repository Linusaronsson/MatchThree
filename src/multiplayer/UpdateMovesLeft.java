package multiplayer;

import multiplayer.Message.MessageType;

/**
 * Move counter message.
 *
 * @author Linus
 */
@SuppressWarnings("serial")
public class UpdateMovesLeft
	extends Message
{
	/** Moves counter. */
	int moves;
	
	/**
	 * Constructor.
	 *
	 * @param moves Moves counter to embed.
	 */
	public UpdateMovesLeft(final int moves) {
		super(MessageType.MOVES_UPDATE);
		this.moves = moves;
	}
	
	/**
	 * Get move counter.
	 *
	 * @return Moves counter.
	 */
	public int getMoves() {
		return moves;
	}
}
