package view;

import java.awt.Color;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;
import model.MatchThreeModel;

/**
 * Score counter.
 * TODO: rename this since its used for Moves Left label too?
 */
@SuppressWarnings({"deprecation", "serial"})
public abstract class LabelObserver
	extends JLabel
	implements Observer
{
	/** Font name. */
	private static final String FONT_NAME = "Helvetica Neue";
	
	/** Font size. */
	private static final int FONT_SIZE = 20;
	
	private String title = null;
	
	/**
	 * Create `Score`.
	 *
	 * @param matchThreeModel MatchThree model to use.
	 */
	public LabelObserver(
			final MatchThreeModel matchThreeModel,
			final String title, 
			final int start_val
		) {
		// Validate argument //
		if (matchThreeModel == null) {
			throw new IllegalStateException();
		}
		
		this.title = title;
		
		// Update state //
		update(start_val);
		
		// Add observer //
		matchThreeModel.addObserver(this);
		
		// Set properties //
		setFont(new Font(FONT_NAME, Font.BOLD, FONT_SIZE));
		setForeground(Color.BLACK);
	}
	
	/**
	 * Update score label.
	 *
	 * @param score New value.
	 */
	public void update(final int score) {
		setText(title + ": " + score);
	}

}
