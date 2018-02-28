package multiplayer;

import model.Jewel;

/**
 * @author Linus
 * 
 * Message sent between users over a DatagramSocket. (The entire board).
 */
@SuppressWarnings("serial")
public class UpdateBoard
	extends Message
{
	/** The board to send */
	Jewel[] board;
	
	/**
	 * Constructor
	 *
	 * @param board The board.
	 */
	public UpdateBoard(final Jewel[] board) {
		super(MessageType.ACCEPTED_GAME);
		this.board = board;
	}
	
	/**
	 * ...
	 *
	 * @return ...
	 */
	public Jewel[] getBoard() {
		return board;
	}
}
