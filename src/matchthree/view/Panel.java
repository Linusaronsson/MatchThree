package matchthree.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.LayoutManager;

/**
 * Layout panel.
 *
 * @author Erik Selstam
 */
@SuppressWarnings("serial")
public class Panel
	extends Container
{
	/**
	 * Constructor.
	 *
	 * @author Erik Selstam
	 */
	public Panel() {
		super();
		
		// Set layout //
		LayoutManager layout = new BorderLayout(0, 0);
		setLayout(layout);
	}
}
