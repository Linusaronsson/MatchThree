package GameModes;

import Controller.BoardController;
import Model.BoardModel;
import View.BoardView;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;

public class Multiplayer extends JPanel {
	private BoardModel      modelPlayer      = null;
	private BoardView       viewPlayer       = null;
	private BoardController controller       = null;
	
	private BoardModel      modelOpponent     = null;
	private BoardView       viewOpponent      = null;
	
	public Multiplayer(String address, int port, final int GAME_SIZE) throws IOException {
		Socket socket = new Socket(InetAddress.getByName(address), port);
		
		// Create MVC context //
		BoardModel      modelPlayer    = new BoardModel(GAME_SIZE);
		BoardView       viewPlayer     = new BoardView(modelPlayer);
		BoardController controller     = new BoardController(modelPlayer, viewPlayer);
		
		BoardModel      modelOpponent  = new BoardModel(modelPlayer.getBoard(), GAME_SIZE);
		BoardView       viewOpponent   = new BoardView(modelOpponent);
		
		setLayout(new GridLayout(1,2, 15, 15));
		add(viewPlayer);
		add(viewOpponent);
		this.viewPlayer = viewPlayer;
	}
	
	public BoardView getView() { return viewPlayer; }
}
