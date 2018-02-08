package View;

import GameModes.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
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
import View.MainMenu;
import View.MatchThreeUI;

@SuppressWarnings("serial")
public class SwapView
	extends JPanel
{
	private static final int GAME_SIZE = 6;
	
	private JButton         goBack     = new JButton("Back to Main Menu");
	private MainMenu        mainMenu   = new MainMenu();
	private GameMode        mode       = GameMode.WAITING;
	private MultiplayerMenu mp         = new MultiplayerMenu();
	private JPanel          rightPanel = new JPanel();
	private Singleplayer    sp         = null;
	private JButton         v1btn      = new JButton("Version 1");
	private JButton         v2btn      = new JButton("Version 2");
	private MatchThreeUI    view       = null;
	private WindowState     viewState  = null;
	private Window          window     = null;
	
	public enum GameMode
	{
		MULTIPLAYER,
		SINGLEPLAYER,
		WAITING
	}
	
	public enum WindowState
	{
		MULTIPLAYER_GAME,
		MULTIPLAYER_MENU,
		SINGLEPLAYER_GAME,
		START_MENU
	}
	
	public SwapView()
	{
		changeState(WindowState.START_MENU);
		
		initializeHandlers();
	}
	
	private void changeState(WindowState state)
	{
		removeAll();
		
		//TODO: Fix Layouts for probably all different states. Multiplayer especially.
		switch (state) {
			case START_MENU:
				viewState = WindowState.START_MENU;
				add(mainMenu);
				break;
			case SINGLEPLAYER_GAME:
				viewState = WindowState.SINGLEPLAYER_GAME;
				mode = GameMode.SINGLEPLAYER;
				sp = new Singleplayer(GAME_SIZE);
				sp.setWindow(window);
				
				//Display new panel (the game)
				sp.setLayout(new FlowLayout());
				sp.setBackground(Color.BLACK);
				rightPanel.setLayout(new GridLayout(3, 1));
				rightPanel.add(goBack); //button to go back to main menu panel
				rightPanel.add(v1btn);
				rightPanel.add(v2btn);
				add(sp);
				add(rightPanel);
				setSize(getPerfectDimension());
				view = sp.getView();
				break;
			case MULTIPLAYER_MENU:
				viewState = WindowState.MULTIPLAYER_MENU;
				add(goBack); //button to go back to main menu panel
				add(mp);
				break;
			case MULTIPLAYER_GAME:
				viewState = WindowState.MULTIPLAYER_GAME;
				mode = GameMode.MULTIPLAYER;
				Multiplayer s = null;
				
				try {
					s = new Multiplayer(
						mp.getIp(),
						Integer.parseInt(mp.getPort()),
						GAME_SIZE
					);
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
				add(s);
				setSize(getPerfectDimension());
				view = s.getView();
				break;
			default: //throw something
				break;
		}
		
		revalidate();
		repaint();
	}
	
	/**
	 * Get the dimension for the Main panel that fits the frame
	 * @return
	 */
	private Dimension getPerfectDimension()
	{
		final int extra_width  = 13;
		final int extra_height = 70;
		return new Dimension(
			getPreferredSize().width  + extra_width,
			getPreferredSize().height + extra_height
		);
	}
	
	private void initializeHandlers()
	{
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
			view.changeSprites(1);
		});
		
		v2btn.addActionListener((ActionEvent e) -> {
			view.changeSprites(2);
		});
	}
	
	/**
	 * Set reference to parent window.
	 *
	 * @param window The parent window.
	 */
	public void setWindow(Window window)
	{
		this.window = window;
		
		if (view != null) {
			sp.setWindow(window);
		}
	}
}
