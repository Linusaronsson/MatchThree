package multiplayer;

import model.Jewel;

/**
 * @author Linus 
 * 
 * Message sent between users over a DatagramSocket (Specific jewel).
 */
@SuppressWarnings("serial")
public class UpdateCell
	extends Message
{
	/** x coordinate */
	private int x;
	
	/** y coordinate */
	private int y;
	
	/** The type of jewel. */
	private Jewel jewelType;
	
	/**
	 * Constructor
	 *
	 * @param x         x-coordinate
	 * @param y         y-coordinate
	 * @param jewelType Jewel type
	 */
	public UpdateCell(final int x, final int y, final Jewel jewelType) {
		super(MessageType.CELL_UPDATE);
		this.x = x;
		this.y = y;
		this.jewelType = jewelType;
	}
	
	/**
	 * Y getter.
	 *
	 * @return y-coordinate
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * X getter
	 *
	 * @return x-coordinate
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Jewel type getter
	 *
	 * @return Jewel type
	 */
	public Jewel getJewelType() {
		return jewelType;
	}
}
