package view;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.MatchThreeModel;

@SuppressWarnings("serial")
public class MatchThreeHeader
	extends JPanel
{
	/** ... */
	private JLabel label = new JLabel("");
	
	public MatchThreeHeader(final MatchThreeModel matchThreeModel) {
		// Validate argument //
		if (matchThreeModel == null) {
			throw new IllegalStateException();
		}
		
		// Set layout //
		setLayout(new FlowLayout());
		
		// Assemble view //
		add(new Score(matchThreeModel));
	}
}
