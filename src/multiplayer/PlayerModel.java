package multiplayer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import model.Coordinate;
import model.Jewel;
import model.MatchThreeModel;
import model.MatchThreeModel.MoveType;

/**
 * Extension of MatchThreeModel used for the player view to send datagrams to
 * opponent.
 */
public class PlayerModel
	extends MatchThreeModel
{
	/** ... */
	private DatagramSocket client = null;
	
	/** ... */
	private DatagramPacket out = null;
	
	/** ... */
	private InetAddress ip = null;
	
	/** ... */
	private int port;
	
	/** ... */
	private boolean gameStarted = false;
	
	/**
	 * ...
	 *
	 * @param width ...
	 * @param host  ...
	 * @param port  ...
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * ...
	 *
	 * @param board ...
	 * @param width ...
	 * @param host  ...
	 * @param port  ...
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
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
	}
	
	/**
	 * ...
	 *
	 * @param b ...
	 */
	public void setGameStarted(final boolean b) {
		gameStarted = b;
	}
	
	/**
	 * Set the value of a cell. May leave the board in an inconsistent state.
	 * Also sends the updated cell to the opponent.
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
	 * Move a cell and clear any generated chains. Leaves the board in a
	 * consistent state. Also sends the new score to opponent.
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
	 * ...
	 *
	 * @param port ...
	 */
	public void sendBoard(final int port) {
		Server.sendDatagram(new UpdateBoard(board), client, ip, port);
	}
	
	/**
	 * ...
	 *
	 * @param message ...
	 */
	private void notifyOpponent(final Message message) {
		Server.sendDatagram(message, client, ip, port);
	}
}
