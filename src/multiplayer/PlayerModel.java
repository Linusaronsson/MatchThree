package multiplayer;

import java.net.*;

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
	private DatagramSocket client = null;
	private DatagramPacket out = null;
	private InetAddress ip = null;
	private int port;
	private boolean gameStarted = false;
	
	/**
	 * ...
	 *
	 * @param width
	 * @param ip
	 * @param port
	 */
	public PlayerModel(
		final int width,
		final InetAddress ip,
		final int port
	) {
		super(width);
		this.ip = ip;
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
	 * @param board
	 * @param width
	 * @param ip
	 * @param port
	 */
	public PlayerModel(
		final Jewel[] board,
		final int width,
		final InetAddress ip,
		final int port
	) {
		super(board, width);
		this.ip = ip;
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
	 */
	public void sendBoard(final int port) {
		Server.sendDatagram(new UpdateBoard(board), client, ip, port);
	}
	
	/**
	 * ...
	 */
	private void notifyOpponent(final Message m) {
		Server.sendDatagram(m, client, ip, port);
	}
}
