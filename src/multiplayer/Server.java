package multiplayer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.swing.JOptionPane;

/**
 * ...
 */
public class Server
	extends Thread
{
	private SwapView window;
	private DatagramSocket server;
	private DatagramPacket in;
	
	byte[] inBuffer;
	private int port;
	
	private static boolean inGame = false;
	
	/**
	 * ...
	 */
	public Server(final SwapView window, final int port) {
		this.window = window;
		this.port = port;
		try {
			//Listen on port
			server = new DatagramSocket(port);
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
	public static void sendDatagram(
		final Message        m,
		final DatagramSocket socket,
		final InetAddress    ip,
		final int            port
	) {
		try {
			ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
			ObjectOutputStream outStream =
				new ObjectOutputStream(byteOutStream);
			outStream.writeObject(m);
			outStream.flush();
			byte[] data = byteOutStream.toByteArray();
			DatagramPacket out =
				new DatagramPacket(data, data.length, ip, port);
			socket.send(out);
			outStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * ...
	 */
	public static void setInGame(final boolean b) {
		inGame = b;
	}
	
	/**
	 * ...
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
							int response = JOptionPane.showConfirmDialog(
									window,
									"Multiplayer",
									"User requesting to play...",
									JOptionPane.INFORMATION_MESSAGE);
							if (response == 0)  {
								//start game as host.
								inGame = true;
								window.changeToMultiplayer(
									in.getAddress(),
									2000,
									true,
									null
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
							//start game as non host
							inGame = true;
							window.changeToMultiplayer(
								in.getAddress(),
								2000,
								false,
								((UpdateBoard) m).getBoard()
							);
							break;
						case END_GAME:
							inGame = false;
							break;
						default:
							throw new IllegalStateException();
					}
					System.out.println("Server Recieved: \n" + m.toString());
					inStream.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
