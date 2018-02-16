package multiplayer;

import java.net.*;
import model.Jewel;
import model.MatchThreeModel;

/**
 * Extension of MatchThreeModel used for the player view to send datagrams to
 * opponent.
 */
public class MultiplayerModel
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
	public MultiplayerModel(
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
	public MultiplayerModel(
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
