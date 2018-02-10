package View;

import Controller.MatchThreeController;
import GameModes.Multiplayer;
import GameModes.Singleplayer;
import Model.Jewel;
import Multiplayer.Message;
import Multiplayer.Server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Maintains multiples views, and swaps between them.
 */
@SuppressWarnings("serial")
public class SwapView
	extends JPanel
{
	/** ... */
	private static final int GAME_SIZE = 6;
	
	/** ... */
	private JButton goBack = new JButton("Back to Main Menu");
	
	/** ... */
	private MainMenu mainMenu = new MainMenu();
	
	/** ... */
	private GameMode mode = GameMode.WAITING;
	
	/** ... */
	private MultiplayerMenu mpMenu = new MultiplayerMenu();
	
	/** ... */
	private JPanel rightPanel = new JPanel();
	
	/** ... */
	private Singleplayer sp = null;
	
	/** .. */
	private Multiplayer mp = null;
	
	/** ... */
	private JButton v1btn = new JButton("Version 1");
	
	/** ... */
	private JButton v2btn = new JButton("Version 2");
	
	/** ... */
	private MatchThreeUI view = null;
	
	/** ... */
	private WindowState viewState = null;
	
	/** ... */
	private Window window  = null;
	
	/**
	 * ...
	 */
	public enum GameMode
	{
		/** ... */
		MULTIPLAYER,
		
		/** ... */
		SINGLEPLAYER,
		
		/** ... */
		WAITING
	}
	
	/**
	 * ...
	 */
	public enum WindowState
	{
		/** ... */
		MULTIPLAYER_GAME,
		
		/** ... */
		MULTIPLAYER_MENU,
		
		/** ... */
		SINGLEPLAYER_GAME,
		
		/** ... */
		START_MENU
	}
	
	/**
	 * ...
	 */
	public SwapView() {
		changeState(WindowState.START_MENU);
		
		initializeHandlers();
	}
	
	/**
	 * ...
	 *
	 * @param state ...
	 */
	private void changeState(final WindowState state) {
		removeAll();
		
		//If a state change was issued during a multiplayer game,
		//then let the other user know that game ended.
		if(viewState == WindowState.MULTIPLAYER_GAME) {
			//TODO
			Server.setInGame(false);
			mp.closeGame();
			mp = null;
		}
		
		viewState = state;
		
		// TODO: Fix Layouts for probably all different states. Multiplayer
		//       especially.
		switch (state) {
			case START_MENU:
				add(mainMenu);
				break;
			case SINGLEPLAYER_GAME:
				mode = GameMode.SINGLEPLAYER;
				sp = new Singleplayer(GAME_SIZE);
				sp.setWindow(window);
				
				// TODO: Avoid magic numbers.
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
				add(goBack); //button to go back to main menu panel
				add(mpMenu);
				break;
			case MULTIPLAYER_GAME:
				mode = GameMode.MULTIPLAYER;
				if(mp == null) {
					//Should be unreachable
					System.err.println("MP was null");
					System.exit(1);
				}
				
				mp.setLayout(new FlowLayout());
				mp.add(goBack); //button to go back to main menu panel
				mp.setBackground(Color.BLACK);
				add(mp);
				window.setSize(1300, 600);
				view = mp.getView(); //remove?
				break;
			default: //throw something
				break;
		}
		
		revalidate();
		repaint();
	}
	
	/**
	 * ...
	 *
	 */
	public void changeToMultiplayer(InetAddress ip, int port, boolean isHost, Jewel[] board) {
		try {
			mp = new Multiplayer(ip, port, isHost, board, GAME_SIZE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mp.setWindow(window);
		changeState(WindowState.MULTIPLAYER_GAME);
	}
	
	/**
	 * ...
	 *
	 * @return ...
	 */
	// TODO: Use mediator pattern instead.
	public MatchThreeController getMatchThreeController() {
		return sp.getMatchThreeController();
	}
	
	/**
	 * Get the dimension for the Main panel that fits the frame.
	 *
	 * @return ...
	 */
	private Dimension getPerfectDimension() {
		final int extraWidth  = 13;
		final int extraHeight = 70;
		return new Dimension(
			getPreferredSize().width  + extraWidth,
			getPreferredSize().height + extraHeight
		);
	}
	
	/**
	 * ...
	 */
	private void initializeHandlers() {
		goBack.addActionListener((ActionEvent e) -> {
			changeState(WindowState.START_MENU);
		});
		
		mpMenu.addConnectListener((ActionEvent e) -> {
			//changeState(WindowState.);
			//Send message to port requesting gameMULTIPLAYER_GAME
			try {
				InetAddress ip = InetAddress.getByName(mpMenu.getIp());
				int port = Integer.parseInt(mpMenu.getPort());
				DatagramSocket client = new DatagramSocket();
				Server.sendDatagram(new Message(Message.MessageType.REQUESTED_GAME), client, ip, port);
	        } catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
	public void setWindow(final Window window) {
		this.window = window;
		
		if (view != null) {
			sp.setWindow(window);
		}
	}
}
