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
	
	private List<List<Jewel>> board = null;
	private int               score = 0;
	
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
		// TODO: Validate arguments.
		
		// Construct board //
		// TODO: Add assertions?
		board = new ArrayList<List<Jewel>>(width);
		for (int x = 0; x < width; x++) {
			board.add(new ArrayList<Jewel>(width));
			for (int y = 0; y < width; y++) {
				Jewel jewel = Jewel.random();
				board.get(x).add(jewel);
			}
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
		// Find matches //
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
			for (int i = x + 1; i < board.size(); i++) {
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
			for (int i = y + 1; i < board.size(); i++) {
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
		// TODO: Add assertions to row and column size.
		return board.get(x).get(y);
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
		return board.size();
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
		// TODO: Validate arguments.
		
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
		// TODO: Add assertions to row and column size.
		board.get(x).set(y, value);
	}
}
