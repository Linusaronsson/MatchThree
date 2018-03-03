package matchthree.message;

import matchthree.model.Jewel;

/**
 * Board update message.
 *
 * @author Linus Aronsson
 * @author Erik Selstam
 */
@SuppressWarnings("serial")
public class UpdateBoard
	extends Message
{
	/** Board data. */
	Jewel[] board;
	
	/**
	 * Constructor.
	 *
	 * @author Linus Aronsson
	 * @param board Board data to embed.
	 */
	public UpdateBoard(final Jewel[] board) {
		super(MessageType.ACCEPTED_GAME);
		this.board = board;
	}
	
	/**
	 * Get board data.
	 *
	 * @author Linus Aronsson
	 * @return The board data.
	 */
	public Jewel[] getBoard() {
		return board;
	}
}
