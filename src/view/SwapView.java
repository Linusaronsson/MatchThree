package view;

import controller.MatchThreeController;
import gamemodes.Multiplayer;
import gamemodes.Singleplayer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import model.Jewel;
import model.MatchThreeModel;
import multiplayer.Message;
import multiplayer.Server;

/**
 * Maintains multiples views, and swaps between them.
 */
@SuppressWarnings("serial")
public class SwapView
	extends JPanel
{
	/** ... */
	private static final String DIR_RESOURCES = "resources";
	
	/** ... */
	private static final int GAME_SIZE = 6;
	
	/** ... */
	private static final BufferedImage IMAGE_BACK =
		MatchThreeUI.loadImage(new File(DIR_RESOURCES, "Back.png"));
	
	/** ... */
	private static final BufferedImage IMAGE_V1 =
		MatchThreeUI.loadImage(new File(DIR_RESOURCES, "V1.png"));
	
	/** ... */
	private static final BufferedImage IMAGE_V2 =
		MatchThreeUI.loadImage(new File(DIR_RESOURCES, "V2.png"));

	/** ... */
	private MultiplayerMenu mpMenu = new MultiplayerMenu();
	
	/** ... */
	private WindowState viewState = WindowState.START_MENU;
	
	/** ... */
	private GameMode mode = GameMode.WAITING;
	
	/** ... */
	private Multiplayer mp = null;
	
	/** ... */
	private Cell back = new Cell(0);
	
	/** ... */
	private MainMenu mainMenu = new MainMenu();
	
	/** ... */
	private ScoreMenu scoreMenu = new ScoreMenu();
	
	/** ... */
	private MultiplayerMenu multiplayerMenu = new MultiplayerMenu();
	
	/** ... */
	private JPanel singleplayerButtonPanel = new JPanel();
	
	/** ... */
	private Singleplayer sp = null;
	
	/** ... */
	private Cell buttonV1 = new Cell(0);
	
	/** ... */
	private Cell buttonV2 = new Cell(0);
	
	/** ... */
	private MatchThreeUI view = null;
	
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
		START_MENU,

		/** ... */
		SCORE_MENU
	}
	
	/**
	 * ...
	 */
	public enum Button
	{
		/** ... */
		BACK,
		
		/** ... */
		V1,
		
		/** ... */
		V2
	}
	
	/**
	 * ...
	 */
	public SwapView() {
		changeState(WindowState.START_MENU);
		
		initializeHandlers();
		setButtonProperties();
		setBackground(Color.DARK_GRAY);
	}
	
	/**
	 * ...
	 */
	private void setButtonProperties() {
		MatchThreeUI.initButtonDefaultValue(back);
		MatchThreeUI.initButtonDefaultValue(buttonV1);
		MatchThreeUI.initButtonDefaultValue(buttonV2);
		
		// Now specifying properties values to the buttons
		back.setIcon(new ImageIcon(IMAGE_BACK));
		back.setPreferredSize(new Dimension(50, 50));
		back.setBackground(Color.DARK_GRAY);
		back.setMnemonic(Button.BACK.ordinal());
		
		buttonV1.setIcon(new ImageIcon(IMAGE_V1));
		buttonV1.setPreferredSize(new Dimension(80, 80));
		buttonV1.setBackground(Color.DARK_GRAY);
		buttonV1.setMnemonic(Button.V1.ordinal());
		
		buttonV2.setIcon(new ImageIcon(IMAGE_V2));
		buttonV2.setPreferredSize(new Dimension(80, 80));
		buttonV2.setBackground(Color.DARK_GRAY);
		buttonV2.setMnemonic(Button.V2.ordinal());
	}
	
	/**
	 * ...
	 */
	private void resetButtonImage() {
		back.setAlpha(1f);
		buttonV1.setAlpha(1f);
		buttonV2.setAlpha(1f);
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
		 * @param button ...
		 */
		private MouseAction(final Cell cell) {
			this.cell = cell;
		}
		
		@Override
		public void mouseClicked(final MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mousePressed(final MouseEvent e) {
			switch(cell.getMnemonic()) {
				case 0:
					changeState(WindowState.START_MENU);
					break;
				case 1:
					view.changeSprites(1);
					break;
				case 2:
					view.changeSprites(2);
					break;
				default:
					break;
			}
		}
		
		@Override
		public void mouseReleased(final MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseEntered(final MouseEvent e) {
			cell.setAlpha(0.5f);
		}
		
		@Override
		public void mouseExited(final MouseEvent e) {
			cell.setAlpha(1f);
		}
	}
	
	/**
	 * ...
	 *
	 * @param state ...
	 */
	private void changeState(final WindowState state) {
		removeAll();
		resetButtonImage();
		
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
				sp = new Singleplayer(new MatchThreeModel(GAME_SIZE));
				sp.setWindow(window);
				
				// TODO: Avoid magic numbers.
				//Display new panel (the game)
				sp.setLayout(
					new FlowLayout(FlowLayout.CENTER, 10, 10)
				);
				sp.setBackground(Color.BLACK);
				sp.setBorder(
					BorderFactory.createLineBorder(Color.WHITE)
				);
				singleplayerButtonPanel.add(back);
				singleplayerButtonPanel.add(buttonV1);
				singleplayerButtonPanel.add(buttonV2);
				singleplayerButtonPanel.setBackground(Color.DARK_GRAY);
				singleplayerButtonPanel.setLayout(
					new GridLayout(3, 1, 10, 10)
				);
				add(sp);
				add(singleplayerButtonPanel);
				view = sp.getView();
				break;
			case MULTIPLAYER_MENU:
				add(back); //button to go back to main menu panel
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
				mp.add(back); //button to go back to main menu panel
				mp.setBackground(Color.BLACK);
				add(mp);
				window.setSize(1300, 600);
				view = mp.getView(); //remove?
				break;
		        case SCORE_MENU:
			        add(scoreMenu);
				break;
				
			default: //throw something
				throw new IllegalStateException();
		}
		
		if (window != null) {
			window.pack();
			window.centerWindow();
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
	 * ...
	 */
	private void initializeHandlers() {
		back.addMouseListener(new MouseAction(back));
		buttonV1.addMouseListener(new MouseAction(buttonV1));
		buttonV2.addMouseListener(new MouseAction(buttonV2));
		
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

		scoreMenu.addRefreshListener((ActionEvent e) -> {
			changeState(WindowState.SCORE_MENU);
		});
		
		mainMenu.addMultiplayerListener((ActionEvent e) -> {
			changeState(WindowState.MULTIPLAYER_MENU);
		});
		
		mainMenu.addSingleplayerListener((ActionEvent e) -> {
			changeState(WindowState.SINGLEPLAYER_GAME);
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
