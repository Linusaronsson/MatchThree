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
	 * @param jewels Jewels to clear.
	 * @return       Gained score.
	 */
	private int clearMatches(Coordinate position)
	{
		// Get jewel type to match //
		Jewel matchType = get(position);
		
		// Unpack coordinates //
		int x = position.x;
		int y = position.y;
		
		// Search for matches on X-axis //
		int startX = position.x;
		int stopX  = position.x;
		for (int i = x; i >= 0; i--) {
			Jewel cell = get(i, y);
			if (cell == null || !cell.equals(matchType)) {
				break;
			}
			startX = i;
		}
		for (int i = x; i < board.size(); i++) {
			Jewel cell = get(i, y);
			if (cell == null || !cell.equals(matchType)) {
				break;
			}
			stopX = i;
		}
		
		// Search for matches on Y-axis //
		int startY = position.y;
		int stopY  = position.y;
		for (int i = y; i >= 0; i--) {
			Jewel cell = get(x, i);
			if (cell == null || !cell.equals(matchType)) {
				break;
			}
			startY = i;
		}
		for (int i = y; i < board.size(); i++) {
			Jewel cell = get(x, i);
			if (cell == null || !cell.equals(matchType)) {
				break;
			}
			stopY = i;
		}
		
		// Clear matches on X-axis //
		int lengthX = stopX - startX + 1;
		if (lengthX >= MINIMUM_LENGTH) {
			for (int i = startX; i <= stopX; i++) {
				// TODO: Write a setter method.
				board.get(i).set(y, null);
			}
		}
		
		// Clear matches on Y-axis //
		int lengthY = stopY - startY + 1;
		if (lengthY >= MINIMUM_LENGTH) {
			for (int i = startY; i <= stopY; i++) {
				// TODO: Write a setter method.
				board.get(x).set(i, null);
			}
		}
		
		// Adjust score //
		// TODO: Make score increase exponentially with longer matches.
		int points = 0;
		points += lengthX * 100;
		points += lengthY * 100;
		score += points;
		
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
		// TODO: Add assertions to row and column size.
		int x = position.x;
		int y = position.y;
		return board.get(x).get(y);
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
	 * Swap two cells.
	 *
	 * @param position1 Coordinates of the first cell.
	 * @param position2 Coordinates of the second cell.
	 * @return          Whether the swap was successful, invalid or canceled.
	 */
	public MoveType swap(Coordinate position1, Coordinate position2)
	{
		// TODO: Validate arguments.
		
		// Unpack coordinates //
		int x1 = position1.x;
		int y1 = position1.y;
		int x2 = position2.x;
		int y2 = position2.y;
		
		// Validate move //
		if (x1 == x2 && y1 == y2) {
			return MoveType.CANCEL;
		}
		if (x1 != x2 && y1 != y2) {
			return MoveType.BAD;
		}
		
		// Swap cells //
		// TODO: Push cell instead of swapping them.
		Jewel first  = board.get(x1).get(y1);
		Jewel second = board.get(x2).get(y2);
		board.get(x1).set(y1, second);
		board.get(x2).set(y2, first);
		
		// Clear cells //
		clearMatches(position2);
		
		return MoveType.OK;
	}
}
