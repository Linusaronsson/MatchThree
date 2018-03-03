package matchthree.message;

/**
 * Move counter message.
 *
 * @author Linus Aronsson
 * @author Erik Selstam
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
	 * @author Linus Aronsson
	 * @param moves Moves counter to embed.
	 */
	public UpdateMovesLeft(final int moves) {
		super(MessageType.MOVES_UPDATE);
		this.moves = moves;
	}
	
	/**
	 * Get move counter.
	 *
	 * @author Linus Aronsson
	 * @return Moves counter.
	 */
	public int getMoves() {
		return moves;
	}
}
