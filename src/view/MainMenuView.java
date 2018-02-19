package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import util.AssetManager;
import util.Properties;

/**
 * Application start view.
 */
@SuppressWarnings("serial")
public class MainMenuView
	extends JPanel
{
	/** ... */
	private static final String BUTTON_FONT_NAME = Properties.getButtonFontName();
	
	/** ... */
	private static final int BUTTON_FONT_SIZE = Properties.getButtonFontSize();
	
	/** ... */
	private static final int PREFERRED_WIDTH = 400;
	
	/** ... */
	private static final int PREFERRED_HEIGHT = 50;
	
	/** ... */
	private static final int GAP = 10;
	
	/** ... */
	private static final Dimension PREFERRED_DIMENSION = new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT);
	
	/** ... */
	private static final Color COLOR_BACKGROUND = Properties.getColorBackground();
	
	/** ... */
	private static final JLabel LOGO = new JLabel(new ImageIcon(AssetManager.loadImage("M3.png")));
	
	/** ... */
	private Button singleplayer = new Button("SINGLEPLAYER");
	
	/** ... */
	private Button multiplayer = new Button("MULTIPLAYER");
	
	/** ... */
	private Button load = new Button("LOAD");
	
	/** ... */
	private Button highscore = new Button("HIGHSCORE");
	
	/** ... */
	private Button settings = new Button("SETTINGS");
	
	/** ... */
	private Button credits = new Button("CREDITS");
	
	/** ... */
	private Button quit = new Button("QUIT");
	
	/** Update if more buttons added */
	private Button[] buttons = new Button[] 
			{singleplayer, multiplayer, load, highscore, settings, credits, quit};
	
	/** ... */
	private JPanel buttonPanel = new JPanel();
	
	
	
	/**
	 * ...
	 */
	public MainMenuView() {
		// Set layout //
		setLayout(new BorderLayout());
		setBackground(COLOR_BACKGROUND);
		
		// Set logo properties //
		//LOGO.setBackground(Color.WHITE);
		
		// Set button properties //
		setProperties(buttons);
		
		// Set button panel properties // 
		buttonPanel.setLayout(new GridLayout(7, 1, GAP, GAP));
		buttonPanel.setBackground(COLOR_BACKGROUND);
		buttonPanel.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.WHITE, 2),
				BorderFactory.createLineBorder(COLOR_BACKGROUND, 10)
			)
		);
		
		// Assemble button panel //
		buttonPanel = assembleButtons(buttons);
		
		// Assemble view //
		add(LOGO, BorderLayout.NORTH);
		add(new JLabel(emptySpaces()), BorderLayout.WEST);
		add(new JLabel(emptySpaces()), BorderLayout.EAST);
		add(new JLabel(" "), BorderLayout.SOUTH);
		add(buttonPanel, BorderLayout.CENTER);
	}
	
	/**
	 * Increase width of main menu.
	 * 
	 * @return
	 */
	private String emptySpaces() {
		return "                                                                                    ";
	}
	
	/**
	 * Set buttons properties
	 * 
	 * @param buttons
	 */
	private void setProperties(Button[] buttons) {
		for(Button button : buttons) {
			button.setPreferredSize(PREFERRED_DIMENSION);
			button.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(Color.GREEN, 1),
					BorderFactory.createLineBorder(Color.GREEN.darker(), 1)
				),
				BorderFactory.createLineBorder(Color.GREEN, 1)
			));
			Font font = new Font(BUTTON_FONT_NAME, Font.PLAIN, BUTTON_FONT_SIZE);
			button.setFont(font);
		}
	}
	
	/**
	 * Assemble buttons and create button panel
	 * 
	 * @return
	 */
	private JPanel assembleButtons(Button[] buttons) {
		for(Button button : buttons) {
			buttonPanel.add(button);
		}
		return buttonPanel;
	}
	
	/**
	 * 
	 * @return
	 */
	public JPanel getButtonPanel() {
		return buttonPanel;
	}
	
	/**
	 * ...
	 *
	 * @param listener ...
	 */
	public void addMultiplayerListener(final ActionListener listener) {
		multiplayer.addActionListener(listener);
	}
	
	/**
	 * ...
	 *
	 * @param listener ...
	 */
	public void addSingleplayerListener(final ActionListener listener) {
		singleplayer.addActionListener(listener);
	}
	
	/**
	 * ...
	 *
	 * @param listener ...
	 */
	public void addButtonPanelListener(final MouseListener listener) {
		buttonPanel.addMouseListener(listener);
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public Button[] getButtons() {
		return buttons;
	}
}
