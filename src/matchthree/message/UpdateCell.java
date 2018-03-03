package matchthree.message;

import matchthree.model.Jewel;

/**
 * Cell update message.
 *
 * @author Linus Aronsson
 * @author Erik Selstam
 */
@SuppressWarnings("serial")
public class UpdateCell
	extends Message
{
	/** X-coordinate. */
	private int x;
	
	/** Y-coordinate. */
	private int y;
	
	/** Jewel type. */
	private Jewel jewelType;
	
	/**
	 * Constructor.
	 *
	 * @author Linus Aronsson
	 * @param x         X-coordinate to embed.
	 * @param y         Y-coordinate to embed.
	 * @param jewelType Jewel type to embed.
	 */
	public UpdateCell(final int x, final int y, final Jewel jewelType) {
		super(MessageType.CELL_UPDATE);
		this.x = x;
		this.y = y;
		this.jewelType = jewelType;
	}
	
	/**
	 * Get the Y-coordinate.
	 *
	 * @author Linus Aronsson
	 * @return Y-coordinate.
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Get the X-coordinate.
	 *
	 * @author Linus Aronsson
	 * @return X-coordinate.
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Get the jewel type.
	 *
	 * @author Linus Aronsson
	 * @return Jewel type.
	 */
	public Jewel getJewelType() {
		return jewelType;
	}
}
