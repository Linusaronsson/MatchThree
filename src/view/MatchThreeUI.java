package view;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import model.MatchThreeModel;

/**
 * MatchThree game view.
 */
@SuppressWarnings("serial")
public class MatchThreeUI
	extends JPanel
{
	/** ... */
	private static final Color COLOR_BACKGROUND = new Color(0x11, 0x11, 0x11);
	
	/** ... */
	private static final Color COLOR_FOREGROUND = new Color(0xEE, 0xEE, 0xEE);
	
	/** ... */
	private static final String DIR_RESOURCES = "resources";
	
	/**
	 * Constructor for `MatchThreeUI`.
	 *
	 * @param model Model to use
	 */
	// TODO: Break into multiple methods?
	// TODO: Call parent constructor?
	public MatchThreeUI(
		final MatchThreeModel matchThreeModel,
		final GridView        gridView
	) {
		// Validate argument //
		if (matchThreeModel == null) {
			throw new NullPointerException();
		}
		
		// Set properties //
		// TODO: Stopgap hack.
		setBackground(new Color(0x11, 0x11, 0x11));
		
		// Set layout //
		setLayout(new BorderLayout());
		
		// Assemble view //
		MatchThreeHeader header = new MatchThreeHeader(matchThreeModel);
		add(header, BorderLayout.PAGE_START);
		add(gridView, BorderLayout.CENTER);
	}
}
