import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * MVC model.
 */
class BoardModel
{
	private static final int MINIMUM_LENGTH = 3;
	
	private Jewel[] board = null;
	private int     score = 0;
	private int     width = 0;
	
	/**
	 * Cell symbol enum.
	 */
	public enum Jewel {
		DIAMOND,
		EMERALD,
		RUBY,
		SAPPHIRE;
		
		// TODO: Use array instead?
		private static final List<Jewel> VALUES =
			Collections.unmodifiableList(Arrays.asList(values()));
		private static final int    SIZE   = VALUES.size();
		private static final Random RANDOM = new Random();
		
		/**
		 * Return a random jewel.
		 *
		 * @return A random jewel type.
		 */
		// TODO: Support returning a limited subset of jewels?
		public static Jewel random()
		{
			return VALUES.get(RANDOM.nextInt(SIZE));
		}
	}
	
	/**
	 * Move type enum.
	 */
	public enum MoveType {
		BAD,
		CANCEL,
		OK;
	}
	
	/**
	 * Constructor for `BoardModel` MVC model.
	 *
	 * @param width Size of the board on one axis in number of cells.
	 */
	public BoardModel(int width)
	{
		// Validate argument //
		if (width < 0) {
			throw new IllegalArgumentException();
		}
		
		this.width = width;
		
		// Construct board //
		board = new Jewel[width * width];
		for (int i = 0; i < width * width; i++) {
			board[i] = Jewel.random();
		}
	}
	
	/**
	 * Clear jewels from the board.
	 *
	 * @param positions Coordinates of affected cells.
	 * @return          Gained score.
	 */
	// TODO: Revise algorithm and reduce complexity.
	private int clearMatches(Coordinate[] positions)
	{
		// Validate argument //
		// TODO: Add assert on count not exceeding number of cells.
		if (positions == null) {
			throw new NullPointerException();
		}
		
		// Find matches //
		// TODO: Check for null values inside array?
		List<List<Coordinate>> matches = new ArrayList<List<Coordinate>>();
		for (Coordinate position : positions) {
			// Get jewel type to match //
			Jewel matchType = get(position);
			
			// Unpack coordinates //
			int x = position.x;
			int y = position.y;
			
			// Search for matches on X-axis //
			int startX = position.x;
			int stopX  = position.x;
			List<Coordinate> cellsX = new ArrayList<Coordinate>();
			for (int i = x; i >= 0; i--) {
				Jewel cell = get(i, y);
				if (cell == null || !cell.equals(matchType)) {
					break;
				}
				startX = i;
				cellsX.add(new Coordinate(i, y));
			}
			for (int i = x + 1; i < width; i++) {
				Jewel cell = get(i, y);
				if (cell == null || !cell.equals(matchType)) {
					break;
				}
				stopX = i;
				cellsX.add(new Coordinate(i, y));
			}
			if (cellsX.size() > 0) {
				matches.add(cellsX);
			}
			
			// Search for matches on Y-axis //
			int startY = position.y;
			int stopY  = position.y;
			List<Coordinate> cellsY = new ArrayList<Coordinate>();
			for (int i = y; i >= 0; i--) {
				Jewel cell = get(x, i);
				if (cell == null || !cell.equals(matchType)) {
					break;
				}
				startY = i;
				cellsY.add(new Coordinate(x, i));
			}
			for (int i = y + 1; i < width; i++) {
				Jewel cell = get(x, i);
				if (cell == null || !cell.equals(matchType)) {
					break;
				}
				stopY = i;
				cellsY.add(new Coordinate(x, i));
			}
			if (cellsY.size() > 0) {
				matches.add(cellsY);
			}
		}
		
		// Clear matches and adjust score //
		int points = 0;
		for (List<Coordinate> match : matches) {
			// Verify length of match //
			if (match.size() < MINIMUM_LENGTH) {
				continue;
			}
			
			// Clear cells //
			for (Coordinate cell : match) {
				set(cell, null);
			}
			
			// Adjust score //
			// TODO: Make score increase exponentially with longer matches.
			int match_points = match.size() * 100;
			points += match_points;
			score  += match_points;
		}
		
		// Drop cells //
		for (int column = 0; column < width; column++) {
			for (int row = width - 1; row >= 0; --row) {
				// Move cell down to last empty space //
				for (int index = row; index < width - 1; index++) {
					int i1 = (index + 0) * width + column;
					int i2 = (index + 1) * width + column;
					if (board[i2] != null) {
						break;
					}
					board[i2] = board[i1];
					board[i1] = null;
				}
			}
		}
		
		// Refill board //
		// TODO: Assert that holes are not filled (fill from the top).
		for (int i = 0; i < width * width; i++) {
			if (board[i] == null) {
				board[i] = Jewel.random();
			}
		}
		
		// TODO: Clear board again.
		
		return points;
	}
	
