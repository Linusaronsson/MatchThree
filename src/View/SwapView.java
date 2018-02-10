package View;

import Controller.MatchThreeController;
import GameModes.Multiplayer;
import GameModes.Singleplayer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
	
	/** ... */
	private static final BufferedImage imageV1HalfAlpha = MatchThreeUI.loadImage(new File(DIR_RESOURCES, "MouseEnteredV1.png"));
	
	/** ... */
	private static final BufferedImage imageV2HalfAlpha = MatchThreeUI.loadImage(new File(DIR_RESOURCES, "MouseEnteredV2.png"));
	
	/** ... */
	private static final BufferedImage imageBackHalfAlpha = MatchThreeUI.loadImage(new File(DIR_RESOURCES, "MouseEnteredBack.png"));
	
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
	
	private enum Button {
		BACK,
		V1,
		V2
	}
	
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
	
	private void resetButtonImage() {
		back.setIcon(new ImageIcon(imageBack));
		buttonV1.setIcon(new ImageIcon(imageV1));
		buttonV2.setIcon(new ImageIcon(imageV2));
	}
	
	private class MouseAction 
	implements MouseListener
	{
		private JButton button = null;
		private MouseAction(JButton button) {
			// TODO Auto-generated constructor stub
			this.button = button;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			switch(button.getMnemonic()) {
			case 0: /* BACK */ button.setIcon(new ImageIcon(imageBackHalfAlpha)); break;
			case 1: /* V1 */   button.setIcon(new ImageIcon(imageV1HalfAlpha));   break;
			case 2: /* V2 */   button.setIcon(new ImageIcon(imageV2HalfAlpha));   break;
				default: break;
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			switch(button.getMnemonic()) {
			case 0: /* BACK */ button.setIcon(new ImageIcon(imageBack)); break;
			case 1: /* V1 */   button.setIcon(new ImageIcon(imageV1));   break;
			case 2: /* V2 */   button.setIcon(new ImageIcon(imageV2));   break;
				default: break;
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
