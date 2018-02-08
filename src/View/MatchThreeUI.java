package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import Model.Coordinate;
import Model.Jewel;
import Model.MatchThreeModel;

/**
 * MVC view.
 */
@SuppressWarnings("serial")
public class MatchThreeUI
	extends JPanel
	implements Observer
{
	private static final String CELL_FONT_NAME   = "Helvetica Neue";
	private static final int    CELL_FONT_SIZE   = 14;
	private static final int    CELL_WIDTH       = 80;
	private static final Color  COLOR_BACKGROUND = new Color(0x11, 0x11, 0x11);
	private static final Color  COLOR_DIAMOND    = new Color(0xB9, 0xF2, 0xFF);
	private static final Color  COLOR_EMERALD    = new Color(0x50, 0xC8, 0x78);
	private static final Color  COLOR_FOREGROUND = new Color(0xEE, 0xEE, 0xEE);
	private static final Color  COLOR_RUBY       = new Color(0xE0, 0x11, 0x5F);
	private static final Color  COLOR_SAPPHIRE   = new Color(0x0F, 0x52, 0xBA);
	private static final Color  COLOR_TOPAZ      = new Color(0xFF, 0xBF, 0x00);
	private static final String DIR_RESOURCES    = "resources";
	private static final int    GAP              = 2;
	private static final String WINDOW_TITLE     = "MatchThree";
	
	private Clip            audioSwap        = null;
	private Cell[]          board            = null;
	private BufferedImage   currentDiamond   = null;
	private BufferedImage   currentEmerald   = null;
	private BufferedImage   currentRuby      = null;
	private BufferedImage   currentSapphire  = null;
	private BufferedImage   currentTopaz     = null;
	private BufferedImage   imageDiamond     = null;
	private BufferedImage   imageDiamond_v2  = null;
	private BufferedImage   imageEmerald     = null;
	private BufferedImage   imageEmerald_v2  = null;
	private BufferedImage   imageRuby        = null;
	private BufferedImage   imageRuby_v2     = null;
	private BufferedImage   imageSapphire    = null;
	private BufferedImage   imageSapphire_v2 = null;
	private BufferedImage   imageTopaz       = null;
	private BufferedImage   imageTopaz_v2    = null;
	private JLabel          label            = new JLabel("");
	private MatchThreeModel model            = null;
	private JMenuItem       newItem          = null;
	private JMenuItem       openItem         = null;
	private JMenuItem       quitItem         = null;
	private JMenuItem       saveItem         = null;
	
	/**
	 * Constructor for `MatchThreeUI`.
	 *
	 * @param model Model to use
	 */
	// TODO: Break into multiple methods?
	// TODO: Call parent constructor?
	public MatchThreeUI(MatchThreeModel model)
	{
		// Validate argument //
		if (model == null) {
			throw new NullPointerException();
		}
		
		// Bind to model //
		this.model = model;
		model.addObserver(this);
		
		// Load external resources //
		initAudio();
		initGraphics();
		
		// Initialize components //
		updateScore();
		
		// Set layout //
		setLayout(new BorderLayout());
		
		// Construct header //
		JPanel header = new JPanel(new FlowLayout());
		label.setForeground(Color.WHITE);
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
	public void addBoardListener(ActionListener listener)
	{
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
	 */
	private JPanel createGrid()
	{
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
			Font font = new Font(CELL_FONT_NAME, Font.PLAIN, CELL_FONT_SIZE);
			button.setFont(font);
			
			// Update button state from view //
			updateCell(x, y, model.get(x, y));
			
			// Add button to grid //
			grid.add(button);
		}
		
		return grid;
	}
	
	/**
	 * Load external audio resources.
	 */
	private void initAudio()
	{
		// Read audio from file //
		File audioFile = new File(DIR_RESOURCES, "Swap.wav").getAbsoluteFile();
		AudioInputStream audioStream = null;
		try {
			audioStream = AudioSystem.getAudioInputStream(audioFile);
		} catch (IOException
		       | UnsupportedAudioFileException e)
		{
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
			audioSwap = AudioSystem.getClip();
			audioSwap.open(audioStream);
		} catch (IOException
		       | LineUnavailableException e)
		{
			System.err.println(e);
			audioSwap = null;
			return;
		}
	}
	
	/**
	 * Change images on buttons
	 * @param i, jewel version
	 */
	public void changeSprites(int i)
	{
		switch (i) {
			case 1:
				currentDiamond  = imageDiamond;
				currentEmerald  = imageEmerald;
				currentRuby     = imageRuby;
				currentSapphire = imageSapphire;
				currentTopaz    = imageTopaz;
				break;
			case 2:
				currentDiamond  = imageDiamond_v2;
				currentEmerald  = imageEmerald_v2;
				currentRuby     = imageRuby_v2;
				currentSapphire = imageSapphire_v2;
				currentTopaz    = imageTopaz_v2;
				break;
			default: break;
		}
		int d = 0;
		
		for (Jewel j : model.getBoard()) {
			Cell cell = board[d];
			switch(j) {
				case DIAMOND:  cell.setIcon(new ImageIcon(currentDiamond));  break;
				case EMERALD:  cell.setIcon(new ImageIcon(currentEmerald));  break;
				case RUBY:     cell.setIcon(new ImageIcon(currentRuby));     break;
				case SAPPHIRE: cell.setIcon(new ImageIcon(currentSapphire)); break;
				case TOPAZ:    cell.setIcon(new ImageIcon(currentTopaz));    break;
				default: break;
			}
			d++;
		}
	}
	
	/**
	 * Load external image resources.
	 */
	private void initGraphics()
	{
		// Load images //
		// TODO: Load new images as well.
		imageDiamond     = loadImage(new File(DIR_RESOURCES, "Diamond.png"));
		imageDiamond_v2  = loadImage(new File(DIR_RESOURCES, "Diamond_v2.png"));
		imageEmerald     = loadImage(new File(DIR_RESOURCES, "Emerald.png"));
		imageEmerald_v2  = loadImage(new File(DIR_RESOURCES, "Emerald_v2.png"));
		imageRuby        = loadImage(new File(DIR_RESOURCES, "Ruby.png"));
		imageRuby_v2     = loadImage(new File(DIR_RESOURCES, "Ruby_v2.png"));
		imageSapphire    = loadImage(new File(DIR_RESOURCES, "Sapphire.png"));
		imageSapphire_v2 = loadImage(new File(DIR_RESOURCES, "Sapphire_v2.png"));
		imageTopaz       = loadImage(new File(DIR_RESOURCES, "Topaz.png"));
		imageTopaz_v2    = loadImage(new File(DIR_RESOURCES, "Topaz_v2.png"));
		// Not actually jewels btw, just some block
		
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
	private static BufferedImage loadImage(File file)
	{
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
	 */
	public void playAudioSwap()
	{
		// Rewind and play clip //
		if (audioSwap != null) {
			audioSwap.setFramePosition(0);
			audioSwap.start();
		}
	}
	
	/**
	 * Set cell activation state.
	 *
	 * @param position  Coordinates of the cell.
	 * @param activated Whether cell is active.
	 */
	public void setCellState(Coordinate position, boolean activated)
	{
		// Validate arguments //
		if (position == null) {
			throw new NullPointerException();
		}
		int width = model.getWidth();
		if (position.x >= width || position.y >= width) {
			throw new IndexOutOfBoundsException();
		}
		
		// Get the affected cell //
		int  x    = position.x;
		int  y    = position.y;
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
	
	private BufferedImage getImage(Jewel j)
	{
		switch (j) {
			case DIAMOND:  return currentDiamond;
			case EMERALD:  return currentEmerald;
			case RUBY:     return currentRuby;
			case SAPPHIRE: return currentSapphire;
			case TOPAZ:    return currentTopaz;
			default: throw new IllegalStateException();
		}
	}
	
	private Color getColor(Jewel j)
	{
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
	 *
	 * @param Jewel
	 * @return String representation of specified Jewel
	 */
	private String getStr(Jewel j)
	{
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
	public void update()
	{
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
	 */
	public void updateCell(Coordinate position, Jewel jewel)
	{
		// Validate argument //
		if (position == null) {
			throw new NullPointerException();
		}
		
		updateCell(position.x, position.y, jewel);
	}
	
	/**
	 * Update a cell.
	 *
	 * @param x X-coordinate of the cell.
	 * @param y Y-coordinate of the cell.
	 */
	// TODO: Reduce code duplication.
	public void updateCell(int x, int y, Jewel jewel)
	{
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
	public void updateScore()
	{
		int score = model.getScore();
		label.setText("Score: " + score);
	}
	
	@Override
	public void update(Observable o, Object arg)
	{
		if (o instanceof MatchThreeModel
			&& arg instanceof MatchThreeModel.CellEvent)
		{
			MatchThreeModel.CellEvent event = (MatchThreeModel.CellEvent) arg;
			Coordinate c = event.getPos();
			Jewel j = event.getType();
			updateCell(c, j);
		}
	}
}
