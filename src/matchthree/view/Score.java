package matchthree.view;

import java.awt.Color;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;
import matchthree.message.LabelEvent;
import matchthree.message.ScoreEvent;
import matchthree.model.MatchThreeModel;

/**
 * Score counter.
 *
 * @author Linus Aronsson
 * @author Erik Selstam
 */
// TODO: Rename this since it is used for Moves Left label too?
@SuppressWarnings({"deprecation", "serial"})
public class Score
	extends JLabel
	implements Observer
{
	/** Font. */
	private static final Font FONT = new Font("Helvetica Neue", Font.PLAIN, 20);
	
	/**
	 * Create `Score`.
	 *
	 * @author Linus Aronsson
	 * @author Erik Selstam
	 * @param matchThreeModel MatchThree model to use.
	 */
	public Score(final MatchThreeModel matchThreeModel) {
		// Validate argument //
		if (matchThreeModel == null) {
			throw new IllegalStateException();
		}
		
		// Update state //
		update(0);
		
		// Add observer //
		matchThreeModel.addObserver(this);
		
		// Set properties //
		setFont(FONT);
		setForeground(Color.WHITE);
	}
	
	/**
	 * Update score label.
	 *
	 * @author Linus Aronsson
	 * @author Erik Selstam
	 * @param score New value.
	 */
	public void update(final int score) {
		setText("Score: " + score);
	}
	
	@Override
	public void update(final Observable o, final Object arg) {
		if (o instanceof MatchThreeModel
			&& arg instanceof ScoreEvent)
		{
			LabelEvent event = (LabelEvent) arg;
			update(event.getValue());
		}
	}
}
