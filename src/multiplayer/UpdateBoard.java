package multiplayer;

import model.Jewel;

/**
 * Message sent between users over a DatagramSocket. (The entire board).
 */
@SuppressWarnings("serial")
public class UpdateBoard
	extends Message
{
	Jewel[] board;
	
	/**
	 * ...
	 */
	public UpdateBoard(final Jewel[] board) {
		super(MessageType.ACCEPTED_GAME);
		this.board = board;
	}
	
	/**
	 * ...
	 */
	public Jewel[] getBoard() {
		return board;
	}
}
