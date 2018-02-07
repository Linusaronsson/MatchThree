package GUI;

import GUI.MainMenu;
import GameModes.*;
import View.BoardView;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetAddress;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GUI
	extends JFrame
{
	private static final int    GAME_SIZE    = 6;
	private static final String WINDOW_TITLE = "MatchThree";
	
	private JButton     goBack    = new JButton("Back to Main Menu");
	private GameMode    mode      = GameMode.WAITING;
	private JMenuItem   newItem   = null;
	private JMenuItem   openItem  = null;
	private JMenuItem   quitItem  = null;
	private JMenuItem   saveItem  = null;
	private WindowState viewState = null;
	
	private MainMenu        mainMenu  = null;
	private JPanel          mainPanel = null;
	private MultiplayerMenu mp        = null;
	
	public GUI()
	{
		mainPanel = new JPanel();
		mainMenu  = new MainMenu();
		mp        = new MultiplayerMenu();
		
		changeState(WindowState.START_MENU);
		
		initializeHandlers();
		
		// Set application properties //
		// TODO: Avoid calling global state changing method from instance
		//       method.
		setProperties();
		
		// Set window properties //
		setTitle(WINDOW_TITLE);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationByPlatform(true);
		setPreferredSize(new Dimension(650, 650));
		setResizable(true);
		
		// Add event listeners //
		addWindowListener(new WindowListener());
		
		// Add main panel //
		add(mainPanel);
		
		// Set menu bar //
		JMenuBar menuBar = createMenuBar(this);
		setJMenuBar(menuBar);
		
		// Update window with content //
		pack();
		
		// Make window visible //
		setVisible(true);
	}
	
	public enum GameMode {
		MULTIPLAYER,
		SINGLEPLAYER,
		WAITING
	}
	
	public enum WindowState {
		MULTIPLAYER_GAME,
		MULTIPLAYER_MENU,
		SINGLEPLAYER_GAME,
		START_MENU
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
	
	private void changeState(WindowState state) {
		mainPanel.removeAll();
		//TODO: Fix Layouts for probably all different states. Multiplayer especially.
		switch(state) {
		case START_MENU:
			mainPanel.add(mainMenu);
			break;
		case SINGLEPLAYER_GAME:
			mode = GameMode.SINGLEPLAYER;
			Singleplayer sp = new Singleplayer(GAME_SIZE);
			
			//Display new panel (the game)
			sp.setLayout(new FlowLayout());
			sp.add(goBack); //button to go back to main menu panel
			mainPanel.add(sp);
			break;
		case MULTIPLAYER_MENU:
			mainPanel.add(goBack); //button to go back to main menu panel
			mainPanel.add(mp);
			break;
		case MULTIPLAYER_GAME:
		{
			mode = GameMode.MULTIPLAYER;
			Multiplayer s = null;
			
			try {
				s = new Multiplayer(
					mp.getIp(),
					Integer.parseInt(mp.getPort()),
					GAME_SIZE);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			setSize(1000, 650);
			s.setLayout(new FlowLayout());
			s.add(goBack); //button to go back to main menu panel
			mainPanel.add(s);
			break;
		}
		default: //throw something
			break;
		}
		
		mainPanel.revalidate();
		mainPanel.repaint();
	}
	
	private void initializeHandlers() {
		goBack.addActionListener((ActionEvent e) -> {
			changeState(WindowState.START_MENU);
		});
		
		mp.addConnectListener((ActionEvent e) -> {
			changeState(WindowState.MULTIPLAYER_GAME);
		});
		
		mainMenu.addMultiplayerListener((ActionEvent e) -> {
			changeState(WindowState.MULTIPLAYER_MENU);
		});
		
		mainMenu.addSingleplayerListener((ActionEvent e) -> {
			changeState(WindowState.SINGLEPLAYER_GAME);
		});
	}
	
	/**
	 * Create window menu bar.
	 *
	 * @param self View to apply menu bar to.
	 * @return     Menu bar object.
	 */
	// TODO: Research use of `self` parameter name.
	private JMenuBar createMenuBar(GUI self)
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
		
		// Add event listeners //
		newItem.addActionListener(new NewListener());
		openItem.addActionListener(new OpenListener());
		quitItem.addActionListener(new QuitListener());
		saveItem.addActionListener(new SaveListener());
		
		return menuBar;
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
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (ClassNotFoundException |
		         InstantiationException |
		         IllegalAccessException |
		         UnsupportedLookAndFeelException e)
		{
			System.err.println(e);
		}
	}
	
	/**
	 * Listens for window events.
	 */
	class WindowListener
		extends WindowAdapter
	{
		public void windowActivated(WindowEvent event) {}
		
		public void windowClosed(WindowEvent event) {}
		
		public void windowClosing(WindowEvent event) {
			// Close windows and free its resources //
			// TODO: Is it necessary to use `view.setVisible(false)` as well?
			dispose();
		}
		
		public void windowDeactivated(WindowEvent event) {}
		
		public void windowDeiconified(WindowEvent event) {}
		
		public void windowGainedFocus(WindowEvent event) {}
		
		public void windowIconified(WindowEvent event) {}
		
		public void windowLostFocus(WindowEvent event) {}
		
		public void windowOpened(WindowEvent event) {}
		
		public void windowStateChanged(WindowEvent event) {}
	}
	
	/**
	 * Listens for "New" menu item.
	 */
	class NewListener
		implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			// Restart the game //
			//restartGame();
		}
	}
	
	/**
	 * Listens for "Open" menu item.
	 */
	class OpenListener
		implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			showError("“Open” not implemented");
		}
	}
	
	/**
	 * Listens for "Quit" menu item.
	 */
	class QuitListener
		implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			// Close window //
			//WindowEvent e = new WindowEvent(view, WindowEvent.WINDOW_CLOSING);
			//dispatchEvent(e);
		}
	}
	
	/**
	 * Listens for "Save" menu item.
	 */
	class SaveListener
		implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			showError("“Save” not implemented");
		}
	}
}
