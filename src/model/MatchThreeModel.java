package matchthree.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Random;
import java.util.Set;

/**
 * MatchThree game model.
 */
@SuppressWarnings("deprecation")
public class MatchThreeModel
	extends Observable
{
	/** Minimum chain length. */
	private static final int MINIMUM_LENGTH = 3;
	
	/** Grid. */
	protected Jewel[] board = null;
	
	/** Score counter. */
	protected int score = 0;
	
	/** Moves left. */
	protected int movesLeft = 2;
	
	/** Width of grid. */
	protected int width = 0;
	
	/**
	 * Move type.
	 */
	public enum MoveType
	{
		/** Illegal move. */
		BAD,
		
		/** Canceled move. */
		CANCEL,
		
		/** Successful move. */
		OK;
	}
	
	/**
	 * Cell update event.
	 *
	 * @author Linus
	 */
	public class CellEvent
	{
		/** Cell type. */
		private Jewel cellType;
		
		/** Cell position. */
		private Coordinate position;
		
		/**
		 * Constructor.
		 *
		 * @param position Cell position to embed.
		 * @param cellType Cell type to embed.
		 */
		public CellEvent(final Coordinate position, final Jewel cellType) {
			this.cellType = cellType;
			this.position = position;
		}
		
		/**
		 * Get cell position.
		 *
		 * @return The cell position.
		 */
		public Coordinate getPos() {
			return position;
		}
		
		/**
		 * Get cell type.
		 *
		 * @return The cell type.
		 */
		public Jewel getType() {
			return cellType;
		}
	}
	
	/**
	 * Game completed event.
	 *
	 * @author Linus
	 */
	public class GameFinishedEvent
		extends LabelEvent
	{
		/**
		 * Constructor.
		 *
		 * @param value ...
		 */
		public GameFinishedEvent(final int value) {
			super(value);
		}
	}
	
	/**
	 * ...
	 *
	 * @author Linus
	 */
	public class LabelEvent
	{
		/** ... */
		private int value;
		
		/**
		 * Constructor.
		 *
		 * @param value ...
		 */
		public LabelEvent(final int value) {
			this.value = value;
		}
		
		/**
		 * Get value.
		 *
		 * @return The value.
		 */
		public int getValue() {
			return value;
		}
	}
	
	/**
	 * Move counter update event.
	 *
	 * @author Linus
	 */
	public class MovesLeftEvent
		extends LabelEvent
	{
		/**
		 * Constructor.
		 *
		 * @param value Move counter to embed.
		 */
		public MovesLeftEvent(final int value) {
			super(value);
		}
	}
	
	/**
	 * Score update event.
	 *
	 * @author Linus
	 */
	public class ScoreEvent
		extends LabelEvent
	{
		/**
		 * Constructor.
		 *
		 * @param value Score to embed.
		 */
		public ScoreEvent(final int value) {
			super(value);
		}
	}
	
	/**
	 * Create `MatchThreeModel`.
	 *
	 * @param width Size of the board on one axis in number of cells.
	 */
	public MatchThreeModel(final int width) {
		// Validate argument //
		if (width <= 0) {
			throw new IllegalArgumentException();
		}
		
		// Construct board //
		board = new Jewel[width * width];
		
		this.width = width;
		this.board = board;
		
		// Set initial state //
		init();
	}
	
	/**
	 * Create `MatchThreeModel`.
	 *
	 * @param board Board to use.
	 * @param width Size of the board on one axis in number of cells.
	 */
	// TODO: Remove `width` parameter.
	public MatchThreeModel(final Jewel[] board, final int width) {
		// Validate argument //
		// TODO: Check width.
		if (board == null) {
			throw new NullPointerException();
		}
		
		this.width = width;
		this.board = board;
		
		// Set initial state //
		init();
	}
	
	/**
	 * Clear jewels from the board. May leave the board in an inconsistent
	 * state.
	 *
	 * @param chains Array of chains with aligned cells to clear.
	 * @return       Gained score.
	 */
	private int clearChains(final Coordinate[][] chains) {
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
			// TODO: Make number a constant.
			points += chain.length * 100;
		}
		
		return points;
	}
	
	/**
	 * Move cells downwards to fill any gaps. May leave the board in an
	 * inconsistent state.
	 */
	private void dropCells() {
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
	 * Fill empty spaces in the board. Avoids creating matches. Since the
	 * algorithm is not proven to work in all instances, it may potentially
	 * leave the board in an inconsistent state.
	 */
	private void fill() {
		// Create RNG //
		// TODO: Store this higher in the hierarchy.
		Random random = new Random();
		
		// Fill all cells //
		for (int i = 0; i < width * width; i++) {
			// Skip filled cells //
			if (board[i] != null) {
				continue;
			}
			
			// Get coordinates //
			int x = i % width;
			int y = i / width;
			Coordinate position = new Coordinate(x, y);
			
			// Create list of possible options //
			Set<Jewel> options = new HashSet<Jewel>();
			options.add(Jewel.DIAMOND);
			options.add(Jewel.EMERALD);
			options.add(Jewel.RUBY);
			options.add(Jewel.SAPPHIRE);
			options.add(Jewel.TOPAZ);
			
			// Attempt to fill cell //
			while (true) {
				// Bail-out if options are exhausted //
				if (options.isEmpty()) {
					System.err.printf(
						"No possible jewel for (%d, %d), leaving empty%s",
						x, y, System.lineSeparator()
					);
					set(position, null);
					break;
				}
				
				// Get a random option //
				int   choice  = random.nextInt(options.size());
				int   current = 0;
				Jewel jewel   = null;
				for (Jewel kind : options) {
					if (current == choice) {
						jewel = kind;
					}
					current++;
				}
				
				// Update cell //
				set(position, jewel);
				
				// Look for chains //
				Coordinate[][] chains = findChains(position);
				if (chains.length == 0) {
					break;
				}
				
				// Option exhausted //
				options.remove(jewel);
			}
		}
	}
	
	/**
	 * Identify chains involving a single cell.
	 *
	 * @param position Coordinate of cell to check.
	 * @return         Array of chains found.
	 */
	private Coordinate[][] findChains(final Coordinate position) {
		// Validate argument //
		if (position == null) {
			throw new NullPointerException();
		}
		
		Coordinate[] positions = new Coordinate[] {position};
		return findChains(positions);
	}
	
	/**
	 * Identify chains involving a list of cells.
	 *
	 * @param positions Coordinates of cells to check.
	 * @return          Array of chains found.
	 */
	private Coordinate[][] findChains(final Coordinate[] positions) {
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
			int x = position.getX();
			int y = position.getY();
			
			// Search for matches on X-axis //
			int startX = position.getX();
			int stopX  = position.getX();
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
			int startY = position.getY();
			int stopY  = position.getY();
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
	public Jewel get(final Coordinate position) {
		// Validate argument //
		if (position == null) {
			throw new NullPointerException();
		}
		
		return get(position.getX(), position.getY());
	}
	
	/**
	 * Get the value of a cell.
	 *
	 * @param x X-coordinate of the cell.
	 * @param y Y-coordinate of the cell.
	 * @return  The cell value.
	 */
	public Jewel get(final int x, final int y) {
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
	 * Get a copy of the board contents.
	 *
	 * @return A copy of the board.
	 */
	public Jewel[] getBoard() {
		return board.clone();
	}
	
	/**
	 * Get the number of moves left.
	 *
	 * @return Number of moves left.
	 */
	public int getMovesLeft() {
		return movesLeft;
	}
	
	/**
	 * Get the current score.
	 *
	 * @return The current score.
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Get the size the board.
	 *
	 * @return The number of cells per axis.
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Initialize a new game.
	 */
	public void init() {
		// Reset board //
		// TODO: Is it necessary to null-initialize array?
		for (int i = 0; i < width * width; i++) {
			board[i] = null;
		}
		fill();
		
		// Reset score //
		score = 0;
	}
	
	/**
	 * Move a cell and clear any generated chains. Leaves the board in a
	 * consistent state.
	 *
	 * @param from Source coordinates.
	 * @param to   Destination coordinates.
	 * @return     Whether the move was successful, invalid or canceled.
	 */
	public MoveType move(final Coordinate from, final Coordinate to) {
		// Validate arguments //
		if (from == null || to == null) {
			throw new NullPointerException();
		}
		
		// Validate move //
		if (from.getX() == to.getX() && from.getY() == to.getY()) {
			return MoveType.CANCEL;
		}
		if (from.getX() != to.getX() && from.getY() != to.getY()) {
			return MoveType.BAD;
		}
		
		// Move cell //
		Jewel source = get(from);
		int dx = to.getX() - from.getX();
		int dy = to.getY() - from.getY();
		// CHECKSTYLE.OFF: LeftCurly
		if (dx < 0) { dx = -1; }
		if (dx > 0) { dx = +1; }
		if (dy < 0) { dy = -1; }
		if (dy > 0) { dy = +1; }
		// CHECKSTYLE.ON: LeftCurly
		List<Coordinate> positions = new ArrayList<Coordinate>();
		// TODO: Implement `equals'.
		// TODO: Implement `clone`.
		// TODO: Make a cleaner loop.
		for (Coordinate position = new Coordinate(from.getX(), from.getY());
		position.getX() != to.getX() || position.getY() != to.getY();
		position.setX(position.getX() + dx),
		position.setY(position.getY() + dy)) {
			// Swap cell with neighbor //
			Coordinate next = new Coordinate(
				position.getX() + dx,
				position.getY() + dy
			);
			swap(position, next);
			
			// Save coordinate //
			positions.add(new Coordinate(position.getX(), position.getY()));
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
	public void set(final Coordinate position, final Jewel value) {
		// Validate arguments //
		if (position == null) {
			throw new NullPointerException();
		}
		
		set(position.getX(), position.getY(), value);
	}
	
	/**
	 * Set the value of a cell. May leave the board in an inconsistent state.
	 *
	 * @param x     X-coordinate of the cell.
	 * @param y     Y-coordinate of the cell.
	 * @param value Value to set.
	 */
	public void set(final int x, final int y, final Jewel value) {
		// Validate arguments //
		if (x < 0 || y < 0) {
			throw new IllegalArgumentException();
		}
		if (x >= width || y >= width) {
			throw new IndexOutOfBoundsException();
		}
		
		// Set value //
		int i = y * width + x;
		board[i] = value;
		
		// Notify observers //
		setChanged();
		notifyObservers(new CellEvent(new Coordinate(x, y), value));
	}
	
	/**
	 * ...
	 */
	public void setMovesLeft() {
		// Notify observers //
		if (--movesLeft == 0) {
			setChanged();
			notifyObservers(new String("remove"));
			setChanged();
			notifyObservers(new GameFinishedEvent(score));
		}
		
		setChanged();
		notifyObservers(new MovesLeftEvent(movesLeft));
	}
	
	/**
	 * Convenience method for swapping two cells. May leave the board in an
	 * inconsistent state.
	 *
	 * @param first  Coordinates of first cell.
	 * @param second Coordinates of second cell.
	 */
	private void swap(final Coordinate first, final Coordinate second) {
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
	 *
	 * @param positions Positions to update.
	 */
	private void update(final Coordinate[] positions) {
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
		
		// Notify observers //
		setChanged();
		notifyObservers(new ScoreEvent(score));
		
		// Drop cells //
		dropCells();
		
		// Refill board //
		fill();
		
		// TODO: Clear board again.
	}
}
