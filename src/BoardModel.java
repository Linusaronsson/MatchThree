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
	private int clearMatch(Jewel[] jewels)
	{
		int points = jewels.length * 100;
		score += points;
		return points;
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
	 * @param x1 X-coordinate of the first cell.
	 * @param y1 Y-coordinate of the first cell.
	 * @param x2 X-coordinate of the second cell.
	 * @param y2 Y-coordinate of the second cell.
	 * @return   Whether the swap was successful.
	 */
	public boolean swap(int x1, int y1, int x2, int y2)
	{
		// TODO: Validate arguments.
		
		// Validate move //
		if (x1 == x2 && y1 == y2) {
			return false;
		}
		if (x1 != x2 && y1 != y2) {
			return false;
		}
		
		// Swap cells //
		// TODO: Push cell instead of swapping them.
		Jewel first  = board.get(x1).get(y1);
		Jewel second = board.get(x2).get(y2);
		board.get(x1).set(y1, second);
		board.get(x2).set(y2, first);
		
		// Clear cells //
		clearMatch(new Jewel[] {first, second});
		
		return true;
	}
}
