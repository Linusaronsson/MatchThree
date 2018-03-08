package matchthree.view;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.JPanel;

/**
 * Sub-layout panel.
 *
 * @author Erik Selstam
 */
@SuppressWarnings("serial")
public class SubPanel
	extends JPanel
{
	/**
	 * Constructor.
	 *
	 * @author Erik Selstam
	 */
	public SubPanel() {
		super();
		
		// Set properties //
		setBackground(null);
		
		// Set layout //
		LayoutManager layout = new BorderLayout(0, 0);
		setLayout(layout);
	}
	
	/**
	 * Set panel padding.
	 *
	 * @author Erik Selstam
	 * @param top    The top padding to use.
	 * @param left   The left padding to use.
	 * @param right  The right padding to use.
	 * @param bottom The bottom padding to use.
	 */
	public void setPadding(
		final int top,
		final int left,
		final int right,
		final int bottom)
	{
		AbstractBorder border =
			new EmptyBorder(top, left, bottom, right);
		setBorder(border);
	}
	
	/**
	 * Set panel padding.
	 *
	 * @author Erik Selstam
	 * @param horizontal The horizontal padding to use.
	 * @param vertical   The vertical padding to use.
	 */
	public void setPadding(final int horizontal, final int vertical) {
		setPadding(vertical, horizontal, horizontal, vertical);
	}
	
	/**
	 * Set panel padding.
	 *
	 * @author Erik Selstam
	 * @param padding The padding to use.
	 */
	public void setPadding(final int padding) {
		setPadding(padding, padding);
	}
}
