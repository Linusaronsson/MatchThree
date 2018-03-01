package multiplayer;

import controller.UIController;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import model.Jewel;
import view.ConfirmDialog;
import view.ErrorDialog;

/**
 * @author Linus
 *
 * The server class is started as soon as the game is loaded. It is
 * responsible for listening for incoming game requests. Once a game
 * is requested, the server starts a multiplayer game but leaves
 * the responsibility to OpponentController for receiving the other
 * messages for the rest of the game.
 */
public class Server
	extends Thread
{
	/** Default packet size. */
	private static final int PACKET_SIZE = 2048;
	
	/** Default opponent port number. */
	private static final int PORT_NUMBER_OPPONENT = 2000;
	
	/** If it is in game, deny other game requests. (Not implemented) */
	private static boolean inGame = false;
		
	/** DatagramPacket for receiving requests. */
	private DatagramPacket in;
	
	/** DatagramPacket buffer. */
	private byte[] inBuffer;
	
	/** Server port (3333 by default). */
	private int port;
	
	/** DatagramSocket for sending/receiving datagram packets from/to
	 * the server
	 */
	private DatagramSocket server;
	
	/** UIController */
	private UIController ui;
	
	/**
	 * Constructs server and initializes the socket/packet.
	 *
	 * @param ui   UIController
	 * @param port Server port (3333)
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
		} catch (SocketException exception) {
			exception.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Sends a datagram to specified ip/port through specific socket.
	 * Used by many parts of the program.
	 *
	 * @param message Message to be sent
	 * @param socket  Socket to send it through
	 * @param host    IP
	 * @param port    Port
	 */
	public static void sendDatagram(
		final Message        message,
		final DatagramSocket socket,
		final InetAddress    host,
		final int            port
	) {
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
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	/**
	 * In game setter.
	 *
	 * @param b In game flag.
	 */
	public static void setInGame(final boolean b) {
		inGame = b;
	}
	
	/**
	 * Listens for game requests and initializes/declines the game
	 * depending on user input.
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
		} catch (ClassNotFoundException exception) {
			exception.printStackTrace();
			System.exit(1);
		} catch (IOException exception) {
			exception.printStackTrace();
			System.exit(1);
		}
	}
}
