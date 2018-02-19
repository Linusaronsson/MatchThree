package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * Application start view.
 */
@SuppressWarnings("serial")
public class MainMenuView
	extends JPanel
{
	/** ... */
	private static final String BUTTON_FONT_NAME = "Helvetica Neue";
	
	/** ... */
	private static final int BUTTON_FONT_SIZE = 40;
	
	/** ... */
	private static final int PREFERRED_WIDTH = 400;
	
	/** ... */
	private static final int PREFERRED_HEIGHT = 40;
	
	/** ... */
	private static final int GAP = 10;
	
	/** ... */
	private static final Dimension PREFERRED_DIMENSION = new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT);
	
	/** ... */
	private static final Color COLOR_BACKGROUND = Color.DARK_GRAY.darker();
	
	/** ... */
	private static final Color COLOR_BORDER = Color.WHITE;
	
	/** ... */
	//private static final JLabel LOGO = new JLabel(new ImageIcon(AssetManager.loadImage("logo.png")));
	
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
		setProperties(singleplayer, multiplayer, load, highscore, settings, credits, quit);
		
		// Create and assemble button panel //
		JPanel buttonPanel = assembleButtons();
		
		// Assemble view //
		//add(LOGO);
		add(buttonPanel, BorderLayout.WEST);
		
	}
	
	/**
	 * Set buttons properties
	 * 
	 * @param buttons
	 */
	private void setProperties(Button ... buttons) {
		for(Button button : buttons) {
			button.setPreferredSize(PREFERRED_DIMENSION);
			button.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(Color.GREEN, 1),
					BorderFactory.createLineBorder(Color.WHITE, 2)
				),
				BorderFactory.createLineBorder(Color.GREEN, 1)
			));
			Font font = new Font(BUTTON_FONT_NAME, Font.BOLD, BUTTON_FONT_SIZE);
			button.setFont(font);
		}
	}
	
	/**
	 * Assemble buttons and create button panel
	 * 
	 * @return
	 */
	private JPanel assembleButtons() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(7, 1, GAP, GAP));
		buttonPanel.setBackground(COLOR_BACKGROUND);
		buttonPanel.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.GREEN, 1),
				BorderFactory.createLineBorder(COLOR_BACKGROUND, 10)
			));
		buttonPanel.add(singleplayer);
		buttonPanel.add(multiplayer);
		buttonPanel.add(load);
		buttonPanel.add(highscore);
		buttonPanel.add(settings);
		buttonPanel.add(credits);
		buttonPanel.add(quit);
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
	
	public Button[] getButtons() {
		return buttons;
	}
}
