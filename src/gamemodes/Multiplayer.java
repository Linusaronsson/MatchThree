package gamemodes;

import controller.MatchThreeController;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.JPanel;

import model.Jewel;
import model.MatchThreeModel;
import multiplayer.MultiplayerModel;
import multiplayer.OpponentController;
import view.GridView;
import view.MatchThreeUI;
import view.Window;

/**
 * ...
 */
@SuppressWarnings("serial")
public class Multiplayer
	extends JPanel
{
	// Player
	private GridView playerGrid = null;
	private MultiplayerModel playerModel = null;
	private MatchThreeUI playerView = null;
	private MatchThreeController playerController = null;
	
	// Opponent
	private GridView opponentGrid = null;
	private MatchThreeModel opponentModel = null;
	private MatchThreeUI opponentView = null;
	private OpponentController opponentController = null;
	
	/**
	 * ...
	 *
	 * @param address  ...
	 * @param port     ...
	 * @param gameSize ...
	 * @throws IOException On file system access errors.
	 */
	public Multiplayer(
		final InetAddress ip,
		final int         port,
		final boolean     isHost, //Could probably be removed
		final Jewel[]     board,
		final int         gameSize
	) throws IOException {
		// Create MVC context for the player //
		if (board == null) {
			playerModel = new MultiplayerModel(gameSize, ip, port);
			opponentModel = new MatchThreeModel(
				playerModel.getBoard(),
				gameSize
			);
			
			// Host sends the board to opponent //
			playerModel.sendBoard(3333);
		} else {
			playerModel = new MultiplayerModel(board, gameSize, ip, port);
			opponentModel = new MatchThreeModel(board, gameSize);
		}
		
		playerModel.setGameStarted(true);
		
		playerGrid = new GridView(playerModel);
		playerView  = new MatchThreeUI(playerModel, playerGrid);
		playerController = new MatchThreeController(
			playerModel,
			playerView,
			playerGrid
		);
		
		opponentGrid = new GridView(opponentModel);
		opponentView = new MatchThreeUI(opponentModel, opponentGrid);
		opponentController = new OpponentController(port, opponentModel);
		opponentController.start();
		
		// TODO: Use constants for these numbers.
		setLayout(new GridLayout(1, 2, 1000, 150));
		add(playerView);
		add(opponentView);
	}
	
	/**
	 * Set reference to parent window.
	 *
	 * @param window The parent window.
	 */
	public void setWindow(final Window window) {
		playerController.setWindow(window);
	}
	
	/**
	 * ...
	 *
	 * @return The view in use.
	 */
	public MatchThreeUI getView() {
		return playerView;
	}
	
	/**
	 * Close an ongoing multiplayer game. (Will close active sockets etc)
	 */
	public void closeGame() {
		opponentController.close();
	}
}
