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
	/** Cell font. */
	private static final Font FONT_CELL =
		new Font("Helvetica Neue", Font.PLAIN, 14);
	
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
	
	/** Icon width. */
	private static final int ICON_SIZE = 80;
	
	/** Active cell color. */
	private static final Color ACTIVE_CELL_COLOR = Color.RED;
	
	/** ... */
	private static final int GAP = 2;
	
	/** ... */
	private Cell[] board = null;
	
	/** ... */
	private BufferedImage currentDiamond = null;
	
	/** ... */
	private BufferedImage currentEmerald = null;
	
	/** ... */
	private BufferedImage currentRuby = null;
	
	/** ... */
	private BufferedImage currentSapphire = null;
	
	/** ... */
	private BufferedImage currentTopaz = null;
	
	/** ... */
	private BufferedImage imageDiamond = null;
	
	/** ... */
	private BufferedImage imageDiamondV2 = null;
	
	/** ... */
	private BufferedImage imageEmerald = null;
	
	/** ... */
	private BufferedImage imageEmeraldV2 = null;
	
	/** ... */
	private BufferedImage imageRuby = null;
	
	/** ... */
	private BufferedImage imageRubyV2 = null;
	
	/** ... */
	private BufferedImage imageSapphire = null;
	
	/** ... */
	private BufferedImage imageSapphireV2 = null;
	
	/** ... */
	private BufferedImage imageTopaz = null;
	
	/** ... */
	private BufferedImage imageTopazV2 = null;
	
	/** ... */
	private MatchThreeModel model = null;
	
	/**
	 * Create `GridView`.
	 *
	 * @author Erik Selstam
	 * @author Erik Tran
	 * @param model MatchThree model to use.
	 * @param style ...
	 */
	public GridView(final MatchThreeModel model, final Style style) {
		// Validate argument //
		if (model == null) {
			throw new NullPointerException();
		}
		
		this.model = model;
		
		// Bind to model //
		model.addObserver(this);
		
		// Set layout //
		setLayout(new GridLayout());
		
		// Load external resources //
		initGraphics(style);
		
		// Construct grid //
		Container grid = createGrid();
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
	 * Change images on buttons.
	 *
	 * @author Erik Tran
	 * @author Erik Selstam
	 * @param style Visual style.
	 */
	public void changeSprites(final Style style) {
		switch (style) {
			case CLASSIC:
				currentDiamond  = imageDiamond;
				currentEmerald  = imageEmerald;
				currentRuby     = imageRuby;
				currentSapphire = imageSapphire;
				currentTopaz    = imageTopaz;
				break;
			case STEEL:
				currentDiamond  = imageDiamondV2;
				currentEmerald  = imageEmeraldV2;
				currentRuby     = imageRubyV2;
				currentSapphire = imageSapphireV2;
				currentTopaz    = imageTopazV2;
				break;
			default: break;
		}
		int index = 0;
		for (final Jewel jewel : model.getBoard()) {
			Cell cell = board[index];
			switch (jewel) {
				case DIAMOND:  cell.setIcon(new ImageIcon(currentDiamond));
				               break;
				case EMERALD:  cell.setIcon(new ImageIcon(currentEmerald));
				               break;
				case RUBY:     cell.setIcon(new ImageIcon(currentRuby));
				               break;
				case SAPPHIRE: cell.setIcon(new ImageIcon(currentSapphire));
				               break;
				case TOPAZ:    cell.setIcon(new ImageIcon(currentTopaz));
				               break;
				default:       break;
			}
			index++;
		}
	}
	
	/**
	 * Create game grid.
	 *
	 * @author Erik Selstam
	 * @author Erik Tran
	 * @return ...
	 */
	private Container createGrid() {
		// Create grid //
		Container grid = new SubPanel();
		
		// Set grid layout //
		int width = model.getWidth();
		LayoutManager layout = new GridLayout(width, width, GAP, GAP);
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
			updateCell(x, y, model.get(x, y));
			
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
	 * @return      The color of the jewel.
	 */
	private Color getColor(final Jewel jewel) {
		switch (jewel) {
			case DIAMOND:  return COLOR_DIAMOND;
			case EMERALD:  return COLOR_EMERALD;
			case RUBY:     return COLOR_RUBY;
			case SAPPHIRE: return COLOR_SAPPHIRE;
			case TOPAZ:    return COLOR_TOPAZ;
			default: throw new IllegalStateException();
		}
	}
	
	/**
	 * Get the image of a jewel.
	 *
	 * @author Erik Selstam
	 * @param jewel Jewel to get the image of.
	 * @return      The image of the jewel.
	 */
	private BufferedImage getImage(final Jewel jewel) {
		switch (jewel) {
			case DIAMOND:  return currentDiamond;
			case EMERALD:  return currentEmerald;
			case RUBY:     return currentRuby;
			case SAPPHIRE: return currentSapphire;
			case TOPAZ:    return currentTopaz;
			default: throw new IllegalStateException();
		}
	}
	
	/**
	 * Get the name of a jewel.
	 *
	 * @author Erik Selstam
	 * @param jewel Jewel to get the name of.
	 * @return      The name of the jewel.
	 */
	private String getName(final Jewel jewel) {
		switch (jewel) {
			case DIAMOND:  return "Diamond";
			case EMERALD:  return "Emerald";
			case RUBY:     return "Ruby";
			case SAPPHIRE: return "Sapphire";
			case TOPAZ:    return "Topaz";
			default: throw new IllegalStateException();
		}
	}
	
	/**
	 * Load external image resources.
	 *
	 * @author Erik Tran
	 * @author Erik Selstam
	 * @param style Visual style.
	 */
	private void initGraphics(final Style style) {
		// Load images //
		// TODO: Load new images as well.
		imageDiamond  = AssetManager.loadImage("Diamond.png");
		imageEmerald  = AssetManager.loadImage("Emerald.png");
		imageRuby     = AssetManager.loadImage("Ruby.png");
		imageSapphire = AssetManager.loadImage("Sapphire.png");
		imageTopaz    = AssetManager.loadImage("Topaz.png");
		
		switch (style) {
			case CLASSIC:
				currentDiamond  = imageDiamond;
				currentEmerald  = imageEmerald;
				currentRuby     = imageRuby;
				currentSapphire = imageSapphire;
				currentTopaz    = imageTopaz;
				break;
			case STEEL:
				currentDiamond  = imageDiamondV2;
				currentEmerald  = imageEmeraldV2;
				currentRuby     = imageRubyV2;
				currentSapphire = imageSapphireV2;
				currentTopaz    = imageTopazV2;
				break;
			default: break;
		}
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
			cell.setMask(ACTIVE_CELL_COLOR, 0.3f);
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
		throw new IllegalStateException();
		
		/*
		int width = model.getWidth();
		for (int i = 0; i < width * width; i++) {
			int x = i % width;
			int y = i / width;
			updateCell(x, y);
		}
		*/
	}
	
	@Override
	public void update(final Observable o, final Object arg) {
		if (o instanceof MatchThreeModel) {
			if (arg instanceof CellEvent) {
				CellEvent event = (CellEvent) arg;
				Coordinate c = event.getPos();
				Jewel j = event.getType();
				updateCell(c, j);
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
	 * @param jewel    Value of the cell.
	 */
	public void updateCell(final Coordinate position, final Jewel jewel) {
		// Validate argument //
		if (position == null) {
			throw new NullPointerException();
		}
		
		updateCell(position.getX(), position.getY(), jewel);
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
	public void updateCell(final int x, final int y, final Jewel jewel) {
		// Validate arguments //
		if (x < 0 || y < 0) {
			throw new IllegalArgumentException();
		}
		int width = model.getWidth();
		if (x >= width || y >= width) {
			throw new IndexOutOfBoundsException();
		}
		
		// Get button from view //
		// TODO: Add assert for `width` or rely less on model consistency?
		int  i    = y * width + x;
		Cell cell = board[i];
		
		// Hide cell if empty //
		cell.setVisible(cell != null);
		
		// Set cell label //
		String text = (jewel != null) ? getName(jewel) : "";
		cell.setText(text);
		
		// Set cell color //
		Color color = (jewel != null) ? getColor(jewel) : Color.BLACK;
		cell.setForeground(color);
		
		// Set cell icon //
		if (jewel != null) {
			ImageIcon     icon  = null;
			BufferedImage image = getImage(jewel);
			if (image != null) {
				Image scaledImage = image.getScaledInstance(
					ICON_SIZE,
					-1,
					java.awt.Image.SCALE_SMOOTH
				);
				icon = new ImageIcon(scaledImage);
				cell.setText("");
			}
			cell.setIcon(icon);
		}
	}
}
