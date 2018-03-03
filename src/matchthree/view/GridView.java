package matchthree.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import matchthree.model.Coordinate;
import matchthree.model.Jewel;
import matchthree.model.MatchThreeModel;
import matchthree.model.Settings.Style;
import matchthree.util.AssetManager;
import matchthree.util.Properties;

/**
 * MatchThree grid view.
 */
@SuppressWarnings({"deprecation", "serial"})
public class GridView
	extends JPanel
	implements Observer
{
	/** ... */
	private static final String CELL_FONT_NAME = "Helvetica Neue";
	
	/** ... */
	private static final int CELL_FONT_SIZE = 14;
	
	/** ... */
	private static final Color COLOR_BACKGROUND =
		Properties.getColorBackground();
	
	/** ... */
	private static final Color COLOR_DIAMOND = new Color(0xB9, 0xF2, 0xFF);
	
	/** ... */
	private static final Color COLOR_EMERALD = new Color(0x50, 0xC8, 0x78);
	
	/** ... */
	private static final Color COLOR_FOREGROUND = new Color(0xEE, 0xEE, 0xEE);
	
	/** ... */
	private static final Color COLOR_RUBY = new Color(0xE0, 0x11, 0x5F);
	
	/** ... */
	private static final Color COLOR_SAPPHIRE = new Color(0x0F, 0x52, 0xBA);
	
	/** ... */
	private static final Color COLOR_TOPAZ = new Color(0xFF, 0xBF, 0x00);
	
	/** ... */
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
	 * @param model      MatchThree model to use.
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
	 * @param listener Event handler.
	 */
	public void addBoardListener(final ActionListener listener) {
		// Validate argument //
		if (listener == null) {
			throw new NullPointerException();
		}
		
		for (Cell cell : board) {
			cell.addActionListener(listener);
		}
	}
	
	/**
	 * Add listener for board cell actions (mouse hovering).
	 *
	 * @param listener Event handler.
	 * @param cell     ...
	 */
	public void addCellHoverListener(
		final MouseListener listener,
		final Cell          cell
	) {
		// Validate argument //
		if (listener == null) {
			throw new NullPointerException();
		}
		
		cell.addMouseListener(listener);
	}
	
	/**
	 * ...
	 *
	 * @return ...
	 */
	public Cell[] getBoard() {
		return board;
	}
	
	/**
	 * Change images on buttons.
	 *
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
		for (Jewel jewel : model.getBoard()) {
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
	 * @return ...
	 */
	private Container createGrid() {
		// Create grid //
		int width = model.getWidth();
		JPanel grid = new JPanel(new GridLayout(width, width, GAP, GAP));
		
		// Set layout //
		Border border = BorderFactory.createCompoundBorder(
			BorderFactory.createLineBorder(
				Color.WHITE,
				1
			),
			BorderFactory.createLineBorder(
				COLOR_BACKGROUND,
				10
			)
		);
		grid.setBorder(border);
					
		// Set background //
		grid.setBackground(COLOR_BACKGROUND);
		
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
			Font font = new Font(CELL_FONT_NAME, Font.PLAIN, CELL_FONT_SIZE);
			button.setFont(font);
			
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
	 * @param style Visual style.
	 */
	private void initGraphics(final Style style) {
		// Load images //
		// TODO: Load new images as well.
		imageDiamond    = AssetManager.loadImage("Diamond.png");
		imageDiamondV2  = AssetManager.loadImage("Diamond_v2.png");
		imageEmerald    = AssetManager.loadImage("Emerald.png");
		imageEmeraldV2  = AssetManager.loadImage("Emerald_v2.png");
		imageRuby       = AssetManager.loadImage("Ruby.png");
		imageRubyV2     = AssetManager.loadImage("Ruby_v2.png");
		imageSapphire   = AssetManager.loadImage("Sapphire.png");
		imageSapphireV2 = AssetManager.loadImage("Sapphire_v2.png");
		imageTopaz      = AssetManager.loadImage("Topaz.png");
		imageTopazV2    = AssetManager.loadImage("Topaz_v2.png");
		// Block images instead of jewels
		
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
	 * @param position  Coordinates of the cell.
	 * @param activated Whether cell is active.
	 */
	public void setCellState(
		final Coordinate position,
		final boolean    activated
	) {
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
			cell.setBackground(COLOR_BACKGROUND);
			cell.setForeground(COLOR_FOREGROUND);
			cell.setContentAreaFilled(true);
		} else {
			cell.setMask(COLOR_BACKGROUND, 0f);
			cell.setBackground(COLOR_BACKGROUND);
			cell.setForeground(COLOR_FOREGROUND);
			cell.setContentAreaFilled(false);
		}
	}
	
	/**
	 * Update all cells.
	 */
	public void update() {
		throw new IllegalStateException();
		
		//int width = model.getWidth();
		//for (int i = 0; i < width * width; i++) {
		//	int x = i % width;
		//	int y = i / width;
		//	updateCell(x, y);
		//}
	}
	
	@Override
	public void update(final Observable o, final Object arg) {
		if (o instanceof MatchThreeModel) {
			if (arg instanceof MatchThreeModel.CellEvent) {
				MatchThreeModel.CellEvent event =
					(MatchThreeModel.CellEvent) arg;
				Coordinate c = event.getPos();
				Jewel j = event.getType();
				updateCell(c, j);
			} else if (arg instanceof MatchThreeModel.GameFinishedEvent) {
				MatchThreeModel.LabelEvent event =
					(MatchThreeModel.LabelEvent) arg;
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
	 * @param position Coordinates of the cell.
	 * @param jewel    ...
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
		
		if (jewel != null) {
			ImageIcon     icon  = null;
			BufferedImage image = getImage(jewel);
			String        text  = getName(jewel);
			Color         color = getColor(jewel);
			
			if (image != null) {
				icon = new ImageIcon(image);
				text = "";
			}
			cell.setIcon(icon);
			cell.setText(text);
			cell.setForeground(color);
		}
	}
}
