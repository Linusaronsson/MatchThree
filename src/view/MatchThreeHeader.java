package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import util.Properties;

/**
 * MatchThree game header.
 */
@SuppressWarnings("serial")
public class MatchThreeHeader
	extends JPanel
{
	/** ... */
	private static final Color COLOR_BACKGROUND =
		Properties.getColorBackground();
	
	/** ... */
	private static final Color COLOR_PANEL =
		COLOR_BACKGROUND.brighter().brighter();
	
	/** Score container. */
	private Container scoreView = new JPanel();
	
	/**
	 * Constructor.
	 */
	public MatchThreeHeader() {
		// Set properties //
		setBackground(COLOR_PANEL);
		
		// Set layout //
		setLayout(new FlowLayout());
		
		// Assemble view //
		add(scoreView);
	}
	
	/**
	 * Get score container.
	 *
	 * @return Score container.
	 */
	public Container getScore() {
		return scoreView;
	}
}
