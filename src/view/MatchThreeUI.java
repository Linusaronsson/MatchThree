package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import javax.swing.JPanel;
import util.Properties;

/**
 * MatchThree game view.
 */
@SuppressWarnings("serial")
public class MatchThreeUI
	extends JPanel
{
	/** ... */
	private static final Color COLOR_BACKGROUND = Properties.getColorBackground();
	
	/** Grid container. */
	private Container gridView = new JPanel();
	
	/** Header container. */
	private Container headerView = new JPanel();
	
	/**
	 * Constructor for `MatchThreeUI`.
	 */
	// TODO: Call parent constructor?
	public MatchThreeUI() {
		// Set properties //
		// TODO: Stopgap hack.
		setBackground(COLOR_BACKGROUND);
		
		// Set layout //
		setLayout(new BorderLayout());
		
		// Assemble view //
		add(headerView, BorderLayout.PAGE_START);
		add(gridView, BorderLayout.CENTER);
	}
	
	/**
	 * Get grid container.
	 *
	 * @return Grid container.
	 */
	public Container getGrid() {
		return gridView;
	}
	
	/**
	 * Get header container.
	 *
	 * @return Header container.
	 */
	public Container getHeader() {
		return headerView;
	}
}
