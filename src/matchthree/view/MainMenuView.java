package matchthree.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import matchthree.util.AssetManager;
import matchthree.util.Properties;

/**
 * Application start view.
 */
@SuppressWarnings("serial")
public class MainMenuView
	extends JPanel
{
	/** ... */
	private static final String BUTTON_FONT_NAME =
		Properties.getButtonFontName();
	
	/** ... */
	private static final int BUTTON_FONT_SIZE =
			Properties.getButtonFontSize();
	
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
	private static final Color COLOR_BACKGROUND =
		Properties.getColorBackground();
	
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
	private JPanel buttonPanel = new JPanel();
	
	/**
	 * ...
	 */
	public MainMenuView() {
		// Set layout //
		setLayout(new BorderLayout());
		setBackground(COLOR_BACKGROUND);
		
		// Set border //
		setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createLineBorder(Color.WHITE, 2),
			BorderFactory.createLineBorder(Color.BLACK, 2))
		);
		
		// Set button properties //
		setProperties(buttons);
		
		// Set button panel properties //
		LayoutManager layout = new GridLayout(buttons.length, 1, GAP, GAP);
		buttonPanel.setLayout(layout);
		buttonPanel.setBackground(COLOR_BACKGROUND);
		buttonPanel.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.WHITE, 2),
				BorderFactory.createLineBorder(COLOR_BACKGROUND, 10)
			)
		);
		
		// Assemble button panel //
		assembleButtons(buttons);
		
		// Assemble view //
		add(LOGO, BorderLayout.LINE_START);
		add(buttonPanel, BorderLayout.CENTER);
		add(new JLabel("     "), BorderLayout.WEST);
		add(new JLabel("     "), BorderLayout.EAST);
		add(new JLabel(" "), BorderLayout.NORTH);
		add(new JLabel(" "), BorderLayout.SOUTH);
	}
	
	/**
	 * Set buttons properties.
	 *
	 * @param buttons ...
	 */
	private void setProperties(final Button[] buttons) {
		for (Button button : buttons) {
			button.setPreferredSize(PREFERRED_DIMENSION);
			button.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(Color.WHITE, 1),
					BorderFactory.createLineBorder(Color.WHITE.darker(), 1)
				),
				BorderFactory.createLineBorder(Color.WHITE, 1)
			));
			Font font = new Font(
				BUTTON_FONT_NAME,
				Font.PLAIN,
				BUTTON_FONT_SIZE
			);
			button.setFont(font);
		}
	}
	
	/**
	 * Assemble buttons and create button panel.
	 *
	 * @param buttons ...
	 */
	private void assembleButtons(final Button[] buttons) {
		for (Button button : buttons) {
			buttonPanel.add(button);
		}
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
	public void addLoadListener(final ActionListener listener) {
		load.addActionListener(listener);
	}
	
	/**
	 * ...
	 *
	 * @param listener ...
	 */
	public void addHighscoreListener(final ActionListener listener) {
		highscore.addActionListener(listener);
	}
	
	/**
	 * ...
	 *
	 * @param listener ...
	 */
	public void addSettingsListener(final ActionListener listener) {
		settings.addActionListener(listener);
	}
	
	/**
	 * ...
	 *
	 * @param listener ...
	 */
	public void addCreditsListener(final ActionListener listener) {
		credits.addActionListener(listener);
	}
	
	/**
	 * ...
	 *
	 * @param listener ...
	 */
	public void addQuitListener(final ActionListener listener) {
		quit.addActionListener(listener);
	}
	
	/**
	 * ...
	 *
	 * @return ...
	 */
	public Button[] getButtons() {
		return buttons;
	}
}
