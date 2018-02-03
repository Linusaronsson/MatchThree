package View;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import Model.*;

import java.util.Observer;
import java.util.Observable;

/**
 * MVC view.
 */
@SuppressWarnings("serial")
public class BoardView
	extends JPanel implements Observer
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
	
	private Clip          audioSwap      = null;
	private Cell[]        board          = null;
	private JButton       button         = new JButton("Test");
	private BufferedImage imageDiamond   = null;
	private BufferedImage imageEmerald   = null;
	private BufferedImage imageRuby      = null;
	private BufferedImage imageSapphire  = null;
	private JLabel        label          = new JLabel("");
	private BoardModel    model          = null;
	private JTextField    textField      = new JTextField(20);
	
	/**
	 * Constructor for `BoardView` MVC view.
	 *
	 * @param model Model to use
	 */
	// TODO: Break into multiple methods?
	// TODO: Call parent constructor?
	public BoardView(BoardModel model)
		throws IOException,
		       LineUnavailableException,
		       UnsupportedAudioFileException
	{
		// Validate argument //
		if (model == null) {
			throw new NullPointerException();
		}
		
		this.model = model;
		model.addObserver(this);
		
		// Load external resources //
		prepareAudio();
		prepareGraphics();
		
		// Initialize components //
		textField.setText("Hello, World!");
		textField.setEditable(false);
		updateScore();
		
		this.setLayout(new BorderLayout());
		
		// Construct header //
		JPanel header = new JPanel(new FlowLayout());
		header.add(label);
		header.add(textField);
		header.add(button);
		this.add(header, BorderLayout.PAGE_START);
		
		// Construct grid //
		JPanel grid = createGrid();
		this.add(grid, BorderLayout.CENTER);
		
		// Update window with content //
		//this.setContentPane(content);
		//this.pack();
	}
	
	/**
	 * Display an error message.
	 *
	 * @param message The message to display.
	 */
	public void showError(String message)
	{
		// Validate argument //
		if (message == null) {
			throw new NullPointerException();
		}
		
		JOptionPane.showMessageDialog(this,
		                              message,
		                              message,
		                              JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Display an informative message.
	 *
	 * @param message The message to display.
	 */
	public void showMessage(String message)
	{
		// Validate argument //
		if (message == null) {
			throw new NullPointerException();
		}
		
		JOptionPane.showMessageDialog(this,
		                              message,
		                              message,
		                              JOptionPane.INFORMATION_MESSAGE);
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
	 * Add listener for top button press.
	 *
	 * @param listener Event handler.
	 */
	public void addButtonListener(ActionListener listener)
	{
		// Validate argument //
		if (listener == null) {
			throw new NullPointerException();
		}
		
		button.addActionListener(listener);
	}
	
	/**
	 * Create game grid.
	 */
	private JPanel createGrid()
	{
		// Create grid //
		int width = model.getWidth();
		JPanel grid = new JPanel(new GridLayout(width, width));
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
	 * Play swap audio clip.
	 */
	public void playAudioSwap()
	{
		audioSwap.setFramePosition(0);
		audioSwap.start();
	}
	
	/**
	 * Load external audio resources.
	 */
	private void prepareAudio()
		throws IOException,
		       LineUnavailableException,
		       UnsupportedAudioFileException
	{
		// Read audio from file //
		File audioFile = new File("src/Swap.wav").getAbsoluteFile();
		AudioInputStream audioStream =
			AudioSystem.getAudioInputStream(audioFile);
		
		// Send audio to system mixer //
		audioSwap = AudioSystem.getClip();
		audioSwap.open(audioStream);
	}
	
	/**
	 * Load external image resources.
	 */
	private void prepareGraphics()
		throws IOException
	{
		imageDiamond  = ImageIO.read(new File("src/Diamond.png"));
		imageEmerald  = ImageIO.read(new File("src/Emerald.png"));
		imageRuby     = ImageIO.read(new File("src/Ruby.png"));
		imageSapphire = ImageIO.read(new File("src/Sapphire.png"));
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
	
	private BufferedImage getImage(Jewel j) {
		switch(j) {
			case DIAMOND:  return imageDiamond;
			case EMERALD:  return imageEmerald;
			case RUBY:     return imageRuby;
			case SAPPHIRE: return imageSapphire;
			case TOPAZ:	   return null;
			default: throw new IllegalStateException();
		}
	}
	
	private Color getColor(Jewel j) {
		switch(j) {
			case DIAMOND:  return COLOR_DIAMOND;
			case EMERALD:  return COLOR_EMERALD;
			case RUBY:     return COLOR_RUBY;
			case SAPPHIRE: return COLOR_SAPPHIRE;
			case TOPAZ:	   return COLOR_TOPAZ;
			default: throw new IllegalStateException();
		}
	}
	
	/**
	 * 
	 * @param Jewel
	 * @return String representation of specified Jewel
	 */
	
	private String getStr(Jewel j) {
		switch(j) {
			case DIAMOND:  return "Diamond";
			case EMERALD:  return "Emerald";
			case RUBY:     return "Ruby";
			case SAPPHIRE: return "Sapphire";
			case TOPAZ:	   return "Topaz";
			default: throw new IllegalStateException();
		}
	}
	
	
	/**
	 * Update all cells. (Removed temporarily).
	 
		public void update()
		{
			int width = model.getWidth();
			for (int i = 0; i < width * width; i++) {
				int x = i % width;
				int y = i / width;
				updateCell(x, y);
			}
		}
	*/
	
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
		
		if (jewel != null) {
			cell.setVisible(true);
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
		} else {
			// Make cells invisible when removed from board //
			cell.setVisible(false);
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
	public void update(Observable o, Object arg) {
		if(o instanceof BoardModel &&
		   arg instanceof BoardModel.CellEvent) {
			BoardModel.CellEvent event = (BoardModel.CellEvent) arg;
			Coordinate c = event.getPos();
			Jewel j = event.getType();
			updateCell(c, j);
		}
	}
}
