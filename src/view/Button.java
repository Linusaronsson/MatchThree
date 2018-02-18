package view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class Button
	extends JButton
{
	/** ... */
	private static final int CELL_HEIGHT = 80;
	
	/** ... */
	private static final int CELL_WIDTH = 80;
	
	/** ... */
	private static final Color COLOR_BACKGROUND = Color.DARK_GRAY.darker();
	
	/** ... */
	private static final Color COLOR_FOREGROUND = new Color(0xEE, 0xEE, 0xEE);
	
	public Button() {
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
	
	private Color color = new Color(0, 0, 0, 0f);
	
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
		//		throw new IllegalArgumentException("alpha is required to be a value between 0f and 1f.");
		//	}
		//}
	
	/**
	 * @param color RGB color to be set, not alpha.
	 */
	protected void setColor(Color color, float alpha) {
		if(color != null) {
			Color newColor = new Color(
					(float)color.getRed()/255f,
					(float)color.getGreen()/255f,
					(float)color.getBlue()/255f,
					alpha
					);
			firePropertyChange("color", this.color, newColor);
			this.color = newColor;
			repaint();
		}
		else throw new IllegalArgumentException("color is null");
	}
	
	/**
	 * @param alpha Alpha value to be set.
	 */
	public void setAlpha(float alpha) {
		alpha = 1-alpha;
		if(alpha >= 0f && alpha <= 1f) {
			Color alphaColor = new Color(
					(float)color.getRed()/255f, 
					(float)color.getGreen()/255f, 
					(float)color.getBlue()/255f, 
					(float)alpha);
			firePropertyChange("alpha", color, alphaColor);
			color = alphaColor;
			repaint();
		}
		else throw new IllegalArgumentException("alpha is required to be a value between 0f and 1f.");
	}
	
	/**
	 * Paints the cell graphical image
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
