package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import Model.Coordinate;
import Model.Jewel;
import Model.MatchThreeModel;

/**
 * MatchThree game view.
 */
@SuppressWarnings("serial")
public class MatchThreeUI
	extends JPanel
	implements Observer
{
	/** ... */
	private static final String CELL_FONT_NAME = "Helvetica Neue";
	
	/** ... */
	private static final int CELL_FONT_SIZE = 14;
	
	/** ... */
	private static final int CELL_WIDTH = 80;
	
	/** ... */
	private static final Color COLOR_BACKGROUND = new Color(0x11, 0x11, 0x11);
	
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
	private static final String DIR_RESOURCES = "resources";
	
	/** ... */
	private static final int GAP = 2;
	
	/** ... */
	private Clip audioClip = null;
	
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
	private BufferedImage imageDiamondHalfAlpha = null;
	
	/** ... */
	private BufferedImage imageDiamondV2HalfAlpha = null;
	
	/** ... */
	private BufferedImage imageEmeraldHalfAlpha = null;
	
	/** ... */
	private BufferedImage imageEmeraldV2HalfAlpha = null;
	
	/** ... */
	private BufferedImage imageRubyHalfAlpha = null;
	
	/** ... */
	private BufferedImage imageRubyV2HalfAlpha = null;
	
	/** ... */
	private BufferedImage imageSapphireHalfAlpha = null;
	
	/** ... */
	private BufferedImage imageSapphireV2HalfAlpha = null;
	
	/** ... */
	private BufferedImage imageTopazHalfAlpha = null;
	
	/** ... */
	private BufferedImage imageTopazV2HalfAlpha = null;
	
	/** ... */
	private JLabel label = new JLabel("");
	
	/** ... */
	private MatchThreeModel model = null;
	
	/** ... */
	private int jewelVersion = 1;
	
	/**
	 * Constructor for `MatchThreeUI`.
	 *
	 * @param model Model to use
	 */
	// TODO: Break into multiple methods?
	// TODO: Call parent constructor?
	public MatchThreeUI(final MatchThreeModel model) {
		// Validate argument //
		if (model == null) {
			throw new NullPointerException();
		}
		
		// Bind to model //
		this.model = model;
		model.addObserver(this);
		
		// Load external resources //
		initAudio("Swap.wav");
		initGraphics();
		
		// Initialize components //
		updateScore();
		
		// Set layout //
		setLayout(new BorderLayout());
		
		// Construct header //
		JPanel header = new JPanel(new FlowLayout());
		label.setForeground(Color.WHITE);
		label.setFont(new Font(CELL_FONT_NAME, Font.BOLD, 20));
		
		header.add(label);
		header.setBackground(Color.BLACK);
		add(header, BorderLayout.PAGE_START);
		
		// Construct grid //
		JPanel grid = createGrid();
		add(grid, BorderLayout.CENTER);
		
		// Update window with content //
		//setContentPane(content);
		//pack();
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
	 * Create game grid.
	 *
	 * @return ...
	 */
	private JPanel createGrid() {
		// Create grid //
		int width = model.getWidth();
		JPanel grid = new JPanel(new GridLayout(width, width, GAP, GAP));
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
			initButtonDefaultValue(button);
			button.addMouseListener(new MouseAction(button));
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
	 * ...
	 *
	 * @param button ...
	 */
	protected static final void initButtonDefaultValue(final JButton button) {
		button.setOpaque(true);
		button.setBorderPainted(false);
		button.setEnabled(true);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setHorizontalAlignment(SwingConstants.CENTER);
		button.setVerticalAlignment(SwingConstants.CENTER);
		button.setForeground(COLOR_FOREGROUND);
		button.setBackground(Color.BLACK);
		button.setPreferredSize(new Dimension(CELL_WIDTH, CELL_WIDTH));
	}
	
	/**
	 * ...
	 */
	private final class MouseAction
		implements MouseListener
	{
		/** ... */
		private Cell cell = null;
		
		/**
		 * ...
		 *
		 * @param cell ...
		 */
		private MouseAction(final Cell cell) {
			// TODO Auto-generated constructor stub
			this.cell = cell;
		}
		
		@Override
		public void mouseClicked(final MouseEvent e) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void mousePressed(final MouseEvent e) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void mouseReleased(final MouseEvent e) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void mouseEntered(final MouseEvent e) {
			// TODO Auto-generated method stub
			switch (jewelVersion) {
				case 1:
					switch (cell.getMnemonic()) {
						case 0: /** Diamond */
							cell.setIcon(new ImageIcon(imageDiamondHalfAlpha));
							break;
						case 1: /** Emerald */
							cell.setIcon(new ImageIcon(imageEmeraldHalfAlpha));
							break;
						case 2: /** Ruby */
							cell.setIcon(new ImageIcon(imageRubyHalfAlpha));
							break;
						case 3: /** Sapphire */
							cell.setIcon(new ImageIcon(imageSapphireHalfAlpha));
							break;
						case 4: /** Topaz */
							cell.setIcon(new ImageIcon(imageTopazHalfAlpha));
							break;
						default:
							break;
					}
					break;
				case 2:
					switch (cell.getMnemonic()) {
						case 0: /** DiamondV2 */
							cell.setIcon(
								new ImageIcon(imageDiamondV2HalfAlpha)
							);
							break;
						case 1: /** EmeraldV2 */
							cell.setIcon(
								new ImageIcon(imageEmeraldV2HalfAlpha)
							);
							break;
						case 2: /** RubyV2 */
							cell.setIcon(new ImageIcon(imageRubyV2HalfAlpha));
							break;
						case 3: /** SapphireV2 */
							cell.setIcon(
								new ImageIcon(imageSapphireV2HalfAlpha)
							);
							break;
						case 4: /** TopazV2 */
							cell.setIcon(new ImageIcon(imageTopazV2HalfAlpha));
							break;
						default:
							break;
					}
					break;
				default: break;
			}
		}
		
		@Override
		public void mouseExited(final MouseEvent e) {
			switch (jewelVersion) {
				case 1:
					switch (cell.getMnemonic()) {
						case 0: /** Diamond */
							cell.setIcon(new ImageIcon(imageDiamond));
							break;
						case 1: /** Emerald */
							cell.setIcon(new ImageIcon(imageEmerald));
							break;
						case 2: /** Ruby */
							cell.setIcon(new ImageIcon(imageRuby));
							break;
						case 3: /** Sapphire */
							cell.setIcon(new ImageIcon(imageSapphire));
							break;
						case 4: /** Topaz */
							cell.setIcon(new ImageIcon(imageTopaz));
							break;
						default:
							break;
					}
					break;
				case 2:
					switch (cell.getMnemonic()) {
						case 0: /** DiamondV2 */
							cell.setIcon(new ImageIcon(imageDiamondV2));
							break;
						case 1: /** EmeraldV2 */
							cell.setIcon(new ImageIcon(imageEmeraldV2));
							break;
						case 2: /** RubyV2 */
							cell.setIcon(new ImageIcon(imageRubyV2));
							break;
						case 3: /** SapphireV2 */
							cell.setIcon(new ImageIcon(imageSapphireV2));
							break;
						case 4: /** TopazV2 */
							cell.setIcon(new ImageIcon(imageTopazV2));
							break;
						default:
							break;
					}
					break;
				default:
					break;
			}
		}
	}
	
	/**
	 * Load external audio resources.
	 *
	 * @param audio ...
	 */
	private void initAudio(final String audio) {
		// Read audio from file //
		File audioFile = new File(DIR_RESOURCES, audio).getAbsoluteFile();
		AudioInputStream audioStream = null;
		try {
			audioStream = AudioSystem.getAudioInputStream(audioFile);
		} catch (IOException
		| UnsupportedAudioFileException e) {
			System.err.printf(
				"Failed to read \"%s\":%s",
				audioFile,
				System.lineSeparator()
			);
			System.err.printf(
				"\t%s%s",
				e,
				System.lineSeparator()
			);
			return;
		}
		
		// Send audio to system mixer //
		try {
			audioClip = AudioSystem.getClip();
			audioClip.open(audioStream);
		} catch (IOException
		| LineUnavailableException e) {
			System.err.println(e);
			audioClip = null;
			return;
		}
	}
	
	/**
	 * Change images on buttons.
	 *
	 * @param i Jewel version.
	 */
	public void changeSprites(final int i) {
		switch (i) {
			case 1:
				currentDiamond  = imageDiamond;
				currentEmerald  = imageEmerald;
				currentRuby     = imageRuby;
				currentSapphire = imageSapphire;
				currentTopaz    = imageTopaz;
				jewelVersion = i;
				break;
			case 2:
				currentDiamond  = imageDiamondV2;
				currentEmerald  = imageEmeraldV2;
				currentRuby     = imageRubyV2;
				currentSapphire = imageSapphireV2;
				currentTopaz    = imageTopazV2;
				jewelVersion = i;
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
	 * Load external image resources.
	 */
	private void initGraphics() {
		// Load images //
		// TODO: Load new images as well.
		imageDiamond    = loadImage(new File(DIR_RESOURCES, "Diamond.png"));
		imageDiamondV2  = loadImage(new File(DIR_RESOURCES, "Diamond_v2.png"));
		imageEmerald    = loadImage(new File(DIR_RESOURCES, "Emerald.png"));
		imageEmeraldV2  = loadImage(new File(DIR_RESOURCES, "Emerald_v2.png"));
		imageRuby       = loadImage(new File(DIR_RESOURCES, "Ruby.png"));
		imageRubyV2     = loadImage(new File(DIR_RESOURCES, "Ruby_v2.png"));
		imageSapphire   = loadImage(new File(DIR_RESOURCES, "Sapphire.png"));
		imageSapphireV2 = loadImage(new File(DIR_RESOURCES, "Sapphire_v2.png"));
		imageTopaz      = loadImage(new File(DIR_RESOURCES, "Topaz.png"));
		imageTopazV2    = loadImage(new File(DIR_RESOURCES, "Topaz_v2.png"));
		imageDiamondHalfAlpha =
			loadImage(new File(DIR_RESOURCES, "MouseEnteredDiamond.png"));
		imageDiamondV2HalfAlpha =
			loadImage(new File(DIR_RESOURCES, "MouseEnteredDiamond_v2.png"));
		imageEmeraldHalfAlpha =
			loadImage(new File(DIR_RESOURCES, "MouseEnteredEmerald.png"));
		imageEmeraldV2HalfAlpha =
			loadImage(new File(DIR_RESOURCES, "MouseEnteredEmerald_v2.png"));
		imageRubyHalfAlpha =
			loadImage(new File(DIR_RESOURCES, "MouseEnteredRuby.png"));
		imageRubyV2HalfAlpha =
			loadImage(new File(DIR_RESOURCES, "MouseEnteredRuby_v2.png"));
		imageSapphireHalfAlpha =
			loadImage(new File(DIR_RESOURCES, "MouseEnteredSapphire.png"));
		imageSapphireV2HalfAlpha =
			loadImage(new File(DIR_RESOURCES, "MouseEnteredSapphire_v2.png"));
		imageTopazHalfAlpha =
			loadImage(new File(DIR_RESOURCES, "MouseEnteredTopaz.png"));
		imageTopazV2HalfAlpha =
			loadImage(new File(DIR_RESOURCES, "MouseEnteredTopaz_v2.png"));
		// Block images instead of jewels
		
		// Default images
		currentDiamond  = imageDiamond;
		currentEmerald  = imageEmerald;
		currentRuby     = imageRuby;
		currentSapphire = imageSapphire;
		currentTopaz    = imageTopaz;
	}
	
	/**
	 * Load an image resource.
	 *
	 * @param file File to read from.
	 * @return     Loaded image buffer.
	 */
	protected static BufferedImage loadImage(final File file) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			System.err.printf(
				"Failed to read \"%s\":%s",
				file,
				System.lineSeparator()
			);
			System.err.printf(
				"\t%s%s",
				e,
				System.lineSeparator()
			);
		}
		return image;
	}
	
	/**
	 * Play swap audio clip.
	 *
	 * @param audio ...
	 */
	public void playAudio(final String audio) {
		// Rewind and play clip //
		initAudio(audio);
		if (audioClip != null) {
			audioClip.setFramePosition(0);
			audioClip.start();
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
		if (activated) {
			cell.setBackground(COLOR_FOREGROUND);
			cell.setForeground(COLOR_BACKGROUND);
			cell.setContentAreaFilled(true);
		} else {
			cell.setBackground(Color.BLACK);
			cell.setForeground(COLOR_FOREGROUND);
			cell.setContentAreaFilled(false);
		}
	}
	
	/**
	 * ...
	 *
	 * @param j ...
	 * @return  ...
	 */
	private BufferedImage getImage(final Jewel j) {
		switch (j) {
			case DIAMOND:  return currentDiamond;
			case EMERALD:  return currentEmerald;
			case RUBY:     return currentRuby;
			case SAPPHIRE: return currentSapphire;
			case TOPAZ:    return currentTopaz;
			default: throw new IllegalStateException();
		}
	}
	
	/**
	 * ...
	 *
	 * @param j ...
	 * @return  ...
	 */
	private Color getColor(final Jewel j) {
		switch (j) {
			case DIAMOND:  return COLOR_DIAMOND;
			case EMERALD:  return COLOR_EMERALD;
			case RUBY:     return COLOR_RUBY;
			case SAPPHIRE: return COLOR_SAPPHIRE;
			case TOPAZ:    return COLOR_TOPAZ;
			default: throw new IllegalStateException();
		}
	}
	
	/**
	 * ...
	 *
	 * @param j ...
	 * @return  ...
	 */
	private String getStr(final Jewel j) {
		switch (j) {
			case DIAMOND:  return "Diamond";
			case EMERALD:  return "Emerald";
			case RUBY:     return "Ruby";
			case SAPPHIRE: return "Sapphire";
			case TOPAZ:    return "Topaz";
			default: throw new IllegalStateException();
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
			String        text  = getStr(jewel);
			Color         color = getColor(jewel);
			
			cell.setMnemonic(jewel.ordinal());
			
			if (image != null) {
				icon = new ImageIcon(image);
				text = "";
			}
			cell.setIcon(icon);
			cell.setText(text);
			cell.setForeground(color);
		}
	}
	
	/**
	 * Update score label.
	 */
	public void updateScore() {
		int score = model.getScore();
		label.setText("Score: " + score);
	}
	
	/**
	 * ...
	 */
	@Override
	public void update(final Observable o, final Object arg) {
		if (o instanceof MatchThreeModel
		&& arg instanceof MatchThreeModel.CellEvent) {
			MatchThreeModel.CellEvent event = (MatchThreeModel.CellEvent) arg;
			Coordinate c = event.getPos();
			Jewel j = event.getType();
			updateCell(c, j);
		}
	}
}
