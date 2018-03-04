package matchthree.view;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import matchthree.util.AssetManager;

/**
 * Back button.
 *
 * @author Erik Selstam
 */
@SuppressWarnings("serial")
public class BackButton
	extends Button
{
	/** Button image. */
	private static final BufferedImage IMAGE_BACK =
		AssetManager.loadImage("Back.png");
	
	/**
	 * Constructor.
	 *
	 * @author Erik Selstam
	 */
	public BackButton() {
		super();
		
		// Set properties //
		ImageIcon icon = new ImageIcon(IMAGE_BACK);
		Dimension size = new Dimension(50, 50);
		setIcon(icon);
		setPreferredSize(size);
	}
}
