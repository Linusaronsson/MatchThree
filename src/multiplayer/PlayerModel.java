package multiplayer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import message.Message;
import message.UpdateBoard;
import message.UpdateCell;
import message.UpdateGameFinished;
import message.UpdateMovesLeft;
import message.UpdateScore;
import model.Coordinate;
import model.Jewel;
import model.MatchThreeModel;
import model.MatchThreeModel.MoveType;

/**
 * @author Linus
 *
 * Extension of MatchThreeModel used for the player view to send datagrams to
 * opponent.
 */
public class PlayerModel
	extends MatchThreeModel
{
	/** DatagramSocket that is used for sending datagram packets to
	 *  the opponent.
	 */
	private DatagramSocket client = null;
	
	/** The DatagramPacket to be sent. */
	private DatagramPacket out = null;
	
	/** IP of the opponent. */
	private InetAddress ip = null;
	
	/** Port of opponent. */
	private int port;
	
	/** Keeps track of wether the game is started or not. */
	private boolean gameStarted = false;
	
	/**
	 * Initializes the object and creates a new DatagramSocket
	 * for sending datagram packets.
	 *
	 * @param width Size of the board.
	 * @param host  IP of opponent.
	 * @param port  Port of opponent.
	 */
	public PlayerModel(
		final int         width,
		final InetAddress host,
		final int         port
	) {
		super(width);
		
		this.ip   = host;
		this.port = port;
		
		try {
			client = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Overloaded constructor for also initializing the model
	 * with a board.
	 *
	 * @param board Initial board.
	 * @param width Size of the board.
	 * @param host  IP of opponent.
	 * @param port  Port of opponent.
	 */
	public PlayerModel(
		final Jewel[]     board,
		final int         width,
		final InetAddress host,
		final int         port
	) {
		super(board, width);
		this.ip   = host;
		this.port = port;
		
		try {
			client = new DatagramSocket();
		} catch (SocketException exception) {
			exception.printStackTrace();
		}
	}
	
	/**
	 * Sets if the game was started.
	 *
	 * @param b Game started flag
	 */
	public void setGameStarted(final boolean b) {
		gameStarted = b;
	}
	
	/**
	 * Set the value of a cell. May leave the board in an inconsistent state.
	 * Also sends the updated cell to the opponent.
	 *
	 * This override also notifies the opponent of the change by sending
	 * a datagram packet.
	 *
	 * @param x     X-coordinate of the cell.
	 * @param y     Y-coordinate of the cell.
	 * @param value Value to set.
	 */
	@Override
	public void set(final int x, final int y, final Jewel value) {
		super.set(x, y, value);
		// Notify opponent //
		if (gameStarted) {
			notifyOpponent(new UpdateCell(x, y, value));
		}
	}
	
	/**
	 * Sets the moves left. Also notifies the opponent the the moves left
	 * value has changed.
	 */
	@Override
	public void setMovesLeft() {
		super.setMovesLeft();
		if (gameStarted) {
			notifyOpponent(new UpdateMovesLeft(movesLeft));
			if (movesLeft == 0) {
				notifyOpponent(new UpdateGameFinished());
			}
		}
	}
	
	/**
	 * Move a cell and clear any generated chains. Leaves the board in a
	 * consistent state. Also sends the new score to opponent.
	 *
	 * Also notifies the opponent of the new score.
	 *
	 * @param from Source coordinates.
	 * @param to   Destination coordinates.
	 * @return     Whether the move was successful, invalid or canceled.
	 */
	@Override
	public MoveType move(final Coordinate from, final Coordinate to) {
		MoveType type = super.move(from, to);
		if (gameStarted) {
			notifyOpponent(new UpdateScore(score));
		}
		return type;
	}
	
	/**
	 * Sends the entire board over. This is only done once at the start.
	 *
	 * @param port Port of opponent.
	 */
	public void sendBoard(final int port) {
		Server.sendDatagram(new UpdateBoard(board), client, ip, port);
	}
	
	/**
	 * General method for notifying the opponent with a specified
	 * message.
	 *
	 * @param message The message to be sent in a datagram packet.
	 */
	private void notifyOpponent(final Message message) {
		Server.sendDatagram(message, client, ip, port);
	}
}
