package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.LayoutManager;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import util.AssetManager.Audio;

/**
 * ...
 */
@SuppressWarnings("serial")
public class SingleplayerView
	extends JPanel
{	
	/** ... */
	private Container buttonPanel = new JPanel();
	
	/** ... */
	private Container gameView = new JPanel();
	
	/**
	 * Create `SingleplayerView`.
	 */
	public SingleplayerView() {
		// Set properties //
		// TODO: Stopgap hack.
		setBackground(new Color(0x11, 0x11, 0x11));
		
		// Set layout //
		LayoutManager layout = new BoxLayout(this, BoxLayout.X_AXIS);
		setLayout(layout);
		
		// Assemble view //
		add(gameView);
		add(buttonPanel);
	}
	
	/**
	 * Get button panel container.
	 *
	 * @return Button panel container.
	 */
	public Container getButtonPanel() {
		return buttonPanel;
	}
	
	/**
	 * Get game container.
	 *
	 * @return Game container.
	 */
	public Container getGame() {
		return gameView;
	}
	
	/**
	 * ...
	 *
	 * @param i ...
	 */
	public void changeSprites(final int i) {
		//gridView.changeSprites(i);
	}
	
	/**
	 * ...
	 *
	 * @param audio ...
	 */
	public void playAudio(final Audio audio) {
		// TODO Auto-generated method stub
		//gridView.playAudio(audio);
	}
}
