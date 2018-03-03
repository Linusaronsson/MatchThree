package matchthree.message;

import matchthree.model.Jewel;

/**
 * Cell update message.
 *
 * @author Linus
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
	 * @return Y-coordinate.
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Get the X-coordinate.
	 *
	 * @return X-coordinate.
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Get the jewel type.
	 *
	 * @return Jewel type.
	 */
	public Jewel getJewelType() {
		return jewelType;
	}
}
