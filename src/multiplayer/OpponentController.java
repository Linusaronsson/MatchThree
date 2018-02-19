package multiplayer;

import controller.UIController;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import model.MatchThreeModel;
import model.Settings;
import view.ErrorDialog;

/**
 * ...
 */
public class OpponentController
	extends Thread
{
	private OpponentModel model = null;
	private UIController uiController = null;
	private DatagramSocket opponent;
	private DatagramPacket in;
	private byte[] inBuffer;
	private int port;
	
	/**
	 * ...
	 */
	public OpponentController(
		final UIController  uiController,
		final Settings      settings,
		final OpponentModel model,
		final int           port
	) {
		this.model = model;
		this.uiController = uiController;
		this.port = port;
		try {
			//Listen on port (2000)
			opponent = new DatagramSocket(port);
			//Setup receiving packet
			inBuffer = new byte[2048];
			in = new DatagramPacket(inBuffer, inBuffer.length);
		} catch (Exception e) {
			e.printStackTrace();
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
						model.setScore(s.getScore());
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
