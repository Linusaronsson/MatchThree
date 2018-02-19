package multiplayer;

import model.Jewel;

/**
 * Message sent between users over a DatagramSocket (Specific jewel).
 */
@SuppressWarnings("serial")
public class UpdateCell
	extends Message
{
	/** ... */
	private int x;
	
	/** ... */
	private int y;
	
	/** ... */
	private Jewel jewelType;
	
	/**
	 * ...
	 *
	 * @param x         ...
	 * @param y         ...
	 * @param jewelType ...
	 */
	public UpdateCell(final int x, final int y, final Jewel jewelType) {
		super(MessageType.CELL_UPDATE);
		this.x = x;
		this.y = y;
		this.jewelType = jewelType;
	}
	
	/**
	 * ...
	 *
	 * @return ...
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * ...
	 *
	 * @return ...
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * ...
	 *
	 * @return ...
	 */
	public Jewel getJewelType() {
		return jewelType;
	}
}
