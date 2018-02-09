package Model;

/**
 * Stores a two-dimensional coordinate, limited to the first quadrant.
 */
public class Coordinate
{
	/** ... */
	private int x = 0;
	
	/** ... */
	private int y = 0;
	
	/**
	 * Constructor for `Coordinate`.
	 *
	 * @param x X-coordinate. Must be positive.
	 * @param y Y-coordinate. Must be positive.
	 */
	public Coordinate(final int x, final int y) {
		if (x < 0 || y < 0) {
			throw new IllegalArgumentException();
		}
		
		this.x = x;
		this.y = y;
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
	public int getY() {
		return y;
	}
	
	/**
	 * ...
	 *
	 * @param x ...
	 */
	public void setX(final int x) {
		if (x < 0) {
			throw new IllegalArgumentException();
		}
		
		this.x = x;
	}
	
	/**
	 * ...
	 *
	 * @param y ...
	 */
	public void setY(final int y) {
		if (y < 0) {
			throw new IllegalArgumentException();
		}
		
		this.y = y;
	}
}
