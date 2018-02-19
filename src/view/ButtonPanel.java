package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import util.AssetManager;
import util.AssetManager.Audio;
import util.Properties;

/**
 * Game button panel.
 */
@SuppressWarnings("serial")
public class ButtonPanel
	extends JPanel
{
	/** ... */
	private static final int GAP_HORIZONTAL = 4;
	
	/** ... */
	private static final int GAP_VERTICAL = 4;
	
	/** ... */
	private static final Color COLOR_BACKGROUND =
		Properties.getColorBackground();
	
	/** ... */
	private static final Color COLOR_PANEL =
		COLOR_BACKGROUND.brighter().brighter();
	
	/** ... */
	private static final BufferedImage IMAGE_BACK =
		AssetManager.loadImage("Back.png");
	
	/** ... */
	private static final BufferedImage IMAGE_V1 =
		AssetManager.loadImage("V1.png");
	
	/** ... */
	private static final BufferedImage IMAGE_V2 =
		AssetManager.loadImage("V2.png");
	
	/** ... */
	private Button back = null;
	
	/** ... */
	private Button buttonV1 = null;
	
	/** ... */
	private Button buttonV2 = null;
	
	/**
	 * ...
	 */
	public enum ButtonEnum
	{
		/** ... */
		BACK,
		
		/** ... */
		V1,
		
		/** ... */
		V2
	}
	
	/**
	 * Create `ButtonPanel`.
	 */
	public ButtonPanel() {
		// Set layout //
		BorderLayout layout = new BorderLayout(
			GAP_HORIZONTAL,
			GAP_VERTICAL
		);
		setLayout(layout);
		
		// Set properties //
		setBackground(COLOR_PANEL);
		
		// Create back button //
		back = new Button();
		back.setIcon(new ImageIcon(IMAGE_BACK));
		back.setPreferredSize(new Dimension(50, 50));
		back.setMnemonic(ButtonEnum.BACK.ordinal());
		
		// Create version 1 button //
		buttonV1 = new Button();
		buttonV1.setIcon(new ImageIcon(IMAGE_V1));
		buttonV1.setMnemonic(ButtonEnum.V1.ordinal());
		
		// Create version 2 button //
		buttonV2 = new Button();
		buttonV2.setIcon(new ImageIcon(IMAGE_V2));
		buttonV2.setMnemonic(ButtonEnum.V2.ordinal());
		
		// Create panel for the version buttons //
		JPanel panel = createVersionButtonsPanel(buttonV1, buttonV2);
		
		// Assemble view //
		add(back, BorderLayout.PAGE_START);
		add(panel);
	}
	
	/**
	 * ...
	 *
	 * @param buttons ...
	 * @return        ...
	 */
	public JPanel createVersionButtonsPanel(final Button... buttons) {
		JPanel panel = new JPanel();
		for (Button button : buttons) {
			panel.add(button);
		}
		panel.setBackground(COLOR_PANEL);
		return panel;
	}
	
	/**
	 * Add listener for back button.
	 *
	 * @param listener Event listener to use.
	 */
	public void addBackListener(final ActionListener listener) {
		back.addActionListener(listener);
	}
	
	/**
	 * Add listener for back button.
	 *
	 * @param listener Event listener to use.
	 */
	public void addBackListener(final MouseListener listener) {
		back.addMouseListener(listener);
	}
	
	/**
	 * Add listener for version 1 button.
	 *
	 * @param listener Event listener to use.
	 */
	public void addButtonV1Listener(final ActionListener listener) {
		buttonV1.addActionListener(listener);
	}
	
	/**
	 * Add listener for version 1 button.
	 *
	 * @param listener Event listener to use.
	 */
	public void addButtonV1Listener(final MouseListener listener) {
		buttonV1.addMouseListener(listener);
	}
	
	/**
	 * Add listener for version 2 button.
	 *
	 * @param listener Event listener to use.
	 */
	public void addButtonV2Listener(final ActionListener listener) {
		buttonV2.addActionListener(listener);
	}
	
	/**
	 * Add listener for version 2 button.
	 *
	 * @param listener Event listener to use.
	 */
	public void addButtonV2Listener(final MouseListener listener) {
		buttonV2.addMouseListener(listener);
	}
	
	/**
	 * Get the filename of an audio asset.
	 *
	 * @param audio Audio asset to get filename of.
	 * @return Filename of audio asset.
	 */
	// TODO: Avoid code duplication.
	private String getAudioName(final Audio audio) {
		String name = null;
		switch (audio) {
			case INVALID:   name = "InvalidMove.wav"; break;
			case SWAP:      name = "Swap.wav";        break;
			case MOUSEOVER: name = "MouseOver.wav";   break;
			case SELECT:    name = "Select.wav";      break;
			default: throw new IllegalStateException();
		}
		return name;
	}
	
	/**
	 * ...
	 *
	 * @return ...
	 */
	public Button getBackButton() {
		// TODO Auto-generated method stub
		return back;
	}
	
	/**
	 * ...
	 *
	 * @return ...
	 */
	public Button getV1Button() {
		// TODO Auto-generated method stub
		return buttonV1;
	}
	
	/**
	 * ...
	 *
	 * @return ...
	 */
	public Button getV2Button() {
		// TODO Auto-generated method stub
		return buttonV2;
	}
	
	/**
	 * Play swap audio clip.
	 *
	 * @param audio ...
	 */
	// TODO: Avoid code duplication.
	public void playAudio(final Audio audio) {
		// Validate argument //
		if (audio == null) {
			throw new NullPointerException();
		}
		
		// Get a reference to clip //
		String name = getAudioName(audio);
		Clip clip = AssetManager.loadAudio(name);
		
		// Rewind and play clip //
		clip.setFramePosition(0);
		clip.start();
	}
}