	/**
	 * Get the value of a cell.
	 *
	 * @param position Coordinates of the cell.
	 * @return         The cell value.
	 */
	public Jewel get(Coordinate position)
	{
		// Validate argument //
		if (position == null) {
			throw new NullPointerException();
		}
		
		return get(position.x, position.y);
	}
	
	/**
	 * Get the value of a cell.
	 *
	 * @param x X-coordinate of the cell.
	 * @param y Y-coordinate of the cell.
	 * @return  The cell value.
	 */
	public Jewel get(int x, int y)
	{
		// Validate arguments //
		if (x < 0 || y < 0) {
			throw new IllegalArgumentException();
		}
		if (x >= width || y >= width) {
			throw new IndexOutOfBoundsException();
		}
		
		int i = y * width + x;
		return board[i];
	}
	
	/**
	 * Get the current score.
	 *
	 * @return The current score.
	 */
	public int getScore()
	{
		return score;
	}
	
	/**
	 * Prototype function for MVC implementation.
	 *
	 * @return A placeholder string.
	 */
	public String getValue()
	{
		return "Hello, World!";
	}
	
	/**
	 * Get the size the board.
	 *
	 * @return The number of cells per axis.
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * Move a cell.
	 *
	 * @param from Source coordinates.
	 * @param to   Destination coordinates.
	 * @return     Whether the move was successful, invalid or canceled.
	 */
	public MoveType move(Coordinate from, Coordinate to)
	{
		// Validate arguments //
		if (from == null || to == null) {
			throw new NullPointerException();
		}
		
		// Validate move //
		if (from.x == to.x && from.y == to.y) {
			return MoveType.CANCEL;
		}
		if (from.x != to.x && from.y != to.y) {
			return MoveType.BAD;
		}
		
		// Move cell //
		Jewel source = get(from);
		int dx = to.x - from.x;
		int dy = to.y - from.y;
		if (dx < 0) { dx = -1; }
		if (dx > 0) { dx = +1; }
		if (dy < 0) { dy = -1; }
		if (dy > 0) { dy = +1; }
		List<Coordinate> positions = new ArrayList<Coordinate>();
		for (Coordinate position = from;
		     position.x != to.x || position.y != to.y; // TODO: Implement `equals'.
		     position.x += dx, position.y += dy)
		{
			// Swap cell with neighbor //
			Jewel first  = get(position.x, position.y);
			Jewel second = get(position.x + dx, position.y + dy);
			set(position.x, position.y, second);
			set(position.x + dx, position.y + dy, first);
			
			// Save coordinate //
			positions.add(new Coordinate(position.x, position.y));
		}
		positions.add(to);
		
		// Clear cells //
		Coordinate[] out = new Coordinate[positions.size()];
		clearMatches(positions.toArray(out));
		
		return MoveType.OK;
	}
	
	/**
	 * Set the value of a cell.
	 *
	 * @param position Coordinates of the cell.
	 * @param value    Value to set.
	 */
	private void set(Coordinate position, Jewel value)
	{
		// Validate arguments //
		if (position == null) {
			throw new NullPointerException();
		}
		
		set(position.x, position.y, value);
	}
	
	/**
	 * Set the value of a cell.
	 *
	 * @param x     X-coordinate of the cell.
	 * @param y     Y-coordinate of the cell.
	 * @param value Value to set.
	 */
	private void set(int x, int y, Jewel value)
	{
		// Validate arguments //
		if (x < 0 || y < 0) {
			throw new IllegalArgumentException();
		}
		if (x >= width || y >= width) {
			throw new IndexOutOfBoundsException();
		}
		
		int i = y * width + x;
		board[i] = value;
	}
}
