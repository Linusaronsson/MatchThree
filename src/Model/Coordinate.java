package Model;
/**
 * Stores a two-dimensional coordinate, limited to the first quadrant.
 */
public class Coordinate
{
	public int x = 0;
	public int y = 0;
	
	/**
	 * Constructor for `Coordinate`.
	 *
	 * @param x X-coordinate. Must be positive.
	 * @param y Y-coordinate. Must be positive.
	 */
	public Coordinate(int x, int y)
	{
		if (x < 0 || y < 0) {
			throw new IllegalArgumentException();
		}
		
		this.x = x;
		this.y = y;
	}
}
