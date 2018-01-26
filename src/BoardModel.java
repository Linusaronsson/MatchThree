import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.*;

/**
 * ...
 */
class BoardModel
{
	/**
	 * ...
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
		 */
		public static Jewel random()
		{
			return VALUES.get(RANDOM.nextInt(SIZE));
		}
	}
	
	private List<List<Jewel>> board = null;
	
	/**
	 * ...
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
	 * ...
	 */
	public String getValue()
	{
		return "Hello, World!";
	}
	
	/**
	 * ...
	 */
	public Jewel get(int x, int y)
	{
		// TODO: Add assertions to row and column size.
		return board.get(x).get(y);
	}
	
	/**
	 * ...
	 */
	public int getWidth()
	{
		return board.size();
	}
	
	/**
	 * Swap two cells.
	 */
	public boolean swap(int x1, int y1, int x2, int y2)
	{
		// TODO: Validate arguments.
		
		// TODO: Validate move.
		
		// Swap cells //
		Jewel first  = board.get(x1).get(y1);
		Jewel second = board.get(x2).get(y2);
		board.get(x1).set(y1, second);
		board.get(x2).set(y2, first);
		
		return true;
	}
}
