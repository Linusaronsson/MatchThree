package GUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import View.BoardView;
import GameModes.*;
import GUI.MainMenu;

public class GUI extends JFrame {
	private WindowState viewState;
	private GameMode mode = GameMode.WAITING;
	private static final String WINDOW_TITLE = "MatchThree";
	private static final int GAME_SIZE = 10;
	private JButton	v1btn = new JButton("Version 1");
	private JButton	v2btn = new JButton("Version 2");
	private JButton       goBack         = new JButton("Back to Main Menu");
	private JMenuItem     newItem        = null;
	private JMenuItem     openItem       = null;
	private JMenuItem     quitItem       = null;
	private JMenuItem     saveItem       = null;
	
	private Singleplayer single = null;
	private Multiplayer multi = null;
	private JPanel 		rightPanel = null;
	private JPanel       mainPanel = null;
	private MainMenu       mainMenu = null;
	private MultiplayerMenu      mp = null;
 
	public GUI() {
		rightPanel = new JPanel();
		mainPanel = new JPanel();
		mainMenu = new MainMenu();
		mp = new MultiplayerMenu();
		
		changeState(WindowState.START_MENU);
		
		initializeHandlers();
		

		// Set application properties //
		// TODO: Avoid calling global state changing method from instance
		//       method.
		setProperties();
		
		// Set window properties //
		setTitle(WINDOW_TITLE);
		setPreferredSize(new Dimension(650, 650));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationByPlatform(true);
		setResizable(true);
		addWindowListener(new WindowListener());
		
		// Add main panel //
		add(mainPanel);
		
		// Set menu bar //
		JMenuBar menuBar = createMenuBar();
		setJMenuBar(menuBar);
		
		pack();
		centerWindow();
		setVisible(true);
	}
	
	public enum GameMode {
		MULTIPLAYER,
		SINGLEPLAYER,
		WAITING
	}
	
	public enum WindowState {
		START_MENU,
		SINGLEPLAYER_GAME,
		MULTIPLAYER_MENU,
		MULTIPLAYER_GAME
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
			  viewState = WindowState.START_MENU;
	          mainPanel.add(mainMenu);
			break;
		case SINGLEPLAYER_GAME:
			viewState = WindowState.SINGLEPLAYER_GAME;
			mode = GameMode.SINGLEPLAYER;
			Singleplayer sp = new Singleplayer(GAME_SIZE);

			//Display new panel (the game)
			sp.setLayout(new FlowLayout());
			sp.setBackground(Color.BLACK);
			rightPanel.setLayout(new GridLayout(3, 1));
			rightPanel.add(goBack); //button to go back to main menu panel
			rightPanel.add(v1btn);
			rightPanel.add(v2btn);
			mainPanel.add(sp);
			mainPanel.add(rightPanel);
			setSize(getPerfectDimension());
			centerWindow();
			single = sp;
			break;
		case MULTIPLAYER_MENU:
			viewState = WindowState.MULTIPLAYER_MENU;
			mainPanel.add(goBack); //button to go back to main menu panel
			mainPanel.add(mp);
			break;
		case MULTIPLAYER_GAME:
		{
			viewState = WindowState.MULTIPLAYER_GAME;
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
			
			s.setLayout(new FlowLayout());
			s.add(goBack); //button to go back to main menu panel
			s.setBackground(Color.BLACK);
			mainPanel.add(s);
			setSize(getPerfectDimension());
			multi = s;
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
		v1btn.addActionListener((ActionEvent e) -> {
			switch(viewState) {
				case SINGLEPLAYER_GAME: single.getView().changeSprites(1); break;
				case MULTIPLAYER_GAME: multi.getView().changeSprites(1); break;
				default: break;
			}
		});
		v2btn.addActionListener((ActionEvent e) -> {
			switch(viewState) {
				case SINGLEPLAYER_GAME: single.getView().changeSprites(2); break;
				case MULTIPLAYER_GAME: multi.getView().changeSprites(2); break;
				default: break;
			}
		});
	}
	
	/**
	 * Get the dimension for the Main panel that fits the frame
	 * @return
	 */
	private Dimension getPerfectDimension() {
		final int extra_width = 13;
		final int extra_height = 70;
		return new Dimension(
				mainPanel.getPreferredSize().width
				+ extra_width, 
				mainPanel.getPreferredSize().height
				+ extra_height
				);
	}
	
	/**
	 * Centers the frame
	 */
	private void centerWindow() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-getSize().width/2, dim.height/2-getSize().height/2);
	}
	
	/**
	 * Create window menu bar.
	 *
	 * @param self View to apply menu bar to.
	 * @return     Menu bar object.
	 */
	// TODO: Research use of `self` parameter name.
	private JMenuBar createMenuBar()
	{
		
		// Create menu bar //
		JMenuBar menuBar = new JMenuBar();
		
		// Create menus //
		JMenu fileMenu = new JMenu("File");
		
		// Create "New" menu item //
		this.newItem = new JMenuItem("New");
		fileMenu.add(this.newItem);
		
		// Create "Open" menu item //
		this.openItem = new JMenuItem("Open");
		fileMenu.add(this.openItem);
		
		// Create "Save" menu item //
		this.saveItem = new JMenuItem("Save");
		fileMenu.add(this.saveItem);
		
		// Create "Quit" menu item //
		this.quitItem = new JMenuItem("Quit");
		fileMenu.add(this.quitItem);
		
		// Add menus to menu bar //
		menuBar.add(fileMenu);
		
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
		try { UIManager.setLookAndFeel(lookAndFeel); }
		catch (ClassNotFoundException e) {}
		catch (InstantiationException e) {}
		catch (IllegalAccessException e) {}
		catch (UnsupportedLookAndFeelException e) {}
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
			showError("â€œOpenâ€� not implemented");
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
			showError("â€œSaveâ€� not implemented");
		}
	}
	
	
}
