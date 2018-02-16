package controller;

import java.awt.Container;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import multiplayer.Message;
import multiplayer.Server;
import view.ErrorDialog;
import view.MultiplayerMenuView;

public class MultiplayerSetupController
{
	private static final int GAME_SIZE = 6;
	
	private UIController uiController = null;
	
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
		MultiplayerMenuView multiplayerMenu = new MultiplayerMenuView();
		
		
		// Add event listeners //
		multiplayerMenu.addConnectListener(e -> {
			InetAddress ip = null;
			DatagramSocket client = null;
			try {
				ip = InetAddress.getByName(multiplayerMenu.getIp());
				int port = Integer.parseInt(multiplayerMenu.getPort());
				client = new DatagramSocket();
				Server.sendDatagram(
				new Message(Message.MessageType.REQUESTED_GAME),
				client,
				ip,
				port);
			} catch (Exception e1) {
				new ErrorDialog(
						"Error sending game request",
						"Error"
					);
				e1.printStackTrace();
			} finally {
				if(client != null)
					client.close();
			}
		});
			
		
		// Add view to parent //
		parent.add(multiplayerMenu);
	}
}
