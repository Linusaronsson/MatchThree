package matchthree.view;

import java.awt.Font;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Credits view.
 *
 * @author Erik Tran
 * @author Erik Selstam
 */
@SuppressWarnings("serial")
public class CreditsView
	extends JPanel
{
	/** Credit names. */
	private static final String[] NAMES = new String[] {
		"Linus Aronsson",
		"David Olofsson",
		"Erik Selstam",
		"Jesper Skoglund",
		"Erik Tran",
	};
	
	/**
	 * Constructor.
	 */
	public CreditsView() {
		// Create labels //
		// TODO: Create new view-type class for credit labels.
		List<JLabel> labels = new ArrayList<JLabel>();
		for (String name : NAMES) {
			JLabel label = new JLabel(name);
			labels.add(label);
		}
		
		// Set label properties //
		for (JLabel label : labels) {
			Font font = new Font("Impact", Font.PLAIN, 20);
			label.setFont(font);
		}
		
		// Set layout //
		LayoutManager layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);
		
		// Assemble view //
		for (JLabel label : labels) {
			add(label);
		}
	}
}
