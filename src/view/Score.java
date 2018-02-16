package view;

import java.awt.Color;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;
import model.MatchThreeModel;

@SuppressWarnings({"deprecation", "serial"})
public class Score
	extends JLabel
	implements Observer
{
	private static final String FONT_NAME = "Helvetica Neue";
	private static final int FONT_SIZE = 20;
	
	private MatchThreeModel matchThreeModel = null;
	
	public Score(final MatchThreeModel matchThreeModel) {
		// Validate argument //
		if (matchThreeModel == null) {
			throw new IllegalStateException();
		}
		
		this.matchThreeModel = matchThreeModel;
		
		// Update state //
		update();
		
		// Add observer //
		matchThreeModel.addObserver(this);
		
		// Set properties //
		setFont(new Font(FONT_NAME, Font.BOLD, FONT_SIZE));
		setForeground(Color.WHITE);
	}
	
	/**
	 * Update score label.
	 */
	public void update() {
		int score = matchThreeModel.getScore();
		setText("Score: " + score);
	}
	
	/**
	 * ...
	 */
	@Override
	public void update(final Observable o, final Object arg) {
		// TODO: Verify correctness.
		update();
	}
}
