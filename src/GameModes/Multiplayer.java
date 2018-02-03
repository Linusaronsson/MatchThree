package GameModes;
import java.awt.GridLayout;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;

import Controller.BoardController;
import Model.BoardModel;
import View.BoardView;

public class Multiplayer extends JPanel {
	private BoardModel      modelPlayer      = null;
	private BoardView       viewPlayer       = null;
	private BoardController controller       = null;
	
	private BoardModel      modelOpponent      = null;
	private BoardView       viewOpponent      = null;
	
	public Multiplayer(final int GAME_SIZE) {
		try {
			// Create MVC context //
			BoardModel      modelPlayer    = new BoardModel(GAME_SIZE);
			BoardView       viewPlayer     = new BoardView(modelPlayer);
			BoardController controller     = new BoardController(modelPlayer, viewPlayer);
			
			BoardModel      modelOpponent  = new BoardModel(modelPlayer.getBoard(), GAME_SIZE);
			BoardView       viewOpponent   = new BoardView(modelOpponent);
			
			setLayout(new GridLayout(1,2, 15, 15));
			add(viewPlayer);
			add(viewOpponent);

		} catch (IOException
		      | LineUnavailableException
		      | UnsupportedAudioFileException e)
		{
			System.out.println(e);
			System.exit(1);
		}
	}
	
	//public BoardView getView() { return view; }
}
