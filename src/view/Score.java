package view;

import java.awt.Color;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;
import model.MatchThreeModel;

/**
 * Score counter.
 */
@SuppressWarnings({"serial"})
public class Score
	extends JLabel
	implements Observer
{
	/** Font name. */
	private static final String FONT_NAME = "Helvetica Neue";
	
	/** Font size. */
	private static final int FONT_SIZE = 20;
	
	/** Reference to MatchThree model. */
	// TODO: Remove this?
	private MatchThreeModel matchThreeModel = null;
	
	/**
	 * Create `Score`.
	 *
	 * @param matchThreeModel MatchThree model to use.
	 */
	public Score(final MatchThreeModel matchThreeModel) {
		// Validate argument //
		if (matchThreeModel == null) {
			throw new IllegalStateException();
		}
		
		this.matchThreeModel = matchThreeModel;
		
		// Update state //
		update(0);
		
		// Add observer //
		matchThreeModel.addObserver(this);
		
		// Set properties //
		setFont(new Font(FONT_NAME, Font.BOLD, FONT_SIZE));
		setForeground(Color.WHITE);
	}
	
	/**
	 * Update score label.
	 *
	 * @param score New value.
	 */
	public void update(final int score) {
		setText("Score: " + score);
	}
	
	@Override
	public void update(final Observable o, final Object arg) {
		if (o instanceof MatchThreeModel
		&& arg instanceof MatchThreeModel.ScoreEvent) {
			MatchThreeModel.ScoreEvent event = (MatchThreeModel.ScoreEvent) arg;
			update(event.getScore());
		}
	}
}
