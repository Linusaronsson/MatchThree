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
	
	private MultiplayerView1 multiplayerView = null;
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
		
		try {
			OpponentInfo info = Server.getOpponentInfo();
			multiplayerView = new MultiplayerView1(
				info.ip,
				info.port,
				info.board,
				GAME_SIZE,
				uiController
			);
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		} catch(IllegalStateException e) {
			System.err.println("getOpponentInfo() was called at a bad time");
			System.exit(1);
		}
		
		// Add event listeners //
		multiplayerView.addBackListener(event -> {
			// Go to main menu //
			goToMainMenu();
		});
		
		// Add view to parent //
		parent.add(multiplayerView);
	}
	
	private void goToMainMenu() {
		uiController.changeView(UIController.View.MAIN_MENU);
		multiplayerView.closeGame();
		Server.setInGame(false);
	}
}
