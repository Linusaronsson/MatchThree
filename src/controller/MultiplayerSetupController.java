package controller;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.net.DatagramSocket;
import java.net.InetAddress;
import multiplayer.Message;
import multiplayer.Server;
import view.ErrorDialog;
import view.MultiplayerMenuView;

/**
 * ...
 */
public class MultiplayerSetupController
{
	/** Default game size. */
	private static final int GAME_SIZE = 6;
	
	/** ... */
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
		final UIController uiController,
		final Container    parent
	) {
		// Validate arguments //
		if (uiController == null) {
			throw new NullPointerException();
		}
		if (parent == null) {
			throw new NullPointerException();
		}
		
		this.uiController = uiController;
		
		multiplayerMenuView = new MultiplayerMenuView();
		
		// Add event listeners //
		multiplayerMenuView.addConnectListener(event -> {
			// Handle connection //
			handleConnect(event);
		});
		
		// Add view to parent //
		parent.add(multiplayerMenuView);
	}
	
	/**
	 * Handle connect event.
	 *
	 * @param event Event object.
	 */
	private void handleConnect(final ActionEvent event) {
		DatagramSocket client = null;
		try {
			InetAddress ip = InetAddress.getByName(multiplayerMenuView.getIp());
			int port = Integer.parseInt(multiplayerMenuView.getPort());
			client = new DatagramSocket();
			Server.sendDatagram(
				new Message(Message.MessageType.REQUESTED_GAME),
				client,
				ip,
				port
			);
		} catch (Exception e1) {
			new ErrorDialog(
				"Error sending game request",
				"Error"
			);
			e1.printStackTrace();
		} finally {
			if (client != null) {
				client.close();
			}
		}
	}
}
