import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * MVC view.
 */
@SuppressWarnings("serial")
class BoardView
	extends JFrame
{
	private static final String WINDOW_TITLE = "MatchThree";
	
	private List<Cell> board     = null;
	private JButton    button    = new JButton("Confirm");
	private JLabel     label     = new JLabel("");
	private BoardModel model     = null;
	private JMenuItem  newItem   = null;
	private JMenuItem  openItem  = null;
	private JMenuItem  quitItem  = null;
	private JMenuItem  saveItem  = null;
	private JTextField textField = new JTextField(20);
	
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
		grid.setBackground(new Color(0x11, 0x11, 0x11));
		
		// Fill grid //
		board = new ArrayList<Cell>(width * width);
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
			button.setForeground(new Color(0xEE, 0xEE, 0xEE));
			button.setBackground(Color.BLACK);
			button.setPreferredSize(new Dimension(80, 80));
			button.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
			
			// Get jewel from model //
			BoardModel.Jewel jewel = model.get(x, y);
			
			// Update text to match jewel //
			String value = null;
			switch (jewel) {
				case DIAMOND:  value = "Diamond";  break;
				case EMERALD:  value = "Emerald";  break;
				case RUBY:     value = "Ruby";     break;
				case SAPPHIRE: value = "Sapphire"; break;
				default: // TODO: Throw runtime error.
			}
			button.setText(value);
			
			// Update color to match jewel //
			Color color = null;
			switch (jewel) {
				case DIAMOND:  color = new Color(0xB9, 0xF2, 0xFF); break;
				case EMERALD:  color = new Color(0x50, 0xC8, 0x78); break;
				case RUBY:     color = new Color(0xE0, 0x11, 0x5F); break;
				case SAPPHIRE: color = new Color(0x0F, 0x52, 0xBA); break;
				default: // TODO: Throw runtime error.
			}
			button.setForeground(color);
			
			// Add button to grid //
			board.add(button);
			grid.add(button);
		}
		
		// Add grid to pane //
		content.add(grid);
		
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
	 * Add listener for “New” menu item.
	 *
	 * @param listener Event handler.
	 */
	public void addNewListener(ActionListener listener)
	{
		newItem.addActionListener(listener);
	}
	
	/**
	 * Add listener for “Open” menu item.
	 *
	 * @param listener Event handler.
	 */
	public void addOpenListener(ActionListener listener)
	{
		openItem.addActionListener(listener);
	}
	
	/**
	 * Add listener for “Quit” menu item.
	 *
	 * @param listener Event handler.
	 */
	public void addQuitListener(ActionListener listener)
	{
		quitItem.addActionListener(listener);
	}
	
	/**
	 * Add listener for “Save” menu item.
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
		
		// Create “New” menu item //
		self.newItem = new JMenuItem("New");
		fileMenu.add(self.newItem);
		
		// Create “Open” menu item //
		self.openItem = new JMenuItem("Open");
		fileMenu.add(self.openItem);
		
		// Create “Save” menu item //
		self.saveItem = new JMenuItem("Save");
		fileMenu.add(self.saveItem);
		
		// Create “Quit” menu item //
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
		Cell cell  = board.get(x + y * width);
		
		// Set state //
		if (activated) {
			cell.setBackground(new Color(0xEE, 0xEE, 0xEE));
			cell.setForeground(new Color(0x11, 0x11, 0x11));
			cell.setContentAreaFilled(true);
		} else {
			cell.setBackground(Color.BLACK);
			cell.setForeground(new Color(0xEE, 0xEE, 0xEE));
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
		Cell cell  = board.get(x % width + y * width);
		
		// Update text to match jewel //
		String value = "";
		if (jewel != null) {
			switch (jewel) {
				case DIAMOND:  value = "Diamond";  break;
				case EMERALD:  value = "Emerald";  break;
				case RUBY:     value = "Ruby";     break;
				case SAPPHIRE: value = "Sapphire"; break;
				default: // TODO: Throw runtime error.
			}
		}
		cell.setText(value);
		
		// Update color to match jewel //
		Color color = Color.BLACK;
		if (jewel != null) {
			switch (jewel) {
				case DIAMOND:  color = new Color(0xB9, 0xF2, 0xFF); break;
				case EMERALD:  color = new Color(0x50, 0xC8, 0x78); break;
				case RUBY:     color = new Color(0xE0, 0x11, 0x5F); break;
				case SAPPHIRE: color = new Color(0x0F, 0x52, 0xBA); break;
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
