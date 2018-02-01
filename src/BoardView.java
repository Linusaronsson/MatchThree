import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

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
	private static final String WINDOW_TITLE     = "MatchThree";
	
	private Cell[]     board     = null;
	private JButton    button    = new JButton("Confirm");
	private JLabel     label     = new JLabel("");
	private BoardModel model     = null;
	private JMenuItem  newItem   = null;
	private JMenuItem  openItem  = null;
	private JMenuItem  quitItem  = null;
	private JMenuItem  saveItem  = null;
	private JTextField textField = new JTextField(20);
	
	private BufferedImage img1 = null;
	private BufferedImage img2 = null;
	private BufferedImage img3 = null;
	private BufferedImage img4 = null;
	
	private Clip audioSwap = null;
	
	/**
	 * Constructor for `BoardView` MVC view.
	 *
	 * @param model Model to use
	 */
	// TODO: Break into multiple methods?
	// TODO: Call parent constructor?
	public BoardView(BoardModel model)
	{
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
		
		// Prepare graphics for the buttons //
		prepareGraphics();
		
		// Prepare audio-clips //
		prepareAudioClips();
		
		// Initialize components //
		textField.setText(model.getValue());
		textField.setEditable(false);
		updateScore();
		
		// Create content pane //
		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		
		// Construct header //
		JPanel header = new JPanel(new FlowLayout());
		header.add(label);
		header.add(textField);
		header.add(button);
		content.add(header);
		
		// Construct grid //
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
			
			// Get jewel from model //
			BoardModel.Jewel jewel = model.get(x, y);
			
			// Update text to match jewel //
			String value = null;
			switch (jewel) {
				case DIAMOND:  value = "Diamond";  button.setIcon(new ImageIcon(img1)); break;
				case EMERALD:  value = "Emerald";  button.setIcon(new ImageIcon(img2)); break;
				case RUBY:     value = "Ruby";     button.setIcon(new ImageIcon(img3)); break;
				case SAPPHIRE: value = "Sapphire"; button.setIcon(new ImageIcon(img4)); break;
				default: // TODO: Throw runtime error.
			}
			value = "";
			button.setText(value);
			
			// Update color to match jewel //
			Color color = null;
			switch (jewel) {
				case DIAMOND:  color = COLOR_DIAMOND;  break;
				case EMERALD:  color = COLOR_EMERALD;  break;
				case RUBY:     color = COLOR_RUBY;     break;
				case SAPPHIRE: color = COLOR_SAPPHIRE; break;
				default: // TODO: Throw runtime error.
			}
			button.setForeground(color);
			
			// Add button to grid //
			board[i] = button;
			grid.add(button);
		}
		
		// Add grid to pane //
		content.add(grid);
		
		// Update window with content //
		this.setContentPane(content);
		this.pack();
	}
	
	/**
	 * Prepare graphics for the buttons
	 */
	public void prepareGraphics() {
		try {
			img1 = ImageIO.read(new File("src/Diamond.png"));
			img2 = ImageIO.read(new File("src/Emerald.png"));
			img3 = ImageIO.read(new File("src/Ruby.png"));
			img4 = ImageIO.read(new File("src/Sapphire.png"));
		}
		catch(IOException e) {System.out.println("IOException detected!"); }
	}
	
	/**
	 * Prepare audio-clips
	 */
	public void prepareAudioClips() {
		try {
			AudioInputStream audioInputStream = 
                AudioSystem.getAudioInputStream(new File("src/sound1.wav").getAbsoluteFile());
         
			// create clip reference
			audioSwap = AudioSystem.getClip();
         
			// open audioInputStream to the clip
			audioSwap.open(audioInputStream);
			
		} catch (Exception e) { 
			e.printStackTrace(); 
		}
	}
	
	public Clip getAudioSwap() {
		return audioSwap;
	}
	
	/**
	 * Add listener for board cell actions (clicks).
	 *
	 * @param listener Event handler.
	 */
	public void addBoardListener(ActionListener listener)
	{
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
		button.addActionListener(listener);
	}
	
	/**
	 * Add listener for [New] menu item.
	 *
	 * @param listener Event handler.
	 */
	public void addNewListener(ActionListener listener)
	{
		newItem.addActionListener(listener);
	}
	
	/**
	 * Add listener for [Open] menu item.
	 *
	 * @param listener Event handler.
	 */
	public void addOpenListener(ActionListener listener)
	{
		openItem.addActionListener(listener);
	}
	
	/**
	 * Add listener for [Quit] menu item.
	 *
	 * @param listener Event handler.
	 */
	public void addQuitListener(ActionListener listener)
	{
		quitItem.addActionListener(listener);
	}
	
	/**
	 * Add listener for [Save] menu item.
	 *
	 * @param listener Event handler.
	 */
	public void addSaveListener(ActionListener listener)
	{
		saveItem.addActionListener(listener);
	}
	
	/**
	 * Add listener for window events.
	 *
	 * @param listener Event handler.
	 */
	public void addWindowListener(ActionListener listener)
	{
		addWindowListener(listener);
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
		// Create menu bar //
		JMenuBar menuBar = new JMenuBar();
		
		// Create menus //
		JMenu fileMenu = new JMenu("File");
		
		// Create [New] menu item //
		self.newItem = new JMenuItem("New");
		fileMenu.add(self.newItem);
		
		// Create [Open] menu item //
		self.openItem = new JMenuItem("Open");
		fileMenu.add(self.openItem);
		
		// Create [Save] menu item //
		self.saveItem = new JMenuItem("Save");
		fileMenu.add(self.saveItem);
		
		// Create [Quit] menu item //
		self.quitItem = new JMenuItem("Quit");
		fileMenu.add(self.quitItem);
		
		// Add menus to menu bar //
		menuBar.add(fileMenu);
		
		return menuBar;
	}
	
	/**
	 * Set cell activation state.
	 *
	 * @param position  Coordinates of the cell.
	 * @param activated Whether cell is active.
	 */
	public void setCellState(Coordinate position, boolean activated)
	{
		// TODO: Validate arguments.
		
		// Get the affected cell //
		// TODO: Validate row-first or column-first store order of grid (and
		//       make it unambiguous).
		int  width = model.getWidth();
		int  x     = position.x;
		int  y     = position.y;
		int  i     = y * width + x;
		Cell cell  = board[i];
		
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
		updateCell(position.x, position.y);
	}
	
	/**
	 * Update a cell.
	 *
	 * @param x X-coordinate of the cell.
	 * @param y Y-coordinate of the cell.
	 */
	public void updateCell(int x, int y)
	{
		// TODO: Validate arguments.
		
		// Get jewel from model //
		BoardModel.Jewel jewel = model.get(x, y);
		
		// Get button from view //
		// TODO: Add assert or rely less on model consistency.
		int  width = model.getWidth();
		int  i     = y * width + x;
		Cell cell  = board[i];
		
		// Update text to match jewel //
		String value = "";
		if (jewel != null) {
			switch (jewel) {
				case DIAMOND:  value = "Diamond";  cell.setIcon(new ImageIcon(img1)); break;
				case EMERALD:  value = "Emerald";  cell.setIcon(new ImageIcon(img2)); break;
				case RUBY:     value = "Ruby";     cell.setIcon(new ImageIcon(img3)); break;
				case SAPPHIRE: value = "Sapphire"; cell.setIcon(new ImageIcon(img4)); break;
				default: // TODO: Throw runtime error.
			}
			value = "";
		}
		else {
			cell.setIcon(null);
			value = "";
		}
		cell.setText(value);
		
		// Update color to match jewel //
		Color color = Color.BLACK;
		if (jewel != null) {
			switch (jewel) {
				case DIAMOND:  color = COLOR_DIAMOND; 	break;
				case EMERALD:  color = COLOR_EMERALD; 	break;
				case RUBY:     color = COLOR_RUBY; 		break;
				case SAPPHIRE: color = COLOR_SAPPHIRE; 	break;
				default: // TODO: Throw runtime error.
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
