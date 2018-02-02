import java.util.ArrayList;
import java.util.HashSet;
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
	 * Move type enum.
	 */
	public enum MoveType
	{
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
			board[i] = null;
		}
		fillBoard();
	}
	
	/**
	 * Clear jewels from the board. May leave the board in an inconsistent
	 * state.
	 *
	 * @param chains Array of chains with aligned cells to clear.
	 * @return       Gained score.
	 */
	private int clearChains(Coordinate[][] chains)
	{
		// Validate argument //
		// TODO: Perform validation on array contents as well?
		if (chains == null) {
			throw new NullPointerException();
		}
		
		// Clear chains //
		int points = 0;
		for (Coordinate[] chain : chains) {
			// Clear cells //
			for (Coordinate cell : chain) {
				set(cell, null);
			}
			
			// Count score //
			// TODO: Make score increase exponentially with longer matches.
			points += chain.length * 100;
		}
		
		return points;
	}
	
	/**
	 * Move cells downwards to fill any gaps. May leave the board in an
	 * inconsistent state.
	 */
	private void dropCells()
	{
		// Iterate over columns //
		for (int column = 0; column < width; column++) {
			// Iterate bottom-up //
			for (int row = width - 1; row >= 0; --row) {
				// Move cell down to last empty space //
				for (int index = row; index < width - 1; index++) {
					Coordinate over  = new Coordinate(column, index);
					Coordinate under = new Coordinate(column, index + 1);
					if (get(under) != null) {
						break;
					}
					swap(over, under);
				}
			}
		}
	}
	
	/**
	 * Fill empty spaces in the board. Avoids creating matches. Depending on the
	 * number of types of jewels, this may not be possible.
	 */
	private void fillBoard()
	{
		Random random = new Random();
		for (int i = 0; i < width * width; i++) {
			if (board[i] == null) {
				// Find potential matches //
				int x = i % width;
				int y = i / width;
				HashSet<Jewel> matches = new HashSet<Jewel>();
				matches.add(Jewel.DIAMOND);
				matches.add(Jewel.EMERALD);
				matches.add(Jewel.RUBY);
				matches.add(Jewel.SAPPHIRE);
				matches.add(Jewel.TOPAZ);
				Jewel type = null;
				
				// Find west matches //
				if (x >= 1) {
					type = get(x - 1, y);
					for (int n = 1; n <= x; n++) {
						if (n >= MINIMUM_LENGTH - 1) {
							matches.remove(type);
							break;
						}
						if (get(x - n, y) == null) {
							continue;
						}
						if (get(x - n, y) != type) {
							break;
						}
					}
				}
				
				// Find east matches //
				if (x <= width - 2) {
					type = get(x + 1, y);
					for (int n = 1; n <= x; n++) {
						if (n >= MINIMUM_LENGTH - 1) {
							matches.remove(type);
							break;
						}
						if (get(x + n, y) == null) {
							continue;
						}
						if (get(x + n, y) != type) {
							break;
						}
					}
				}
				
				// Find north matches //
				if (y >= 1) {
					type = get(x, y - 1);
					for (int n = 1; n <= x; n++) {
						if (n >= MINIMUM_LENGTH - 1) {
							matches.remove(type);
							break;
						}
						if (get(x, y - n) == null) {
							continue;
						}
						if (get(x, y - n) != type) {
							break;
						}
					}
				}
				
				// Find south matches //
				if (y <= width - 2) {
					type = get(x, y + 1);
					for (int n = 1; n <= x; n++) {
						if (n >= MINIMUM_LENGTH - 1) {
							matches.remove(type);
							break;
						}
						if (get(x, y + 1) == null) {
							continue;
						}
						if (get(x, y + 1) != type) {
							break;
						}
					}
				}
				
				// Fill cell //
				Jewel jewel = null;
				if (matches.isEmpty()) {
					System.out.println("No possible jewel for (" + x + ", " + y + "), ramdomizing");
					jewel = Jewel.random();
				} else {
					int index = random.nextInt(matches.size());
					int j     = 0;
					for (Jewel kind : matches) {
						if (j == index) {
							jewel = kind;
						}
						j++;
					}
				}
				board[i] = jewel;
			}
		}
	}
	
	/**
	 * Identify chains involving affected cells.
	 *
	 * @param positions Coordinates of cells to check.
	 * @return          Array of chains found.
	 */
	private Coordinate[][] findChains(Coordinate[] positions)
	{
		// Validate argument //
		// TODO: Check for null values inside array?
		if (positions == null) {
			throw new NullPointerException();
		}
		
		// Look for chains //
		List<Coordinate[]> chains = new ArrayList<Coordinate[]>();
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
			if (cellsX.size() >= MINIMUM_LENGTH) {
				Coordinate[] chain = new Coordinate[cellsX.size()];
				chain = cellsX.toArray(chain);
				chains.add(chain);
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
			if (cellsY.size() >= MINIMUM_LENGTH) {
				Coordinate[] chain = new Coordinate[cellsY.size()];
				chain = cellsY.toArray(chain);
				chains.add(chain);
			}
		}
		
		Coordinate[][] out = new Coordinate[chains.size()][];
		return chains.toArray(out);
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
	 * Get the size the board.
	 *
	 * @return The number of cells per axis.
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * Move a cell and clear any generated chains. Leaves the board in a
	 * consistent state.
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
			Coordinate next = new Coordinate(position.x + dx, position.y + dy);
			swap(position, next);
			
			// Save coordinate //
			positions.add(new Coordinate(position.x, position.y));
		}
		positions.add(to);
		
		// Clear cells //
		Coordinate[] out = new Coordinate[positions.size()];
		update(positions.toArray(out));
		
		return MoveType.OK;
	}
	
	/**
	 * Set the value of a cell. May leave the board in an inconsistent state.
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
	 * Set the value of a cell. May leave the board in an inconsistent state.
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
	
	/**
	 * Convenience method for swapping two cells. May leave the board in an
	 * inconsistent state.
	 *
	 * @param first  Coordinates of first cell.
	 * @param second Coordinates of second cell.
	 */
	private void swap(Coordinate first, Coordinate second)
	{
		// Validate arguments //
		if (first == null || second == null) {
			throw new NullPointerException();
		}
		
		Jewel firstType  = get(first);
		Jewel secondType = get(second);
		set(first, secondType);
		set(second, firstType);
	}
	
	/**
	 * Progress board into a consistent state.
	 */
	private void update(Coordinate[] positions)
	{
		// Validate argument //
		// TODO: Add assert on count not exceeding number of cells.
		if (positions == null) {
			throw new NullPointerException();
		}
		
		// Find matches //
		Coordinate[][] chains = findChains(positions);
		
		// Clear matches and adjust score //
		int points = clearChains(chains);
		score += points;
		
		// Drop cells //
		dropCells();
		
		// Refill board //
		fillBoard();
		
		// TODO: Clear board again.
	}
}
