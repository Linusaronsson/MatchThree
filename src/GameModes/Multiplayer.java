package GameModes;

import Controller.MatchThreeController;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;
import Model.MatchThreeModel;
import View.MatchThreeUI;

@SuppressWarnings("serial")
public class Multiplayer
	extends JPanel
{
	private MatchThreeController controller    = null;
	private MatchThreeModel      modelOpponent = null;
	private MatchThreeModel      modelPlayer   = null;
	private MatchThreeUI         viewOpponent  = null;
	private MatchThreeUI         viewPlayer    = null;
	
	public Multiplayer(String address, int port, final int GAME_SIZE)
		throws IOException
	{
		Socket socket = new Socket(InetAddress.getByName(address), port);
		
		// Create MVC context //
		MatchThreeModel modelPlayer = new MatchThreeModel(GAME_SIZE);
		MatchThreeUI    viewPlayer  = new MatchThreeUI(modelPlayer);
		MatchThreeController controller  = new MatchThreeController(
			modelPlayer,
			viewPlayer
		);
		
		MatchThreeModel modelOpponent = new MatchThreeModel(
			modelPlayer.getBoard(),
			GAME_SIZE
		);
		MatchThreeUI viewOpponent = new MatchThreeUI(modelOpponent);
		
		setLayout(new GridLayout(1, 2, 15, 15));
		add(viewPlayer);
		add(viewOpponent);
		this.viewPlayer = viewPlayer;
	}
	
	public MatchThreeUI getView()
	{
		return viewPlayer;
	}
}
