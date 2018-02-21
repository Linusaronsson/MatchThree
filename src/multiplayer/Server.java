package multiplayer;

import controller.UIController;
import controller.UIController.View;
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
 * ...
 */
public class Server
	extends Thread
{
	/** Default packet size. */
	private static final int PACKET_SIZE = 2048;
	
	/** Default opponent port number. */
	private static final int PORT_NUMBER_OPPONENT = 2000;
	
	/** ... */
	private static boolean inGame = false;
	
	/** ... */
	private static OpponentInfo opponentInfo = null;
	
	/** ... */
	private DatagramPacket in;
	
	/** ... */
	private byte[] inBuffer;
	
	/** ... */
	private int port;
	
	/** ... */
	private DatagramSocket server;
	
	/** ... */
	private UIController ui;
	
	/**
	 * ...
	 */
	public class OpponentInfo
	{
		/** ... */
		public Jewel[] board;
		
		/** ... */
		public InetAddress ip;
		
		/** ... */
		public int port;
		
		/**
		 * ...
		 *
		 * @param ip    ...
		 * @param port  ...
		 * @param board ...
		 */
		public OpponentInfo(
			final InetAddress ip,
			final int         port,
			final Jewel[]     board
		) {
			this.board = board;
			this.ip    = ip;
			this.port  = port;
		}
	}
	
	/**
	 * ...
	 *
	 * @param ui   ...
	 * @param port ...
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
	 * ...
	 *
	 * @param message ...
	 * @param socket  ...
	 * @param host    ...
	 * @param port    ...
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
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
	}
	
	/**
	 * ...
	 *
	 * @return ...
	 */
	public static OpponentInfo getOpponentInfo() {
		OpponentInfo info = opponentInfo;
		opponentInfo = null;
		if (info == null) {
			throw new IllegalStateException();
		}
		return info;
	}
	
	/**
	 * ...
	 *
	 * @param b ...
	 */
	public static void setInGame(final boolean b) {
		inGame = b;
	}
	
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
								opponentInfo = new OpponentInfo(
									in.getAddress(),
									// TODO: Change to in.getPort() (problems
									//       for localhost).
									PORT_NUMBER_OPPONENT,
									null
								);
								ui.changeView(View.MULTIPLAYER_GAME);
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
							//start game as non host
							inGame = true;
							opponentInfo = new OpponentInfo(
								in.getAddress(),
								// TODO: Change to in.getPort().
								PORT_NUMBER_OPPONENT,
								((UpdateBoard) m).getBoard()
							);
							ui.changeView(View.MULTIPLAYER_GAME);
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
