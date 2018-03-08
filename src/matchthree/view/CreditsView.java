package matchthree.view;

import java.awt.Component;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;

/**
 * Credits view.
 *
 * @author Erik Tran
 * @author Erik Selstam
 */
@SuppressWarnings("serial")
public class CreditsView
	extends Panel
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
	 *
	 * @author Erik Tran
	 * @author Erik Selstam
	 */
	public CreditsView() {
		// Create labels //
		List<Component> labels = new ArrayList<Component>();
		for (final String name : NAMES) {
			Component label = new CreditsLabel(name);
			labels.add(label);
		}
		
		// Set layout //
		LayoutManager layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);
		
		// Assemble view //
		for (final Component label : labels) {
			add(label);
		}
	}
}
