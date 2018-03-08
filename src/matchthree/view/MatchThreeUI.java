package matchthree.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.util.Observable;
import java.util.Observer;
import matchthree.model.MatchThreeModel;

/**
 * MatchThree game view.
 *
 * @author Erik Selstam
 * @author Linus Aronsson
 * @author Erik Tran
 */
@SuppressWarnings({"deprecation", "serial"})
public class MatchThreeUI
	extends Panel
	implements Observer
{
	/** Background color. */
	private static final Color COLOR_BACKGROUND = new Color(0x11, 0x11, 0x11);
	
	/** Grid container. */
	private SubPanel gridView = new SubPanel();
	
	/** Header container. */
	private SubPanel headerView = new SubPanel();
	
	/**
	 * Constructor.
	 *
	 * @author Erik Selstam
	 * @author Linus Aronsson
	 * @author Erik Tran
	 * @param model MatchThree game model to use.
	 */
	// TODO: Call parent constructor?
	public MatchThreeUI(final MatchThreeModel model) {
		// Register observer //
		model.addObserver(this);
		
		// Set properties //
		setBackground(COLOR_BACKGROUND);
		
		// Set sub-panel properties //
		headerView.setPadding(40, 40, 40, 10);
		gridView.setPadding(10, 40, 40, 40);
		
		// Assemble view //
		add(headerView, BorderLayout.NORTH);
		add(gridView,   BorderLayout.CENTER);
	}
	
	/**
	 * Get grid container.
	 *
	 * @author Erik Selstam
	 * @return Grid container.
	 */
	public Container getGrid() {
		return gridView;
	}
	
	/**
	 * Get header container.
	 *
	 * @author Erik Selstam
	 * @return Header container.
	 */
	public Container getHeader() {
		return headerView;
	}
	
	@Override
	public void update(final Observable o, final Object arg) {
		if (o instanceof MatchThreeModel && arg instanceof String) {
			String str = (String) arg;
			if ("remove".equals(str)) {
				headerView.removeAll();
				repaint();
				revalidate();
			}
		}
	}
}
