package view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Button.
 */
@SuppressWarnings("serial")
public class Button
	extends JButton
{	
	/** ... */
	private static final int CELL_WIDTH = 80;
	
	/** ... */
	private static final Color COLOR_BACKGROUND = Color.DARK_GRAY.darker();
	
	/** ... */
	private static final Color COLOR_FOREGROUND = new Color(0xEE, 0xEE, 0xEE);
	
	/** ... */
	private JLabel label = new JLabel();
	
	/**
	 * Create button.
	 */
	public Button() {
		setProperties();
	}
	
	/**
	 * 
	 * @param label
	 */
	public Button(String text) {
		label.setText(text);
		label.setForeground(Color.WHITE);
		add(label);
		setProperties();
	}
	
	/**
	 * 
	 * @param icon
	 */
	public Button(ImageIcon icon) {
		setProperties();
		setIcon(icon);
	}
	
	/**
	 * Set button properties
	 */
	private void setProperties() {
		// Set colors //
		setForeground(COLOR_FOREGROUND);
		setBackground(COLOR_BACKGROUND);
		
		// Set alignment //
		setHorizontalAlignment(SwingConstants.CENTER);
		setVerticalAlignment(SwingConstants.CENTER);
		
		// Set properties //
		setBorderPainted(false);
		setEnabled(true);
		setFocusPainted(false);
		setOpaque(true);
		setPreferredSize(new Dimension(CELL_WIDTH, CELL_WIDTH));
		setContentAreaFilled(false);
	}
	
	public void setLabelForeground(final Color color, final float alpha) {
		// Validate arguments //
		if(color == null) {
			throw new IllegalArgumentException();
		}
		if(alpha < 0f || alpha > 1f) {
			throw new IllegalArgumentException();
		}
				
		Color newColor = new Color(
			(float)color.getRed() / 255f,
			(float)color.getGreen() / 255f,
			(float)color.getBlue() / 255f,
			alpha
		);
		
		label.setForeground(newColor);
	}
	
	/** ... */
	private Color color = new Color(0, 0, 0, 0f);
	
	/**
	 * @param color
	 */
	public void setColor(Color color, float alpha) {
		// Validate arguments //
		if(color == null) {
			throw new IllegalArgumentException();
		}
		if(alpha < 0f || alpha > 1f) {
			throw new IllegalArgumentException();
		}
		
		Color newColor = new Color(
			(float)color.getRed() / 255f,
			(float)color.getGreen() / 255f,
			(float)color.getBlue() / 255f,
			alpha
		);
		firePropertyChange("color", this.color, newColor);
		this.color = newColor;
		
		repaint();
	}
	
	/**
	 * @param alpha
	 */
	public void setAlpha(float alpha) {
		alpha = 1-alpha;
		
		// Validate argument //
		if(alpha < 0f || alpha > 1f) {
			throw new IllegalArgumentException();
		}
		
		Color alphaColor = new Color(
			(float)color.getRed() / 255f, 
			(float)color.getGreen() / 255f, 
			(float)color.getBlue() / 255f, 
			alpha
		);
		firePropertyChange("alpha", color, alphaColor);
		color = alphaColor;
		
		repaint();
	}
	
	@Override
	public void setFont(final Font font) {
		if(font != null && label != null)
		label.setFont(font);
	}
	
	/**
	 * Updates graphical image
	 */
	@Override
	protected void paintComponent(final Graphics g) {
		BufferedImage image =
				getGraphicsConfiguration().
				createCompatibleImage(
						getWidth(),
						getHeight(),
						Transparency.TRANSLUCENT
						);
		image.coerceData(true);
		
		Graphics2D imageGraphics = image.createGraphics();
		super.paintComponent(imageGraphics);
		imageGraphics.dispose();
		
		BufferedImage mask =
				getGraphicsConfiguration().
				createCompatibleImage(
						image.getWidth(),
						image.getHeight(),
						Transparency.TRANSLUCENT
						);
		mask.coerceData(true);
		
		Graphics2D maskGraphics = mask.createGraphics();
		maskGraphics.drawImage(image, 0, 0, null);
		maskGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, 1f));
		maskGraphics.setColor(color);
		maskGraphics.fillRect(0, 0, image.getWidth(), image.getHeight());
		maskGraphics.dispose();
		
		g.drawImage(image, 0, 0, this);
		g.drawImage(mask, 0, 0, this);
	}
}
