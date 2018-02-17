package controller;

import java.awt.Container;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import multiplayer.Message;
import multiplayer.Server;
import multiplayer.Server.OpponentInfo;
import view.ErrorDialog;
import view.MultiplayerMenuView;
import view.MultiplayerView1;

public class MultiplayerViewController
{
	private static final int GAME_SIZE = 6;
	
	private UIController uiController = null;
	
	public MultiplayerViewController(
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
		OpponentInfo info = null;
		try {
			info = Server.getOpponentInfo();
			final MultiplayerView1 multiplayerView = new MultiplayerView1(
						info.ip,
						info.port,
						info.board,
						GAME_SIZE
					);
			
		// Add event listeners //
		multiplayerView.addBackListener(e -> {
			uiController.changeView(UIController.View.MAIN_MENU);
			multiplayerView.closeGame();
			Server.setInGame(false);
		});
		
		// Add view to parent //
		parent.add(multiplayerView);
		
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		} catch(IllegalStateException e) {
			System.err.println("getOpponentInfo() was called at a bad time");
			System.exit(1);
		}
		
	}
}
