package GameModes;

import Controller.BoardController;
import Model.BoardModel;
import View.BoardView;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;

public class Singleplayer extends JPanel {
	private BoardModel      model      = null;
	private BoardView       view       = null;
	private BoardController controller = null;
	
	public Singleplayer(final int GAME_SIZE) {
		// Create MVC context //
		BoardModel      model      = new BoardModel(GAME_SIZE);
		BoardView       view       = new BoardView(model);
		BoardController controller = new BoardController(model, view);
		add(view);
		// Show GUI //
		// TODO: Move this to view?
		this.view = view;
	}
	
	public BoardView getView() { return view; }
}
