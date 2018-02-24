package multiplayer;

import controller.UIController;

import java.awt.Container;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import model.Settings;
import view.ErrorDialog;
import view.GameFinished;

/**
 * ...
 */
public class OpponentController
	extends Thread
{
	/** Default packet size. */
	private static final int PACKET_SIZE = 2048;
	
	/** ... */
	private OpponentModel model = null;
	
	/** ... */
	private UIController uiController = null;
	
	/** .. */
	private Container gridView = null;
	
	/** ... */
	private DatagramSocket opponent;
	
	/** ... */
	private DatagramPacket in;
	
	/** ... */
	private byte[] inBuffer;
	
	/** ... */
	private int port;
	
	/** .. */
	private int current_score;
	
	/**
	 * ...
	 *
	 * @param uiController ...
	 * @param settings     ...
	 * @param model        ...
	 * @param port         ...
	 */
	public OpponentController(
		final UIController  uiController,
		final Settings      settings,
		final OpponentModel model,
		final int           port,
		final Container     gridView
	) {
		this.model        = model;
		this.port         = port;
		this.uiController = uiController;
		this.gridView     = gridView;
		
		try {
			//Listen on port (2000)
			opponent = new DatagramSocket(port);
			//Setup receiving packet
			inBuffer = new byte[PACKET_SIZE];
			in = new DatagramPacket(inBuffer, inBuffer.length);
		} catch (SocketException exception) {
			exception.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * ...
	 */
	public void close() {
		this.interrupt();
		opponent.close();
	}
	
	@Override
	public void run() {
		try {
			while (!interrupted()) {
				opponent.receive(in);
				ByteArrayInputStream byteInStream =
					new ByteArrayInputStream(inBuffer);
				ObjectInputStream inStream =
					new ObjectInputStream(byteInStream);
				Message m = (Message) inStream.readObject();
				switch (m.getType()) {
					case CELL_UPDATE:
						UpdateCell c = (UpdateCell) m;
						model.set(c.getX(), c.getY(), c.getJewelType());
						break;
					case SCORE_UPDATE:
						UpdateScore s = (UpdateScore) m;
						current_score = s.getScore();
						model.setScore(current_score);
						break;
					case MOVES_UPDATE:
						UpdateMovesLeft u = (UpdateMovesLeft) m;
						int moves_left = u.getMoves();
						model.setMovesLeft(moves_left);
						break;
					case GAME_FINISHED:
						gridView.removeAll();
						gridView.add(new GameFinished(current_score));
						gridView.repaint();
						gridView.revalidate();
						break;
					case END_GAME:
						uiController.changeView(UIController.View.MAIN_MENU);
						new ErrorDialog(
								"Opponent ended the game",
								"End of game"
							);
						Server.setInGame(false);
						close();
						break;
					default:
						throw new IllegalStateException();
				}
				System.out.println(
					"OpponentController Recieved: \n" + m.toString()
				);
				inStream.close();
			}
		} catch (SocketException e) {
			return;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
