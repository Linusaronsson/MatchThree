import javax.swing.JButton;

/**
 * Board cell.
 */
@SuppressWarnings("serial")
class Cell
	extends JButton
{
	private int x = 0;
	private int y = 0;
	
	/**
	 * Constructor for `Cell`.
	 *
	 * @param x X-coordinate of cell.
	 * @param y Y-coordinate of cell.
	 */
	public Cell(int x, int y)
	{
		super();
		
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Get the X-coordinate of the cell.
	 *
	 * @return The X-coordinate.
	 */
	public int getPositionX()
	{
		return x;
	}
	
	/**
	 * Get the Y-coordinate of the cell.
	 *
	 * @return The Y-coordinate.
	 */
	public int getPositionY()
	{
		return y;
	}
}
