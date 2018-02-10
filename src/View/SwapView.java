package View;

import Controller.MatchThreeController;
import GameModes.Multiplayer;
import GameModes.Singleplayer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
	private static final int GAME_SIZE = 6;
	
	/** ... */
	private JButton back = new JButton("");
	
	/** ... */
	private MainMenu mainMenu = new MainMenu();
	
	/** ... */
	private MultiplayerMenu multiplayerMenu = new MultiplayerMenu();
	
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
	
	/** ... */
	private static final String DIR_RESOURCES = "resources";
	
	/** ... */
	private static final BufferedImage imageV1 = MatchThreeUI.loadImage(new File(DIR_RESOURCES, "V1.png"));
	
	/** ... */
	private static final BufferedImage imageV2 = MatchThreeUI.loadImage(new File(DIR_RESOURCES, "V2.png"));
	
	/** ... */
	private static final BufferedImage imageBack = MatchThreeUI.loadImage(new File(DIR_RESOURCES, "Back.png"));
	
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
		initGraphics();
		setBackground(Color.DARK_GRAY);
	}
	
	private void initGraphics() {
		back.setPreferredSize(new Dimension(50, 50));
		back.setIcon(new ImageIcon(imageBack));
		back.setBackground(Color.DARK_GRAY);
		
		buttonV1.setIcon(new ImageIcon(imageV1));
		buttonV1.setPreferredSize(new Dimension(80, 80));
		buttonV1.setBackground(Color.DARK_GRAY);
		
		buttonV2.setIcon(new ImageIcon(imageV2));
		buttonV2.setPreferredSize(new Dimension(80, 80));
		buttonV2.setBackground(Color.DARK_GRAY);
	}
	
	/**
	 * ...
	 *
	 * @param state ...
	 */
	private void changeState(final WindowState state) {
		removeAll();
		
		// TODO: Fix Layouts for probably all different states. Multiplayer
		//       especially.
		switch (state) {
			case START_MENU:
				add(mainMenu);
				break;
			case SINGLEPLAYER_GAME:
				singleplayer = new Singleplayer(GAME_SIZE);
				singleplayer.setWindow(window);
				
				// TODO: Avoid magic numbers.
				//Display new panel (the game)
				singleplayer.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
				singleplayer.setBackground(Color.BLACK);
				singleplayer.setBorder(BorderFactory.createLineBorder(Color.white));
				singleplayerButtonPanel.add(back);
				singleplayerButtonPanel.add(buttonV1);
				singleplayerButtonPanel.add(buttonV2);
				singleplayerButtonPanel.setBackground(Color.DARK_GRAY);
				singleplayerButtonPanel.setLayout(new GridLayout(3, 1, 10, 10));
				add(singleplayer);
				add(singleplayerButtonPanel);
				view = singleplayer.getView();
				break;
			case MULTIPLAYER_MENU:
				add(back);
				add(multiplayerMenu);
				break;
			case MULTIPLAYER_GAME:
				Multiplayer multiplayer = null;
				
				try {
					multiplayer = new Multiplayer(
						multiplayerMenu.getIp(),
						Integer.parseInt(multiplayerMenu.getPort()),
						GAME_SIZE
					);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				multiplayer.setLayout(new FlowLayout());
				multiplayer.add(back);
				multiplayer.setBackground(Color.BLACK);
				add(multiplayer);
				view = multiplayer.getView();
				break;
			default: //throw something
				break;
		}
		
		if(window != null) {
			window.pack();
			window.centerWindow();
		}
		
		revalidate();
		repaint();
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
		
		multiplayerMenu.addConnectListener((ActionEvent e) -> {
			changeState(WindowState.MULTIPLAYER_GAME);
		});
		
		mainMenu.addMultiplayerListener((ActionEvent e) -> {
			changeState(WindowState.MULTIPLAYER_MENU);
		});
		
		mainMenu.addSingleplayerListener((ActionEvent e) -> {
			changeState(WindowState.SINGLEPLAYER_GAME);
		});
		
		buttonV1.addActionListener((ActionEvent e) -> {
			view.changeSprites(1);
		});
		
		buttonV2.addActionListener((ActionEvent e) -> {
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
			singleplayer.setWindow(window);
		}
	}
}
