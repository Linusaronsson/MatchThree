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

/**
 * MVC view.
 */
@SuppressWarnings("serial")
class BoardView
	extends JFrame
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
	private static final String WINDOW_TITLE     = "MatchThree";
	
	private Clip          audioSwap      = null;
	private Cell[]        board          = null;
	private JButton       button         = new JButton("Confirm");
	private BufferedImage imageDiamond   = null;
	private BufferedImage imageEmerald   = null;
	private BufferedImage imageRuby      = null;
	private BufferedImage imageSapphire  = null;
	private JLabel        label          = new JLabel("");
	private BoardModel    model          = null;
	private JMenuItem     newItem        = null;
	private JMenuItem     openItem       = null;
	private JMenuItem     quitItem       = null;
	private JMenuItem     saveItem       = null;
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
		
		// Set application properties //
		// TODO: Avoid calling global state changing method from instance
		//       method.
		setProperties();
		
		// Set window properties //
		this.setTitle(WINDOW_TITLE);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLocationByPlatform(true);
		
		// Set menu bar //
		JMenuBar menuBar = createMenuBar(this);
		this.setJMenuBar(menuBar);
		
		// Load external resources //
		prepareAudio();
		prepareGraphics();
		
		// Initialize components //
		textField.setText("Hello, World!");
		textField.setEditable(false);
		updateScore();
		
		// Create content pane //
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());
		
		// Construct header //
		JPanel header = new JPanel(new FlowLayout());
		header.add(label);
		header.add(textField);
		header.add(button);
		content.add(header, BorderLayout.PAGE_START);
		
		// Construct grid //
		JPanel grid = createGrid();
		content.add(grid, BorderLayout.CENTER);
		
		// Update window with content //
		this.setContentPane(content);
		this.pack();
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
	 * Add listener for "New" menu item.
	 *
	 * @param listener Event handler.
	 */
	public void addNewListener(ActionListener listener)
	{
		// Validate argument //
		if (listener == null) {
			throw new NullPointerException();
		}
		
		newItem.addActionListener(listener);
	}
	
	/**
	 * Add listener for "Open" menu item.
	 *
	 * @param listener Event handler.
	 */
	public void addOpenListener(ActionListener listener)
	{
		// Validate argument //
		if (listener == null) {
			throw new NullPointerException();
		}
		
		openItem.addActionListener(listener);
	}
	
	/**
	 * Add listener for "Quit" menu item.
	 *
	 * @param listener Event handler.
	 */
	public void addQuitListener(ActionListener listener)
	{
		// Validate argument //
		if (listener == null) {
			throw new NullPointerException();
		}
		
		quitItem.addActionListener(listener);
	}
	
	/**
	 * Add listener for "Save" menu item.
	 *
	 * @param listener Event handler.
	 */
	public void addSaveListener(ActionListener listener)
	{
		// Validate argument //
		if (listener == null) {
			throw new NullPointerException();
		}
		
		saveItem.addActionListener(listener);
	}
	
	/**
	 * Add listener for window events.
	 *
	 * @param listener Event handler.
	 */
	public void addWindowListener(ActionListener listener)
	{
		// Validate argument //
		if (listener == null) {
			throw new NullPointerException();
		}
		
		addWindowListener(listener);
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
			updateCell(x, y);
			
			// Add button to grid //
			grid.add(button);
		}
		
		return grid;
	}
	
	/**
	 * Create window menu bar.
	 *
	 * @param self View to apply menu bar to.
	 * @return     Menu bar object.
	 */
	// TODO: Research use of `self` parameter name.
	private static JMenuBar createMenuBar(BoardView self)
	{
		// Validate argument //
		if (self == null) {
			throw new NullPointerException();
		}
		
		// Create menu bar //
		JMenuBar menuBar = new JMenuBar();
		
		// Create menus //
		JMenu fileMenu = new JMenu("File");
		
		// Create "New" menu item //
		self.newItem = new JMenuItem("New");
		fileMenu.add(self.newItem);
		
		// Create "Open" menu item //
		self.openItem = new JMenuItem("Open");
		fileMenu.add(self.openItem);
		
		// Create "Save" menu item //
		self.saveItem = new JMenuItem("Save");
		fileMenu.add(self.saveItem);
		
		// Create "Quit" menu item //
		self.quitItem = new JMenuItem("Quit");
		fileMenu.add(self.quitItem);
		
		// Add menus to menu bar //
		menuBar.add(fileMenu);
		
		return menuBar;
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
	public void prepareAudio()
		throws IOException,
		       LineUnavailableException,
		       UnsupportedAudioFileException
	{
		// Read audio from file //
		File audioFile = new File("src/sound1.wav").getAbsoluteFile();
		AudioInputStream audioStream =
			AudioSystem.getAudioInputStream(audioFile);
		
		// Send audio to system mixer //
		audioSwap = AudioSystem.getClip();
		audioSwap.open(audioStream);
	}
	
	/**
	 * Load external image resources.
	 */
	public void prepareGraphics()
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
	
	/**
	 * Set application properties.
	 */
	private static void setProperties()
	{
		// Use native menu bar on macOS/OS X //
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		// TODO: Does not appear important or is misused.
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Test");
		
		// Set look and feel //
		String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
		try { UIManager.setLookAndFeel(lookAndFeel); }
		catch (ClassNotFoundException e) {}
		catch (InstantiationException e) {}
		catch (IllegalAccessException e) {}
		catch (UnsupportedLookAndFeelException e) {}
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
	 * Update all cells.
	 */
	public void update()
	{
		int width = model.getWidth();
		for (int i = 0; i < width * width; i++) {
			int x = i % width;
			int y = i / width;
			updateCell(x, y);
		}
	}
	
	/**
	 * Update a cell.
	 *
	 * @param position Coordinates of the cell.
	 */
	public void updateCell(Coordinate position)
	{
		// Validate argument //
		if (position == null) {
			throw new NullPointerException();
		}
		
		updateCell(position.x, position.y);
	}
	
	/**
	 * Update a cell.
	 *
	 * @param x X-coordinate of the cell.
	 * @param y Y-coordinate of the cell.
	 */
	// TODO: Reduce code duplication.
	public void updateCell(int x, int y)
	{
		// Validate arguments //
		if (x < 0 || y < 0) {
			throw new IllegalArgumentException();
		}
		int width = model.getWidth();
		if (x >= width || y >= width) {
			throw new IndexOutOfBoundsException();
		}
		
		// Get jewel from model //
		Jewel jewel = model.get(x, y);
		
		// Get button from view //
		// TODO: Add assert for `width` or rely less on model consistency?
		int  i    = y * width + x;
		Cell cell = board[i];
		
		// Update text to match jewel //
		ImageIcon     icon  = null;
		BufferedImage image = null;
		String        text  = "";
		if (jewel != null) {
			switch (jewel) {
				case DIAMOND:  text = "Diamond";  image = imageDiamond;  break;
				case EMERALD:  text = "Emerald";  image = imageEmerald;  break;
				case RUBY:     text = "Ruby";     image = imageRuby;     break;
				case SAPPHIRE: text = "Sapphire"; image = imageSapphire; break;
				case TOPAZ:    text = "Topaz";                           break;
				default: throw new IllegalStateException();
			}
		}
		if (image != null) {
			icon = new ImageIcon(image);
			text = "";
		}
		cell.setIcon(icon);
		cell.setText(text);
		
		// Update color to match jewel //
		Color color = Color.BLACK;
		if (jewel != null) {
			switch (jewel) {
				case DIAMOND:  color = COLOR_DIAMOND;  break;
				case EMERALD:  color = COLOR_EMERALD;  break;
				case RUBY:     color = COLOR_RUBY;     break;
				case SAPPHIRE: color = COLOR_SAPPHIRE; break;
				case TOPAZ:    color = COLOR_TOPAZ;    break;
				default: throw new IllegalStateException();
			}
		}
		cell.setForeground(color);
	}
	
	/**
	 * Update score label.
	 */
	public void updateScore()
	{
		int score = model.getScore();
		label.setText("Score: " + score);
	}
}
