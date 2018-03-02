package multiplayer;

import controller.UIController;
import java.awt.Container;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import message.Message;
import message.UpdateCell;
import message.UpdateMovesLeft;
import message.UpdateScore;
import model.Settings;
import view.ErrorDialog;
import view.GameFinished;

/**
 * Opponent controller. Listens for network messages and updates model
 * accordingly.
 *
 * @author Linus
 */
public class OpponentController
	extends Thread
{
	/** Default packet size. */
	private static final int PACKET_SIZE = 2048;
	
	/** Opponent model. */
	private OpponentModel model = null;
	
	/** Reference to UI controller. */
	private UIController uiController = null;
	
	/** Opponent grid view. */
	private Container gridView = null;
	
	/** Socket for receiving messages. */
	private DatagramSocket opponent;
	
	/** `DatagramPacket` containing received data. */
	private DatagramPacket in;
	
	/** Receiving buffer. */
	private byte[] inBuffer;
	
	/** Listening port number. */
	private int port;
	
	/** Current score. */
	private int currentScore;
	
	/**
	 * Constructor.
	 *
	 * @param uiController UI controller to use.
	 * @param settings     Application settings.
	 * @param model        Model to use.
	 * @param port         Port number to listen on.
	 * @param gridView     View to use.
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
	 * Close socket. Allows a new game to be started.
	 */
	public void close() {
		this.interrupt();
		opponent.close();
	}
	
	// TODO: The use of the word "thereafter" is archaic. Is it better to simply
	//       use "accordingly"?
	/**
	 * Listen for messages and update model thereafter.
	 */
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
						currentScore = s.getScore();
						model.setScore(currentScore);
						break;
					case MOVES_UPDATE:
						UpdateMovesLeft u = (UpdateMovesLeft) m;
						int movesLeft = u.getMoves();
						model.setMovesLeft(movesLeft);
						break;
					case GAME_FINISHED:
						gridView.removeAll();
						gridView.add(new GameFinished(currentScore));
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
