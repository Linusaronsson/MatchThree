package matchthree.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import matchthree.util.AssetManager;

/**
 * Application start view.
 *
 * @author Erik Tran
 * @author Erik Selstam
 */
@SuppressWarnings("serial")
public class MainMenuView
	extends Panel
{
	/** Button font. */
	private static final Font FONT_BUTTON = new Font("Impact", Font.PLAIN, 30);
	
	/** ... */
	private static final int PREFERRED_WIDTH = 400;
	
	/** ... */
	private static final int PREFERRED_HEIGHT = 50;
	
	/** ... */
	private static final int GAP = 10;
	
	/** ... */
	private static final Dimension PREFERRED_DIMENSION =
		new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT);
	
	/** ... */
	private static final JLabel LOGO =
		new JLabel(new ImageIcon(AssetManager.loadImage("Logo3.png")));
	
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
	
	/** ... */
	private Button[] buttons = new Button[] {
		singleplayer, multiplayer, load, highscore, settings, credits, quit,
	};
	
	/** ... */
	private Container buttonPanel = new SubPanel();
	
	/**
	 * ...
	 *
	 * @author Erik Tran
	 * @author Erik Selstam
	 */
	public MainMenuView() {
		// Set button properties //
		for (final Button button : buttons) {
			button.setBackground(null);
			button.setFont(FONT_BUTTON);
			button.setOpaque(false);
			button.setPreferredSize(PREFERRED_DIMENSION);
		}
		
		// Set button panel layout //
		LayoutManager layout = new GridLayout(buttons.length, 1, GAP, GAP);
		buttonPanel.setLayout(layout);
		
		// Assemble button panel //
		for (final Button button : buttons) {
			buttonPanel.add(button);
		}
		
		// Set layout //
		setLayout(new BorderLayout());
		
		// Assemble view //
		add(LOGO,        BorderLayout.LINE_START);
		add(buttonPanel, BorderLayout.CENTER);
		add(new JLabel("     "), BorderLayout.WEST);
		add(new JLabel("     "), BorderLayout.EAST);
		add(new JLabel(" "),     BorderLayout.NORTH);
		add(new JLabel(" "),     BorderLayout.SOUTH);
	}
	
	/**
	 * ...
	 *
	 * @author Erik Tran
	 * @param listener ...
	 */
	public void addMultiplayerListener(final ActionListener listener) {
		multiplayer.addActionListener(listener);
	}
	
	/**
	 * ...
	 *
	 * @author Erik Tran
	 * @param listener ...
	 */
	public void addSingleplayerListener(final ActionListener listener) {
		singleplayer.addActionListener(listener);
	}
	
	/**
	 * ...
	 *
	 * @author Erik Tran
	 * @param listener ...
	 */
	public void addLoadListener(final ActionListener listener) {
		load.addActionListener(listener);
	}
	
	/**
	 * ...
	 *
	 * @author Erik Tran
	 * @param listener ...
	 */
	public void addHighscoreListener(final ActionListener listener) {
		highscore.addActionListener(listener);
	}
	
	/**
	 * ...
	 *
	 * @author Erik Tran
	 * @param listener ...
	 */
	public void addSettingsListener(final ActionListener listener) {
		settings.addActionListener(listener);
	}
	
	/**
	 * ...
	 *
	 * @author Erik Tran
	 * @param listener ...
	 */
	public void addCreditsListener(final ActionListener listener) {
		credits.addActionListener(listener);
	}
	
	/**
	 * ...
	 *
	 * @author Erik Tran
	 * @param listener ...
	 */
	public void addQuitListener(final ActionListener listener) {
		quit.addActionListener(listener);
	}
	
	/**
	 * ...
	 *
	 * @author Erik Tran
	 * @return ...
	 */
	public Button[] getButtons() {
		return buttons;
	}
}
