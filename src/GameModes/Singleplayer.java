package GameModes;

import Controller.MatchThreeController;
import Controller.WindowController;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;
import Model.MatchThreeModel;
import View.MatchThreeUI;
import View.SwapView;
import View.Window;

@SuppressWarnings("serial")
public class Singleplayer
	extends JPanel
{
	private MatchThreeUI matchThreeUI = null;
	
	public Singleplayer(final int GAME_SIZE)
	{
		// Create game //
		MatchThreeModel matchThreeModel = new MatchThreeModel(GAME_SIZE);
		MatchThreeUI    matchThreeUI    = new MatchThreeUI(matchThreeModel);
		MatchThreeController matchThreeController = new MatchThreeController(
			matchThreeModel,
			matchThreeUI
		);
		
		// Add view to panel //
		add(matchThreeUI);
		
		this.matchThreeUI = matchThreeUI;
	}
	
	// TODO: Remove this method.
	public MatchThreeUI getView()
	{
		return matchThreeUI;
	}
}
