package GameModes;

import Controller.MatchThreeController;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.JPanel;
import Model.MatchThreeModel;
import View.MatchThreeUI;

/**
 * ...
 */
@SuppressWarnings("serial")
public class Multiplayer
	extends JPanel
{
	/** ... */
	private MatchThreeController controller = null;
	
	/** ... */
	private MatchThreeModel modelOpponent = null;
	
	/** ... */
	private MatchThreeModel modelPlayer = null;
	
	/** ... */
	private MatchThreeUI viewOpponent = null;
	
	/** ... */
	private MatchThreeUI viewPlayer = null;
	
	/**
	 * ...
	 *
	 * @param address  ...
	 * @param port     ...
	 * @param gameSize ...
	 * @throws IOException On file-system access errors.
	 */
	public Multiplayer(
		final String address,
		final int port,
		final int gameSize
	) throws IOException {
		Socket socket = new Socket(InetAddress.getByName(address), port);
		
		// Create MVC context //
		modelPlayer = new MatchThreeModel(gameSize);
		viewPlayer  = new MatchThreeUI(modelPlayer);
		controller  = new MatchThreeController(modelPlayer, viewPlayer);
		
		modelOpponent = new MatchThreeModel(
			modelPlayer.getBoard(),
			gameSize
		);
		viewOpponent = new MatchThreeUI(modelOpponent);
		
		// TODO: Use constants for these numbers.
		setLayout(new GridLayout(1, 2, 15, 15));
		add(viewPlayer);
		add(viewOpponent);
	}
	
	/**
	 * ...
	 *
	 * @return The view in use.
	 */
	public MatchThreeUI getView() {
		return viewPlayer;
	}
}
