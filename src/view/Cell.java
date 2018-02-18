package view;

import model.Coordinate;

/**
 * Board cell.
 */
@SuppressWarnings("serial")
public class Cell
	extends Button
{
	/** ... */
	private Coordinate position = null;
	
	/**
	 * Constructor for `Cell`.
	 *
	 * @param position Coordinates of the cell.
	 */
	public Cell(final Coordinate position) {
		super();
		
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
		super();
		
		// Validate arguments //
		if (x < 0 || y < 0) {
			throw new IllegalArgumentException();
		}
		
		position = new Coordinate(x, y);
	}
	
	/**
	 * Create `Cell`.
	 *
	 * @param i ...
	 */
	public Cell(final int i) {
		// Validate argument //
		if (i != 0) {
			throw new IllegalArgumentException();
		}
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
	
	/** ... */
	private boolean state = false;
	
	/**
	 * 
	 * 
	 * @return
	 */
	public boolean isActive() {
		return state;
	}
	
	/**
	 * 
	 * 
	 * @param state
	 */
	public void setState(boolean state) {
		this.state = state;
	}
}
