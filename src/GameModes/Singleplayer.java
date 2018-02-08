package GameModes;

import Controller.MatchThreeController;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;
import Model.MatchThreeModel;
import View.MatchThreeUI;

public class Singleplayer
	extends JPanel
{
	private MatchThreeController controller = null;
	private MatchThreeModel      model      = null;
	private MatchThreeUI         view       = null;
	
	public Singleplayer(final int GAME_SIZE)
	{
		// Create MVC context //
		MatchThreeModel      model      = new MatchThreeModel(GAME_SIZE);
		MatchThreeUI         view       = new MatchThreeUI(model);
		MatchThreeController controller = new MatchThreeController(model, view);
		add(view);
		
		// Show GUI //
		// TODO: Move this to view?
		this.view = view;
	}
	
	public MatchThreeUI getView()
	{
		return view;
	}
}
