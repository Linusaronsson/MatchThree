package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import model.MatchThreeModel;

@SuppressWarnings("serial")
public class MatchThreeHeader
	extends JPanel
{
	/** ... */
	private static final Color COLOR_BACKGROUND = Color.DARK_GRAY.darker();
	
	/** ... */
	private static final Color COLOR_PANEL = COLOR_BACKGROUND.brighter().brighter();
	
	public MatchThreeHeader(final MatchThreeModel matchThreeModel) {
		// Validate argument //
		if (matchThreeModel == null) {
			throw new IllegalStateException();
		}
		
		// Set layout //
		setLayout(new FlowLayout());
		setBackground(COLOR_PANEL);
		
		// Assemble view //
		add(new Score(matchThreeModel));
	}
}
