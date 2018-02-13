package View;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import Model.Coordinate;

/**
 * Board cell.
 */
@SuppressWarnings("serial")
public class Cell
	extends JButton
{
	/** ... */
	private Coordinate position = null;
	
	/**
	 * Constructor for `Cell`.
	 *
	 * @param position Coordinates of the cell.
	 */
	public Cell(final Coordinate position) {
		super();
		
		// Validate argument //
		if (position == null) {
			throw new NullPointerException();
		}
		
		this.position = position;
	}
    
	/**
	 * Constructor for `Cell`.
	 *
	 * @param x X-coordinate of the cell.
	 * @param y Y-coordinate of the cell.
	 */
	public Cell(final int x, final int y) {
		super();
		
		// Validate arguments //
		if (x < 0 || y < 0) {
			throw new IllegalArgumentException();
		}
		
		position = new Coordinate(x, y);
	}
	
	/**
	 * //Not a board cell, just a normal JButton that needs the setAlpha method 
	 * [backButton, buttonV1, buttonV2]
	 * @param i ...
	 */
	public Cell(final int i) {
		if(i == 0) {
			return;
		}
		else throw new IllegalArgumentException();
	}
	
	/**
	 * Get the coordinates of the cell.
	 *
	 * @return The coordinates.
	 */
	public Coordinate getPosition() {
		return position;
	}
	
	/**
	 * Get the X-coordinate of the cell.
	 *
	 * @return The X-coordinate.
	 */
	public int getPositionX() {
		return position.getX();
	}
	
	/**
	 * Get the Y-coordinate of the cell.
	 *
	 * @return The Y-coordinate.
	 */
	public int getPositionY() {
		return position.getY();
	}
	
	
	private Color color = new Color(0, 0, 0, 0f);
	
	/**
	 * @param alpha Alpha value to be set.
	 */
	protected void setAlpha(float alpha) {
		alpha = 1-alpha;
		if(alpha >= 0f && alpha <= 1f) {
			Color alphaColor = new Color(0, 0, 0, alpha);
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
	protected void paintComponent(Graphics g) {
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
