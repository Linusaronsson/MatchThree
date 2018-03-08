package matchthree.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * ...
 *
 * @author Erik Selstam
 * @author Erik Tran
 */
@SuppressWarnings("serial")
public class GameFinished
	extends Panel
{
	/** Foreground color. */
	private static final Color COLOR_FOREGROUND = Color.WHITE;
	
	/** Message font size. */
	private static final Font FONT_HEADER =
		new Font(Font.SANS_SERIF, Font.BOLD, 32);
	
	/** Score font size. */
	private static final Font FONT_SCORE =
		new Font(Font.SANS_SERIF, Font.PLAIN, 32);
	
	/** Title font size. */
	private static final Font FONT_TITLE =
		new Font(Font.SANS_SERIF, Font.BOLD, 48);
	
	/** Header content. */
	private static final String HEADER = "Score";
	
	/** Title content. */
	private static final String TITLE = "Game Over";
	
	/**
	 * Constructor.
	 *
	 * @author Erik Selstam
	 * @author Erik Tran
	 * @param score ...
	 */
	public GameFinished(final int score) {
		super();
		
		// Create labels //
		JLabel headerLabel = new JLabel(HEADER);
		JLabel scoreLabel  = new JLabel(Integer.toString(score));
		JLabel titleLabel  = new JLabel(TITLE);
		
		// Set label colors //
		headerLabel.setForeground(COLOR_FOREGROUND);
		scoreLabel.setForeground(COLOR_FOREGROUND);
		titleLabel.setForeground(COLOR_FOREGROUND);
		
		// Set label alignments //
		headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		// Set label fonts //
		headerLabel.setFont(FONT_HEADER);
		scoreLabel.setFont(FONT_SCORE);
		titleLabel.setFont(FONT_TITLE);
		
		// Set layout //
		LayoutManager layout = new GridLayout(3, 0, 0, 10);
		setLayout(layout);
		
		// Assemble view //
		add(titleLabel);
		add(headerLabel);
		add(scoreLabel);
	}
}
