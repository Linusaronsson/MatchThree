package matchthree.view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Transparency;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Button.
 *
 * @author Erik Tran
 * @author Erik Selstam
 */
@SuppressWarnings("serial")
public class Button
	extends JButton
{
	/** ... */
	private static final int DEFAULT_WIDTH = 80;
	
	/** ... */
	private static final int DEFAULT_HEIGHT = 80;
	
	/** ... */
	private static final Color COLOR_BACKGROUND = new Color(0x33, 0x33, 0x33);
	
	/** ... */
	private static final Color COLOR_FOREGROUND = new Color(0xEE, 0xEE, 0xEE);
	
	/** ... */
	private Color color = new Color(0, 0, 0, 0);
	
	/** ... */
	private JLabel label = new JLabel();
	
	/**
	 * Create button.
	 *
	 * @author Erik Tran
	 */
	public Button() {
		setProperties();
	}
	
	/**
	 * ...
	 *
	 * @author Erik Tran
	 * @param size Cell width
	 */
	public Button(final int size) {
		setProperties(size);
	}
	
	/**
	 * ...
	 *
	 * @author Erik Tran
	 * @param label ...
	 */
	public Button(final String label) {
		this.label.setText(label);
		this.label.setForeground(Color.WHITE);
		add(this.label);
		setProperties();
	}
	
	/**
	 * ...
	 *
	 * @author Erik Tran
	 * @param icon ...
	 */
	public Button(final ImageIcon icon) {
		setProperties();
		setIcon(icon);
	}
	
	/**
	 * Set button properties.
	 *
	 * @author Erik Selstam
	 * @author Erik Tran
	 */
	private void setProperties() {
		// Set properties //
		setBorderPainted(false);
		setContentAreaFilled(false);
		setEnabled(true);
		setFocusPainted(false);
		setForeground(COLOR_FOREGROUND);
		setHorizontalAlignment(SwingConstants.CENTER);
		setOpaque(false);
		setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		setVerticalAlignment(SwingConstants.CENTER);
	}
	
	/**
	 * ...
	 *
	 * @author Erik Tran
	 * @param size Cell width.
	 */
	private void setProperties(final int size) {
		setProperties();
		setPreferredSize(new Dimension(size, size));
	}
	
	/**
	 * ...
	 *
	 * @author Erik Tran
	 * @param color ...
	 * @param alpha ...
	 */
	public void setLabelForeground(final Color color, final float alpha) {
		// Validate arguments //
		if (color == null) {
			throw new NullPointerException();
		}
		if (alpha < 0f || alpha > 1f) {
			throw new IllegalArgumentException();
		}
		
		Color newColor = getNewColor(color, alpha);
		label.setForeground(newColor);
	}
	
	/**
	 * ...
	 *
	 * @author Erik Tran
	 * @param color ...
	 * @param alpha ...
	 */
	public void setMask(final Color color, final float alpha) {
		// Validate arguments //
		if (color == null) {
			throw new NullPointerException();
		}
		if (alpha < 0f || alpha > 1f) {
			throw new IllegalArgumentException();
		}
		
		Color newColor = getNewColor(color, alpha);
		firePropertyChange("color", this.color, newColor);
		this.color = newColor;
		
		repaint();
	}
	
	/**
	 * ...
	 *
	 * @author Erik Tran
	 * @param color ...
	 * @param alpha ...
	 * @return      ...
	 */
	private Color getNewColor(final Color color, final float alpha) {
		return new Color(
			(float) color.getRed()   / 255f,
			(float) color.getGreen() / 255f,
			(float) color.getBlue()  / 255f,
			alpha
		);
	}
	
	@Override
	public void setFont(final Font font) {
		super.setFont(font);
		
		if (font != null && label != null) {
			label.setFont(font);
		}
	}
	
	/**
	 * Updates graphical image.
	 *
	 * @author Erik Tran
	 */
	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		
		BufferedImage image =
			getGraphicsConfiguration().createCompatibleImage(
				getWidth(),
				getHeight(),
				Transparency.TRANSLUCENT
			);
		image.coerceData(true);
		
		Graphics2D imageGraphics = image.createGraphics();
		imageGraphics.dispose();
		
		BufferedImage mask =
			getGraphicsConfiguration().createCompatibleImage(
				image.getWidth(),
				image.getHeight(),
				Transparency.TRANSLUCENT
			);
		mask.coerceData(true);
		
		Graphics2D maskGraphics = mask.createGraphics();
		maskGraphics.drawImage(image, 0, 0, null);
		AlphaComposite composite = AlphaComposite.getInstance(
			AlphaComposite.SRC_IN,
			1f
		);
		maskGraphics.setComposite(composite);
		maskGraphics.setColor(color);
		maskGraphics.fillRect(0, 0, image.getWidth(), image.getHeight());
		maskGraphics.dispose();
		
		g.drawImage(image, 0, 0, this);
		g.drawImage(mask, 0, 0, this);
	}
}
