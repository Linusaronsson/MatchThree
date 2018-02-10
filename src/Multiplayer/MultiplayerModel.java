package Multiplayer;

import Model.Coordinate;
import Model.Jewel;
import Model.MatchThreeModel;
import Model.MatchThreeModel.CellEvent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import Multiplayer.Message;

public class MultiplayerModel extends MatchThreeModel {
    private DatagramSocket client = null;
    private DatagramPacket out = null;
    private InetAddress ip = null;
    private int port;
    private boolean gameStarted = false;
   
	public MultiplayerModel(int width, InetAddress ip, int port) {
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
	
	public MultiplayerModel(Jewel[] board, int width, InetAddress ip, int port) {
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
	
	public void setGameStarted(boolean b) { gameStarted = b; }
	
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
		if(gameStarted)
			notifyOpponent(new UpdateCell(x, y, value));
	}
	
	public void sendBoard(int port) {
		Server.sendDatagram(new UpdateBoard(board), client, ip, port);
	}
	
	private void notifyOpponent(Message m) {
        Server.sendDatagram(m, client, ip, port);
	}
}
