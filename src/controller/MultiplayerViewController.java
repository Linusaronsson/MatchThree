package controller;

import java.awt.Container;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import model.Jewel;
import model.Settings;
import multiplayer.Message;
import multiplayer.OpponentController;
import multiplayer.OpponentModel;
import multiplayer.PlayerModel;
import multiplayer.Server;
import multiplayer.OpponentUIController;
import view.MultiplayerView;

/**
 * Controller for `MultiplayerView`.
 */
public class MultiplayerViewController
	implements ViewController
{
	/** Exit code - Network error. */
	private static final int EXIT_NETWORK = 3;
	
	/** Default game size. */
	private static final int GAME_SIZE = Settings.getGameSize();
	
	/** Default port number. */
	private static final int PORT_NUMBER = Settings.getPortNumber();
	
	/** ... */
	private InetAddress host = null;
	
	/** View to control. */
	private MultiplayerView multiplayerView = null;
	
	/** ... */
	private OpponentUIController opponentController = null;
	
	/** ... */
	private OpponentModel opponentModel = null;
	
	/** ... */
	private MatchThreeUIController playerController = null;
	
	/** ... */
	private PlayerModel playerModel = null;
	
	/** ... */
	private int port = 0;
	
	/** Reference to UI controller. */
	private UIController uiController = null;
	
	/**
	 * Create `MultiplayerViewController`.
	 *
	 * @param parent       Parent view to use.
	 * @param uiController UI controller to use.
	 * @param settings     ...
	 * @param board        ...
	 * @param host         ...
	 * @param port         ...
	 */
	public MultiplayerViewController(
		final Container    parent,
		final UIController uiController,
		final Settings     settings,
		final Jewel[]      board,
		final InetAddress  host,
		final int          port
	) {
		// Validate arguments //
		if (parent == null) {
			throw new IllegalArgumentException("`parent` must not be null");
		}
		if (uiController == null) {
			throw new IllegalArgumentException(
				"`uiController` must not be null"
			);
		}
		if (settings == null) {
			throw new IllegalArgumentException("`settings` must not be null");
		}
		// NOTE: `board` may be null.
		if (host == null) {
			throw new IllegalArgumentException("`host` must not be null");
		}
		if (port < 0) {
			throw new IllegalArgumentException("`port` must be positive");
		}
		
		this.uiController = uiController;
		this.host         = host;
		this.port         = port;
		
		// Create view //
		multiplayerView = new MultiplayerView();
		
		if (board == null) {
			playerModel = new PlayerModel(GAME_SIZE, host, port);
			Jewel[] playerBoard = playerModel.getBoard();
			opponentModel = new OpponentModel(playerBoard, GAME_SIZE);
			
			// Host sends the board to opponent //
			playerModel.sendBoard(PORT_NUMBER);
		} else {
			playerModel   = new PlayerModel(board, GAME_SIZE, host, port);
			opponentModel = new OpponentModel(board, GAME_SIZE);
		}
		
		playerModel.setGameStarted(true);
		
		//Create PlayerView
		playerController = new MatchThreeUIController(
			multiplayerView.getPlayer1(),
			uiController,
			settings,
			playerModel
		);
		
		
		//Create OpponentView
		new OpponentUIController(
			multiplayerView.getPlayer2(),
			uiController,
			settings,
			opponentModel,
			port
		);
		
		
		// Add event listeners //
		multiplayerView.addBackListener(event -> {
			// Go to main menu //
			uiController.changeView(UIController.View.MAIN_MENU);
		});
		
		// Add view to parent //
		parent.add(multiplayerView);
	}
	
	/**
	 * Close an ongoing multiplayer game (will close active sockets etc.).
	 */
	public void closeGame() {
		opponentController.close();
		Server.setInGame(false);
		try {
			Message message = new Message(Message.MessageType.END_GAME);
			DatagramSocket socket = new DatagramSocket();
			Server.sendDatagram(message, socket, host, port);
		} catch (SocketException exception) {
			exception.printStackTrace();
		}
	}
	
	@Override
	public void closeView() {
		// Close game session //
		closeGame();
	}
}
