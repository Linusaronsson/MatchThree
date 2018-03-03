package matchthree.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import matchthree.model.MatchThreeModel;
import matchthree.util.Properties;

/**
 * MatchThree game view.
 */
@SuppressWarnings({"deprecation", "serial"})
public class MatchThreeUI
	extends JPanel
	implements Observer
{
	/** Background color. */
	private static final Color COLOR_BACKGROUND =
		Properties.getColorBackground();
	
	/** Panel color. */
	private static final Color COLOR_PANEL =
		COLOR_BACKGROUND.brighter().brighter();
	
	/** Grid container. */
	private Container gridView = new JPanel();
	
	/** Header container. */
	private Container headerView = new JPanel();
	
	/**
	 * Constructor.
	 *
	 * @param model MatchThree game model to use.
	 */
	// TODO: Call parent constructor?
	public MatchThreeUI(final MatchThreeModel model) {
		// Set properties //
		// TODO: Stopgap hack.
		setBackground(COLOR_BACKGROUND);
		headerView.setBackground(COLOR_BACKGROUND);
		gridView.setBackground(COLOR_BACKGROUND);
		
		// Set layout //
		setLayout(new BorderLayout());
		
		// Assemble view //
		model.addObserver(this);
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
	
	@Override
	public void update(final Observable o, final Object arg) {
		if (o instanceof MatchThreeModel && arg instanceof String) {
			String str = (String) arg;
			if (str.equals("remove")) {
				headerView.removeAll();
				repaint();
				revalidate();
			}
		}
	}
}
