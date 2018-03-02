package matchthree.view;

import matchthree.model.Coordinate;

/**
 * Board cell.
 */
@SuppressWarnings("serial")
public class Cell
	extends Button
{
	/** Cell width. */
	private static final int CELL_WIDTH = 80;
	
	/** ... */
	private Coordinate position = null;
	
	/** ... */
	private boolean state = false;
	
	/**
	 * Constructor for `Cell`.
	 *
	 * @param position Coordinates of the cell.
	 */
	public Cell(final Coordinate position) {
		super(CELL_WIDTH);
		
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
		super(CELL_WIDTH);
		
		// Validate arguments //
		if (x < 0 || y < 0) {
			throw new IllegalArgumentException();
		}
		
		position = new Coordinate(x, y);
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
	
	/**
	 * ...
	 *
	 * @return ...
	 */
	public boolean isActive() {
		return state;
	}
	
	/**
	 * ...
	 *
	 * @param state ...
	 */
	public void setState(final boolean state) {
		this.state = state;
	}
}
