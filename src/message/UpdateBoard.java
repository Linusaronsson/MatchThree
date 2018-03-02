package message;

import model.Jewel;

/**
 * Board update message.
 *
 * @author Linus
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
	 * @param board Board data to embed.
	 */
	public UpdateBoard(final Jewel[] board) {
		super(MessageType.ACCEPTED_GAME);
		this.board = board;
	}
	
	/**
	 * Get board data.
	 *
	 * @return The board data.
	 */
	public Jewel[] getBoard() {
		return board;
	}
}
