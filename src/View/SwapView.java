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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
	private static final String DIR_RESOURCES = "resources";
	
	/** ... */
	private static final int GAME_SIZE = 6;
	
	/** ... */
	private static final BufferedImage IMAGE_BACK =
		MatchThreeUI.loadImage(new File(DIR_RESOURCES, "Back.png"));
	
	/** ... */
	private static final BufferedImage IMAGE_BACK_HALF_ALPHA =
		MatchThreeUI.loadImage(new File(DIR_RESOURCES, "MouseEnteredBack.png"));
	
	/** ... */
	private static final BufferedImage IMAGE_V1 =
		MatchThreeUI.loadImage(new File(DIR_RESOURCES, "V1.png"));
	
	/** ... */
	private MultiplayerMenu mpMenu = new MultiplayerMenu();
	
	private static final BufferedImage IMAGE_V1_HALF_ALPHA =
		MatchThreeUI.loadImage(new File(DIR_RESOURCES, "MouseEnteredV1.png"));
	
	/** ... */
	private static final BufferedImage IMAGE_V2 =
		MatchThreeUI.loadImage(new File(DIR_RESOURCES, "V2.png"));
	
	/** ... */
	private static final BufferedImage IMAGE_V2_HALF_ALPHA =
		MatchThreeUI.loadImage(new File(DIR_RESOURCES, "MouseEnteredV2.png"));

	private WindowState viewState = WindowState.START_MENU;
	private GameMode mode = GameMode.SINGLEPLAYER;
	
	/** .. */
	private Multiplayer mp = null;
	
	/** ... */
	private JButton back = new JButton("");
	
	/** ... */
	private MainMenu mainMenu = new MainMenu();
	
	/** ... */
	private JPanel singleplayerButtonPanel = new JPanel();
	
	/** ... */
	private Singleplayer singleplayer = null;
	
	/** ... */
	private JButton buttonV1 = new JButton("");
	
	/** ... */
	private JButton buttonV2 = new JButton("");
	
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
		START_MENU
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
	
	/** ... */
	private enum Button
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
	private void setButtonProperties() {
		MatchThreeUI.initButtonDefaultValue(back);
		MatchThreeUI.initButtonDefaultValue(buttonV1);
		MatchThreeUI.initButtonDefaultValue(buttonV2);
		
		// Now specifying properties values to the buttons
		resetButtonImage();
		back.setPreferredSize(new Dimension(50, 50));
		back.setBackground(Color.DARK_GRAY);
		back.addMouseListener(new MouseAction(back));
		back.setMnemonic(Button.BACK.ordinal());
		
		buttonV1.setPreferredSize(new Dimension(80, 80));
		buttonV1.setBackground(Color.DARK_GRAY);
		buttonV1.addMouseListener(new MouseAction(buttonV1));
		buttonV1.setMnemonic(Button.V1.ordinal());
		
		buttonV2.setPreferredSize(new Dimension(80, 80));
		buttonV2.setBackground(Color.DARK_GRAY);
		buttonV2.addMouseListener(new MouseAction(buttonV2));
		buttonV2.setMnemonic(Button.V2.ordinal());
	}
	
	/**
	 * ...
	 */
	private void resetButtonImage() {
		back.setIcon(new ImageIcon(IMAGE_BACK));
		buttonV1.setIcon(new ImageIcon(IMAGE_V1));
		buttonV2.setIcon(new ImageIcon(IMAGE_V2));
	}
	
	/**
	 * ...
	 */
	private final class MouseAction
		implements MouseListener
	{
		/** ... */
		private JButton button = null;
		
		/**
		 * ...
		 *
		 * @param button ...
		 */
		private MouseAction(final JButton button) {
			// TODO Auto-generated constructor stub
			this.button = button;
		}
		
		@Override
		public void mouseClicked(final MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mousePressed(final MouseEvent e) {
			// TODO Auto-generated method stub
			switch (button.getMnemonic()) {
				case 0: /* BACK */
					changeState(WindowState.START_MENU);
					break;
				case 1: /* V1 */
					view.changeSprites(1);
					break;
				case 2: /* V2 */
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
			// TODO Auto-generated method stub
			switch (button.getMnemonic()) {
				case 0: /* BACK */
					button.setIcon(new ImageIcon(IMAGE_BACK_HALF_ALPHA));
					break;
				case 1: /* V1 */
					button.setIcon(new ImageIcon(IMAGE_V1_HALF_ALPHA));
					break;
				case 2: /* V2 */
					button.setIcon(new ImageIcon(IMAGE_V2_HALF_ALPHA));
					break;
				default:
					break;
			}
		}
		
		@Override
		public void mouseExited(final MouseEvent e) {
			// TODO Auto-generated method stub
			switch (button.getMnemonic()) {
				case 0: /* BACK */
					button.setIcon(new ImageIcon(IMAGE_BACK));
					break;
				case 1: /* V1 */
					button.setIcon(new ImageIcon(IMAGE_V1));
					break;
				case 2: /* V2 */
					button.setIcon(new ImageIcon(IMAGE_V2));
					break;
				default:
					break;
			}
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
				singleplayer = new Singleplayer(GAME_SIZE);
				singleplayer.setWindow(window);
				
				// TODO: Avoid magic numbers.
				//Display new panel (the game)
				singleplayer.setLayout(
					new FlowLayout(FlowLayout.CENTER, 10, 10)
				);
				singleplayer.setBackground(Color.BLACK);
				singleplayer.setBorder(
					BorderFactory.createLineBorder(Color.white)
				);
				singleplayerButtonPanel.add(back);
				singleplayerButtonPanel.add(buttonV1);
				singleplayerButtonPanel.add(buttonV2);
				singleplayerButtonPanel.setBackground(Color.DARK_GRAY);
				singleplayerButtonPanel.setLayout(
					new GridLayout(3, 1, 10, 10)
				);
				add(singleplayer);
				add(singleplayerButtonPanel);
				view = singleplayer.getView();
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
			default:
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
		return singleplayer.getMatchThreeController();
	}
	
	/**
	 * ...
	 */
	private void initializeHandlers() {
		back.addActionListener((ActionEvent e) -> {
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
	}
	
	/**
	 * Set reference to parent window.
	 *
	 * @param window The parent window.
	 */
	public void setWindow(final Window window) {
		this.window = window;
		
		if (view != null) {
			singleplayer.setWindow(window);
		}
	}
}
