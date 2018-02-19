package controller;

import java.awt.Container;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import model.Settings;
import multiplayer.Message;
import multiplayer.Server;
import view.ErrorDialog;
import view.MultiplayerMenuView;

/**
 * ...
 */
public class MultiplayerSetupController
{
	/** Default port number. */
	private static final int PORT_NUMBER = 3333;
	
	/** View to control. */
	private MultiplayerMenuView multiplayerMenuView = null;
	
	/** Reference to UI controller. */
	private UIController uiController = null;
	
	/**
	 * Create `MultiplayerSetupController`.
	 *
	 * @param uiController ...
	 * @param parent       ...
	 */
	public MultiplayerSetupController(
		final Container    parent,
		final UIController uiController,
		final Settings     settings
	) {
		// Validate arguments //
		if (uiController == null) {
			throw new NullPointerException();
		}
		if (parent == null) {
			throw new NullPointerException();
		}
		
		this.uiController = uiController;
		
		// Create view //
		multiplayerMenuView = new MultiplayerMenuView();
		
		// Add event listeners //
		multiplayerMenuView.addConnectListener(event -> {
			// Initiate connection //
			connect();
		});
		
		// Add view to parent //
		parent.add(multiplayerMenuView);
		
		// Start server //
		// TODO: Close server when leaving multiplayer.
		new Server(uiController, PORT_NUMBER).start();
	}
	
	/**
	 * Handle connect event.
	 *
	 * @param event Event object.
	 */
	private void connect() {
		// Get value from fields //
		String hostText = multiplayerMenuView.getIp();
		String portText = multiplayerMenuView.getPort();
		
		// Parse values //
		InetAddress host = null;
		int         port = Integer.parseInt(portText);
		try {
			host = InetAddress.getByName(hostText);
		} catch (UnknownHostException exception) {
			new ErrorDialog("Unknown Host", "Could not resolve host");
			
			// Soft return //
			return;
		}
		
		// Connect //
		DatagramSocket socket = null;
		try {
			Message message = new Message(Message.MessageType.REQUESTED_GAME);
			socket = new DatagramSocket();
			Server.sendDatagram(message, socket, host, port);
		} catch (SocketException exception) {
			new ErrorDialog(
				"Network Error",
				"Socket error while sending game request"
			);
			exception.printStackTrace();
		} finally {
			if (socket != null) {
				socket.close();
			}
		}
		
		uiController.startMultiplayer(null, host, port);
	}
}
