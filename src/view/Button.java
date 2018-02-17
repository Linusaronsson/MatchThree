package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 * Button.
 */
@SuppressWarnings("serial")
public class Button
	extends JButton
{
	/** ... */
	private static final int CELL_HEIGHT = 80;
	
	/** ... */
	private static final int CELL_WIDTH = 80;
	
	/** ... */
	private static final Color COLOR_BACKGROUND = new Color(0x11, 0x11, 0x11);
	
	/** ... */
	private static final Color COLOR_FOREGROUND = new Color(0xEE, 0xEE, 0xEE);
	
	/**
	 * ...
	 */
	private final class ButtonHoverListener
		implements MouseListener
	{
		/** ... */
		private Button target = null;
		
		/**
		 * ...
		 *
		 * @param target ...
		 */
		private ButtonHoverListener(final Button target) {
			this.target = target;
		}
		
		@Override
		public void mouseClicked(final MouseEvent e) { }
		
		@Override
		public void mouseEntered(final MouseEvent e) {
			//target.setAlpha(0.5f);
		}
		
		@Override
		public void mouseExited(final MouseEvent e) {
			//target.setAlpha(1.0f);
		}
		
		@Override
		public void mousePressed(final MouseEvent e) { }
		
		@Override
		public void mouseReleased(final MouseEvent e) { }
	}
	
	/**
	 * Create `Button`.
	 */
	public Button() {
		// Set colors //
		setForeground(COLOR_FOREGROUND);
		setBackground(COLOR_BACKGROUND);
		
		// Set alignment //
		setHorizontalAlignment(SwingConstants.CENTER);
		setVerticalAlignment(SwingConstants.CENTER);
		
		// Set properties //
		setBorderPainted(false);
		setContentAreaFilled(false);
		setEnabled(true);
		setFocusPainted(false);
		setOpaque(true);
		setPreferredSize(new Dimension(CELL_WIDTH, CELL_WIDTH));
		
		// Add event listener //
		addMouseListener(new ButtonHoverListener(this));
	}
	
	///**
	// * @param alpha Alpha value to be set.
	// */
	//protected void setAlpha(float alpha) {
	//	alpha = 1 - alpha;
	//	if (alpha >= 0f && alpha <= 1f) {
	//		Color alphaColor = new Color(0, 0, 0, alpha);
	//		firePropertyChange("alpha", color, alphaColor);
	//		color = alphaColor;
	//		repaint();
	//	} else {
	//		throw new IllegalArgumentException(
	//			"alpha is required to be a value between 0f and 1f."
	//		);
	//	}
	//}
}
