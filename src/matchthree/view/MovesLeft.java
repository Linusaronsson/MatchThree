package matchthree.view;

import java.awt.Color;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;
import matchthree.message.LabelEvent;
import matchthree.message.MovesLeftEvent;
import matchthree.model.MatchThreeModel;

/**
 * Score counter.
 *
 * @author Linus Aronsson
 * @author Erik Selstam
 */
// TODO: Rename this since it is used for Moves Left label too?
@SuppressWarnings({"deprecation", "serial"})
public class MovesLeft
	extends JLabel
	implements Observer
{
	/** Font. */
	private static final Font FONT = new Font("Helvetica Neue", Font.BOLD, 20);
	
	/**
	 * Create `Score`.
	 *
	 * @author Linus Aronsson
	 * @author Erik Selstam
	 * @param matchThreeModel MatchThree model to use.
	 */
	public MovesLeft(final MatchThreeModel matchThreeModel) {
		// Validate argument //
		if (matchThreeModel == null) {
			throw new IllegalStateException();
		}
		
		// Update state //
		int value = matchThreeModel.getMovesLeft();
		update(value);
		
		// Add observer //
		matchThreeModel.addObserver(this);
		
		// Set properties //
		setFont(FONT);
		setForeground(Color.WHITE);
	}
	
	/**
	 * Update score label with value.
	 *
	 * @author Linus Aronsson
	 * @param score New value.
	 */
	public void update(final int score) {
		setText("Moves left: " + score);
	}
	
	@Override
	public void update(final Observable o, final Object arg) {
		if (o instanceof MatchThreeModel
			&& arg instanceof MovesLeftEvent)
		{
			LabelEvent event = (LabelEvent) arg;
			update(event.getValue());
		}
	}
}
