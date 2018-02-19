package controller;

import java.awt.Container;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import info.Info;
import multiplayer.Message;
import multiplayer.Server;
import multiplayer.Server.OpponentInfo;
import view.MultiplayerView1;

/**
 * Controller for `MultiplayerView`.
 */
public class MultiplayerViewController
{
	/** Default game size. */
	private static final int GAME_SIZE = Info.getGameSize();
	
	/** View to control. */
	private MultiplayerView1 multiplayerView = null;
	
	/** Reference to UI controller. */
	private UIController uiController = null;
	
	/**
	 * Create `MultiplayerViewController`.
	 *
	 * @param uiController UI controller to use.
	 * @param parent       Parent view to use.
	 * @param jewelVersion 
	 */
	public MultiplayerViewController(
		final UIController uiController,
		final Container    parent,
		final int          jewelVersion
	) {
		// Validate arguments //
		if (uiController == null) {
			throw new NullPointerException();
		}
		if (parent == null) {
			throw new NullPointerException();
		}
		
		this.uiController = uiController;
		OpponentInfo info = null;
		try {
			info = Server.getOpponentInfo();
			multiplayerView = new MultiplayerView1(
				info.ip,
				info.port,
				info.board,
				GAME_SIZE,
				uiController,
				jewelVersion
			);
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IllegalStateException e) {
			System.err.println("getOpponentInfo() was called at a bad time");
			System.exit(1);
		}
		
		final InetAddress ip = info.ip;
		final int port = info.port;
		
		// Add event listeners //
		multiplayerView.addBackListener(event -> {
			// Go to main menu //
			goToMainMenu(ip, port);
		});
		
		// Add view to parent //
		parent.add(multiplayerView);
	}
	
	/**
	 * Navigate to main menu.
	 *
	 * @param ip   ...
	 * @param port ...
	 */
	private void goToMainMenu(final InetAddress ip, final int port) {
		uiController.changeView(UIController.View.MAIN_MENU);
		multiplayerView.closeGame();
		Server.setInGame(false);
		try {
			Server.sendDatagram(
				new Message(Message.MessageType.END_GAME),
				new DatagramSocket(),
				ip,
				port
			);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
}
