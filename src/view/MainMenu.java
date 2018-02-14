package view;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Application start view.
 */
@SuppressWarnings("serial")
public class MainMenu
	extends JPanel
{
	/** ... */
	private JButton multiplayer = new JButton("Multiplayer");
	
	/** ... */
	private JButton singleplayer = new JButton("Singleplayer");
	
	/**
	 * ...
	 */
	public MainMenu() {
		add(singleplayer);
		add(multiplayer);
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
	public void addMultiplayerListener(final ActionListener listener) {
		multiplayer.addActionListener(listener);
	}
}
