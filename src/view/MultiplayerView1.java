package view;

import controller.MatchThreeController;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.InetAddress;
import javax.swing.JPanel;
import model.Jewel;
import model.MatchThreeModel;
import multiplayer.OpponentController;
import multiplayer.OpponentModel;
import multiplayer.PlayerModel;

/**
 * ...
 */
@SuppressWarnings("serial")
public class MultiplayerView1
	extends JPanel
{
	// Player
	private GridView playerGrid = null;
	private PlayerModel playerModel = null;
	private MatchThreeUI playerView = null;
	private MatchThreeController playerController = null;
	
	// Opponent
	private GridView opponentGrid = null;
	private OpponentModel opponentModel = null;
	private MatchThreeUI opponentView = null;
	private OpponentController opponentController = null;
	
	private ButtonPanel buttonPanel = new ButtonPanel();
	
	/**
	 * ...
	 *
	 * @param address  ...
	 * @param port     ...
	 * @param gameSize ...
	 * @throws IOException On file system access errors.
	 */
	public MultiplayerView1(
		final InetAddress ip,
		final int         port,
		final Jewel[]     board,
		final int         gameSize
	) throws IOException {
		// Create MVC context for the player //
		if (board == null) {
			playerModel = new PlayerModel(gameSize, ip, port);
			opponentModel = new OpponentModel(
				playerModel.getBoard(),
				gameSize
			);
			
			// Host sends the board to opponent //
			playerModel.sendBoard(3333);
		} else {
			playerModel = new PlayerModel(board, gameSize, ip, port);
			opponentModel = new OpponentModel(board, gameSize);
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
		setLayout(new BorderLayout());
		JPanel j = new JPanel(new GridLayout(1, 2, 100, 150));
		j.add(playerView);
		j.add(opponentView);
		add(j, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.EAST);
	}

	/**
	 * 
	 * @param listener
	 */
	public void addBackListener(final ActionListener listener) {
		buttonPanel.addBackListener(listener);
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
