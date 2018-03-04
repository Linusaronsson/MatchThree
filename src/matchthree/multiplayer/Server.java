package matchthree.multiplayer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import matchthree.controller.UIController;
import matchthree.message.Message;
import matchthree.message.UpdateBoard;
import matchthree.model.Jewel;
import matchthree.view.ConfirmDialog;
import matchthree.view.ErrorDialog;

/**
 * Listens for and responds to game requests over the network.
 *
 * @author Linus Aronsson
 * @author Erik Selstam
 */
public class Server
	extends Thread
{
	/** Default packet size. */
	private static final int PACKET_SIZE = 2048;
	
	// TODO: Fill in port purpose in comment bellow.
	/** Default port number for [...]. */
	private static final int PORT_NUMBER_OPPONENT = 2000;
	
	/** Whether currently in-game. */
	// TODO: Implement game-blocking functionality.
	private static boolean inGame = false;
		
	/** Received packet. */
	private DatagramPacket in;
	
	/** Receiving buffer. */
	private byte[] inBuffer;
	
	/** Server port number. */
	private int port;
	
	/** Network socket. */
	private DatagramSocket server;
	
	/** Reference to UI controller. */
	private UIController ui;
	
	/**
	 * Constructs server and initializes the socket/packet.
	 *
	 * @author Linus Aronsson
	 * @author Erik Selstam
	 * @param ui   UI controller to use.
	 * @param port Server port (coerced to 3333).
	 */
	public Server(final UIController ui, final int port) {
		this.ui   = ui;
		this.port = port;
		
		try {
			// Listen on port //
			server = new DatagramSocket(port);
			
			// Setup receiving packet //
			inBuffer = new byte[PACKET_SIZE];
			in = new DatagramPacket(inBuffer, inBuffer.length);
		} catch (final SocketException exception) {
			exception.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Send a datagram to specified host/port through provided socket.
	 *
	 * @author Linus Aronsson
	 * @author Erik Selstam
	 * @param message Message to send.
	 * @param socket  Socket to use.
	 * @param host    Target address.
	 * @param port    Target port number.
	 */
	public static void sendDatagram(
		final Message        message,
		final DatagramSocket socket,
		final InetAddress    host,
		final int            port)
	{
		try {
			ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
			ObjectOutputStream outStream =
				new ObjectOutputStream(byteOutStream);
			outStream.writeObject(message);
			outStream.flush();
			byte[] data = byteOutStream.toByteArray();
			int length = data.length;
			DatagramPacket out = new DatagramPacket(data, length, host, port);
			socket.send(out);
			outStream.close();
		} catch (final IOException exception) {
			exception.printStackTrace();
		}
	}
	
	/**
	 * Set in-game state. When set to `true`, further game requests will be
	 * blocked until set to `false` again.
	 *
	 * @author Erik Selstam
	 * @param b In-game state.
	 */
	public static void setInGame(final boolean b) {
		inGame = b;
	}
	
	/**
	 * Start listening for and reacting to game requests.
	 *
	 * @author Linus Aronsson
	 * @author Erik Selstam
	 */
	@Override
	public void run() {
		try {
			System.out.printf("Listening on port: %d\n", port);
			while (!interrupted()) {
				if (!inGame) {
					server.receive(in);
					ByteArrayInputStream byteInStream =
						new ByteArrayInputStream(inBuffer);
					ObjectInputStream inStream =
						new ObjectInputStream(byteInStream);
					Message m = (Message) inStream.readObject();
					switch (m.getType()) {
						case REQUESTED_GAME:
							ConfirmDialog dialog = new ConfirmDialog(
								"Multiplayer Request",
								"User <unknown> is requesting to play."
							);
							boolean response = dialog.getResponse();
							
							if (response) {
								//start game as host.
								inGame = true;
								InetAddress host = in.getAddress();
								ui.startMultiplayer(
									null,
									host,
									PORT_NUMBER_OPPONENT
								);
							} else {
								sendDatagram(
									new Message(Message.MessageType.END_GAME),
									server,
									in.getAddress(),
									port
								);
							}
							break;
						case ACCEPTED_GAME:
							UpdateBoard message = (UpdateBoard) m;
							//start game as non host
							inGame = true;
							Jewel[] board = message.getBoard();
							InetAddress host = in.getAddress();
							ui.startMultiplayer(
								board,
								host,
								PORT_NUMBER_OPPONENT
							);
							break;
						case END_GAME:
							inGame = false;
							new ErrorDialog("Game request denied", "Reponse");
							break;
						default:
							throw new IllegalStateException();
					}
					System.out.println("Server Recieved: \n" + m.toString());
					inStream.close();
				}
			}
		} catch (final ClassNotFoundException exception) {
			exception.printStackTrace();
			System.exit(1);
		} catch (final IOException exception) {
			exception.printStackTrace();
			System.exit(1);
		}
	}
}
