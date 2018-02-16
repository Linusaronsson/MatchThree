package view;

import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Application start view.
 */
@SuppressWarnings("serial")
public class MainMenuView
	extends JPanel
{
	/** ... */
	private JButton multiplayer = new JButton("Multiplayer");
	
	/** ... */
	private JButton singleplayer = new JButton("Singleplayer");
	
	/**
	 * ...
	 */
	public MainMenuView() {
		// Set layout //
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// Assemble view //
		add(singleplayer);
		add(multiplayer);
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
}
