package matchthree.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.LayoutManager;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import matchthree.message.CellEvent;
import matchthree.message.GameFinishedEvent;
import matchthree.message.LabelEvent;
import matchthree.model.Coordinate;
import matchthree.model.Jewel;
import matchthree.model.MatchThreeModel;
import matchthree.model.Settings.Style;
import matchthree.util.AssetManager;

/**
 * MatchThree grid view.
 *
 * @author Erik Selstam
 * @author Erik Tran
 * @author Linus Aronsson
 */
@SuppressWarnings({"deprecation", "serial"})
public class GridView
	extends Panel
	implements Observer
{
	/** Active cell color. */
	private static final Color COLOR_ACTIVE_CELL = Color.RED;
	
	/** Background color. */
	private static final Color COLOR_BACKGROUND = new Color(0x33, 0x33, 0x33);
	
	/** Diamond color. */
	private static final Color COLOR_DIAMOND = new Color(0xB9, 0xF2, 0xFF);
	
	/** Emerald color. */
	private static final Color COLOR_EMERALD = new Color(0x50, 0xC8, 0x78);
	
	/** Foreground color. */
	private static final Color COLOR_FOREGROUND = new Color(0xEE, 0xEE, 0xEE);
	
	/** Ruby color. */
	private static final Color COLOR_RUBY = new Color(0xE0, 0x11, 0x5F);
	
	/** Sapphire color. */
	private static final Color COLOR_SAPPHIRE = new Color(0x0F, 0x52, 0xBA);
	
	/** Topaz color. */
	private static final Color COLOR_TOPAZ = new Color(0xFF, 0xBF, 0x00);
	
	/** Cell font. */
	private static final Font FONT_CELL =
		new Font("Helvetica Neue", Font.PLAIN, 14);
	
	/** Grid cell gap in logical pixels. */
	private static final int GRID_GAP = 2;
	
	/** Icon width. */
	private static final int ICON_SIZE = 80;
	
	/** Cell grid. */
	private Cell[] board = null;
	
	/** Diamond icon. */
	private ImageIcon iconDiamond = null;
	
	/** Emerald icon. */
	private ImageIcon iconEmerald = null;
	
	/** Ruby icon. */
	private ImageIcon iconRuby = null;
	
	/** Sapphire icon. */
	private ImageIcon iconSapphire = null;
	
	/** Topaz icon. */
	private ImageIcon iconTopaz = null;
	
	/** Game model. */
	private MatchThreeModel model = null;
	
	/**
	 * Constructor.
	 *
	 * @author Erik Selstam
	 * @author Erik Tran
	 * @param model MatchThree model to use.
	 * @param style Visual style to use.
	 */
	public GridView(final MatchThreeModel model, final Style style) {
		// Validate argument //
		if (model == null) {
			throw new NullPointerException();
		}
		
		// Assign fields //
		this.model = model;
		
		// Bind to model //
		model.addObserver(this);
		
		// Construct grid //
		int width = model.getWidth();
		Container grid = createGrid(width);
		
		// Load external resources //
		setStyle(style);
		
		// Set layout //
		setLayout(new GridLayout());
		
		// Assemble view //
		add(grid);
	}
	
	/**
	 * Add listener for board cell actions (clicks).
	 *
	 * @author Erik Selstam
	 * @param listener Event handler.
	 */
	public void addBoardListener(final ActionListener listener) {
		// Validate argument //
		if (listener == null) {
			throw new NullPointerException();
		}
		
		// Add event listeners //
		for (final Cell cell : board) {
			cell.addActionListener(listener);
		}
	}
	
	/**
	 * Add listener for board cell actions (mouse hovering).
	 *
	 * @author Erik Tran
	 * @param listener Event handler.
	 * @param cell     ...
	 */
	public void addCellHoverListener(
		final MouseListener listener,
		final Cell          cell)
	{
		// Validate argument //
		if (listener == null) {
			throw new NullPointerException();
		}
		
		// Add event listener //
		cell.addMouseListener(listener);
	}
	
	/**
	 * ...
	 *
	 * @author Erik Tran
	 * @return ...
	 */
	public Cell[] getBoard() {
		return board;
	}
	
	/**
	 * Create game grid.
	 *
	 * @author Erik Selstam
	 * @author Erik Tran
	 * @param width Width of the grid.
	 * @return Game grid container.
	 */
	private Container createGrid(final int width) {
		// Create grid //
		Container grid = new SubPanel();
		
		// Set grid layout //
		LayoutManager layout = new GridLayout(width, width, GRID_GAP, GRID_GAP);
		grid.setLayout(layout);
		
		// Fill grid //
		board = new Cell[width * width];
		for (int i = 0; i < width * width; i++) {
			// Get coordinates //
			int x = i % width;
			int y = i / width;
			
			// Create button //
			Cell button = new Cell(x, y);
			board[i] = button;
			
			// Set button properties //
			button.setFont(FONT_CELL);
			
			// Update button state from view //
			update(x, y, model.get(x, y));
			
			// Add button to grid //
			grid.add(button, JLayeredPane.DEFAULT_LAYER);
		}
		
		return grid;
	}
	
	/**
	 * Get the color of a jewel.
	 *
	 * @author Erik Selstam
	 * @param jewel Jewel to get the color of.
	 * @return The color of the jewel.
	 */
	private Color getColor(final Jewel jewel) {
		switch (jewel) {
			case DIAMOND:  return COLOR_DIAMOND;
			case EMERALD:  return COLOR_EMERALD;
			case RUBY:     return COLOR_RUBY;
			case SAPPHIRE: return COLOR_SAPPHIRE;
			case TOPAZ:    return COLOR_TOPAZ;
			default:
				throw new IllegalStateException(
					"Unknown value for `Jewel`"
				);
		}
	}
	
	/**
	 * Get the icon of a jewel.
	 *
	 * @author Erik Selstam
	 * @param jewel Jewel to get the icon of.
	 * @return The icon of the jewel.
	 */
	private ImageIcon getIcon(final Jewel jewel) {
		switch (jewel) {
			case DIAMOND:  return iconDiamond;
			case EMERALD:  return iconEmerald;
			case RUBY:     return iconRuby;
			case SAPPHIRE: return iconSapphire;
			case TOPAZ:    return iconTopaz;
			default:
				throw new IllegalStateException(
					"Unknown value for `Jewel`"
				);
		}
	}
	
	/**
	 * Set theme and update accordingly.
	 *
	 * @author Erik Selstam
	 * @author Erik Tran
	 * @param style Visual style.
	 */
	public void setStyle(final Style style) {
		// Validate argument //
		if (style == null) {
			throw new IllegalArgumentException("`style` must not be null");
		}
		
		// Get file paths //
		// TODO: Use type suitable for paths instead.
		// TODO: Find a more elegant solution to this (wrap in a class?).
		String pathDiamond;
		String pathEmerald;
		String pathRuby;
		String pathSapphire;
		String pathTopaz;
		switch (style) {
			case CLASSIC:
				pathDiamond  = "Version 1/Diamond.png";
				pathEmerald  = "Version 1/Emerald.png";
				pathRuby     = "Version 1/Ruby.png";
				pathSapphire = "Version 1/Sapphire.png";
				pathTopaz    = "Version 1/Topaz.png";
				break;
			case GEMSTONES:
				pathDiamond  = "Version 3/Diamond.png";
				pathEmerald  = "Version 3/Emerald.png";
				pathRuby     = "Version 3/Ruby.png";
				pathSapphire = "Version 3/Sapphire.png";
				pathTopaz    = "Version 3/Topaz.png";
				break;
			case NONE:
				pathDiamond  = null;
				pathEmerald  = null;
				pathRuby     = null;
				pathSapphire = null;
				pathTopaz    = null;
				break;
			case STEEL:
				pathDiamond  = "Version 2/Diamond.png";
				pathEmerald  = "Version 2/Emerald.png";
				pathRuby     = "Version 2/Ruby.png";
				pathSapphire = "Version 2/Sapphire.png";
				pathTopaz    = "Version 2/Topaz.png";
				break;
			default:
				throw new IllegalStateException("Unknown value for `Style`");
		}
		
		// Load images //
		BufferedImage imageDiamond  = AssetManager.loadImage(pathDiamond);
		BufferedImage imageEmerald  = AssetManager.loadImage(pathEmerald);
		BufferedImage imageRuby     = AssetManager.loadImage(pathRuby);
		BufferedImage imageSapphire = AssetManager.loadImage(pathSapphire);
		BufferedImage imageTopaz    = AssetManager.loadImage(pathTopaz);
		
		// Scale images //
		int scale = java.awt.Image.SCALE_SMOOTH;
		int size  = ICON_SIZE;
		Image scaledDiamond  = imageDiamond.getScaledInstance(size, -1, scale);
		Image scaledEmerald  = imageEmerald.getScaledInstance(size, -1, scale);
		Image scaledRuby     = imageRuby.getScaledInstance(size, -1, scale);
		Image scaledSapphire = imageSapphire.getScaledInstance(size, -1, scale);
		Image scaledTopaz    = imageTopaz.getScaledInstance(size, -1, scale);
		
		// Update icon storage //
		iconDiamond  = new ImageIcon(scaledDiamond);
		iconEmerald  = new ImageIcon(scaledEmerald);
		iconRuby     = new ImageIcon(scaledRuby);
		iconSapphire = new ImageIcon(scaledSapphire);
		iconTopaz    = new ImageIcon(scaledTopaz);
		
		// Update view //
		update();
	}
	
	/**
	 * Set cell activation state.
	 *
	 * @author Erik Selstam
	 * @author Erik Tran
	 * @param position  Coordinates of the cell.
	 * @param activated Whether cell is active.
	 */
	public void setCellState(
		final Coordinate position,
		final boolean    activated)
	{
		// Validate arguments //
		if (position == null) {
			throw new NullPointerException();
		}
		int width = model.getWidth();
		if (position.getX() >= width || position.getY() >= width) {
			throw new IndexOutOfBoundsException();
		}
		
		// Get the affected cell //
		int  x    = position.getX();
		int  y    = position.getY();
		int  i    = y * width + x;
		Cell cell = board[i];
		
		// Set state //
		cell.setState(activated);
		if (activated) {
			cell.setMask(COLOR_ACTIVE_CELL, 0.3f);
			cell.setForeground(COLOR_FOREGROUND);
			cell.setContentAreaFilled(true);
		} else {
			cell.setMask(COLOR_BACKGROUND, 0f);
			cell.setForeground(COLOR_FOREGROUND);
			cell.setContentAreaFilled(false);
		}
	}
	
	/**
	 * Update all cells.
	 *
	 * @author Erik Selstam
	 */
	public void update() {
		int width = model.getWidth();
		for (int i = 0; i < width * width; i++) {
			int x = i % width;
			int y = i / width;
			update(x, y);
		}
	}
	
	@Override
	public void update(final Observable o, final Object arg) {
		if (o instanceof MatchThreeModel) {
			if (arg instanceof CellEvent) {
				CellEvent event = (CellEvent) arg;
				Coordinate c = event.getPos();
				Jewel j = event.getType();
				update(c, j);
			} else if (arg instanceof GameFinishedEvent) {
				LabelEvent event = (LabelEvent) arg;
				removeAll();
				GameFinished f = new GameFinished(event.getValue());
				add(f);
				repaint();
				revalidate();
			}
		}
	}
	
	/**
	 * Update a cell.
	 *
	 * @author Erik Selstam
	 * @param position Coordinates of the cell.
	 */
	public void update(final Coordinate position) {
		// Validate argument //
		if (position == null) {
			throw new IllegalArgumentException("`position must not be null");
		}
		
		int x = position.getX();
		int y = position.getY();
		update(x, y);
	}
	
	/**
	 * Update a cell.
	 *
	 * @author Erik Selstam
	 * @param x     X-coordinate of the cell.
	 * @param y     Y-coordinate of the cell.
	 */
	public void update(final int x, final int y) {
		// Validate arguments //
		if (x < 0) {
			throw new IllegalArgumentException("`x` must be at least 0");
		}
		if (y < 0) {
			throw new IllegalArgumentException("`y` must be at least 0");
		}
		
		Jewel jewel = model.get(x, y);
		update(x, y, jewel);
	}
	
	/**
	 * Update a cell.
	 *
	 * @author Erik Selstam
	 * @param position Coordinates of the cell.
	 * @param jewel    Value of the cell.
	 */
	public void update(final Coordinate position, final Jewel jewel) {
		// Validate argument //
		if (position == null) {
			throw new NullPointerException();
		}
		
		int x = position.getX();
		int y = position.getY();
		update(x, y, jewel);
	}
	
	/**
	 * Update a cell.
	 *
	 * @author Erik Selstam
	 * @param x     X-coordinate of the cell.
	 * @param y     Y-coordinate of the cell.
	 * @param jewel ...
	 */
	// TODO: Reduce code duplication.
	public void update(final int x, final int y, final Jewel jewel) {
		// Validate arguments //
		if (x < 0) {
			throw new IllegalArgumentException("`x` must be at least 0");
		}
		if (y < 0) {
			throw new IllegalArgumentException("`y` must be at least 0");
		}
		int width = model.getWidth();
		if (x >= width) {
			throw new IndexOutOfBoundsException("`x` out-of-bounds");
		}
		if (y >= width) {
			throw new IndexOutOfBoundsException("`y` out-of-bounds");
		}
		
		// Get button from view //
		// TODO: Add assert for `width` or rely less on model consistency?
		int  index = y * width + x;
		Cell cell  = board[index];
		
		// Hide cell if empty //
		cell.setVisible(cell != null);
		
		// Set cell label //
		String text = (jewel != null) ? jewel.toString() : "";
		cell.setText(text);
		
		// Set cell color //
		Color color = (jewel != null) ? getColor(jewel) : Color.BLACK;
		cell.setForeground(color);
		
		// Set cell icon //
		if (jewel != null) {
			ImageIcon icon = getIcon(jewel);
			if (icon != null) {
				cell.setText("");
			}
			cell.setIcon(icon);
		}
	}
}
