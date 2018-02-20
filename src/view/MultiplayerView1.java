package view;

import controller.GridViewController;
import controller.UIController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.InetAddress;
import javax.swing.JPanel;
import model.Jewel;
import model.Settings;
import model.Settings.Style;
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
	/** Default port number. */
	private static final int PORT_NUMBER = Settings.getPortNumber();
	
	/** ... */
	private static final Color COLOR_OPPONENT_BACKGROUND =
		COLOR_BACKGROUND.brighter().brighter().brighter();
	
	/** ... */
	private ButtonPanel buttonPanel = new ButtonPanel();
	
	/** ... */
	private OpponentController opponentController = null;
	
	/** ... */
	private GridView opponentGrid = null;
	
	/** ... */
	private OpponentModel opponentModel = null;
	
	/** ... */
	private MatchThreeUI opponentView = null;
	
	/** ... */
	private GridViewController playerController = null;
	
	/** ... */
	private GridView playerGrid = null;
	
	/** ... */
	private PlayerModel playerModel = null;
	
	/** ... */
	private MatchThreeUI playerView = null;
	
	/**
	 * ...
	 *
	 * @param host         ...
	 * @param port         ...
	 * @param board        ...
	 * @param gameSize     ...
	 * @param uiController ...
	 * @param jewelVersion ...
	 * @throws IOException On file system access errors.
	 */
	public MultiplayerView1(
		final InetAddress  host,
		final int          port,
		final Jewel[]      board,
		final int          gameSize,
		final UIController uiController,
		final Settings     settings
	) throws IOException {
		// Create MVC context for the player //
		if (board == null) {
			playerModel = new PlayerModel(gameSize, host, port);
			opponentModel = new OpponentModel(
				playerModel.getBoard(),
				gameSize
			);
			
			// Host sends the board to opponent //
			playerModel.sendBoard(PORT_NUMBER);
		} else {
			playerModel   = new PlayerModel(board, gameSize, host, port);
			opponentModel = new OpponentModel(board, gameSize);
		}
		
		playerModel.setGameStarted(true);
		
		playerGrid = new GridView(playerModel, settings.getStyle());
		playerView  = new MatchThreeUI();
		playerController = new GridViewController(
			playerView,
			uiController,
			new Settings(),
			playerModel
		);
		
		opponentGrid = new GridView(opponentModel, settings.getStyle());
		opponentView = new MatchThreeUI();
		opponentController = new OpponentController(
			uiController,
			new Settings(),
			opponentModel,
			port
		);
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
	 * Add listener for back button.
	 *
	 * @param listener Event listener to use.
	 */
	public void addBackListener(final MouseListener listener) {
		buttonPanel.addBackListener(listener);
	}
	
	/**
	 * Add listener for back button.
	 *
	 * @param listener Event listener to use.
	 */
	public void addBackListener(final ActionListener listener) {
		buttonPanel.addBackListener(listener);
	}
	
	/**
	 * Close an ongoing multiplayer game. (Will close active sockets etc)
	 */
	public void closeGame() {
		opponentController.close();
	}
}
