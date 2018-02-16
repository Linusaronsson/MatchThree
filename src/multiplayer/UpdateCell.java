package multiplayer;

import model.Jewel;

/**
 * Message sent between users over a DatagramSocket (Specific jewel).
 */
public class UpdateCell
	extends Message
{
	private int x;
	private int y;
	private Jewel jewelType;
	
	/**
	 * ...
	 */
	public UpdateCell(final int x, final int y, final Jewel jewelType) {
		super(MessageType.CELL_UPDATE);
		this.x = x;
		this.y = y;
		this.jewelType = jewelType;
	}
	
	/**
	 * ...
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * ...
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * ...
	 */
	public Jewel getJewelType() {
		return jewelType;
	}
}
