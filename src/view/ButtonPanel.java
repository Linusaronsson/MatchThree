package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import model.MatchThreeModel;
import util.AssetManager;
import util.Properties;

/**
 * Game button panel.
 */
@SuppressWarnings("serial")
public class ButtonPanel
	extends JPanel implements Observer
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
	
	/** ... */
	private JPanel styleButtonsPanel = new JPanel();;
	
	/**
	 * Create `ButtonPanel`.
	 */
	public ButtonPanel(final MatchThreeModel model) {
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
		
		// Create version 1 button //
		buttonV1 = new Button();
		buttonV1.setIcon(new ImageIcon(IMAGE_V1));
		
		// Create version 2 button //
		buttonV2 = new Button();
		buttonV2.setIcon(new ImageIcon(IMAGE_V2));
		
		// Create panel for the version buttons //
		styleButtonsPanel = createVersionButtonsPanel(buttonV1, buttonV2);
		
		// Assemble view //
		model.addObserver(this);
		add(back, BorderLayout.PAGE_START);
		add(styleButtonsPanel);
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
	public void addBackListener(final MouseListener listener) {
		back.addMouseListener(listener);
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
	public void addButtonV2Listener(final MouseListener listener) {
		buttonV2.addMouseListener(listener);
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
	 * Add listener for version 1 button.
	 *
	 * @param listener Event listener to use.
	 */
	public void addButtonV1Listener(final ActionListener listener) {
		buttonV1.addActionListener(listener);
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
	 * Removes buttonPanel when game finished
	 */
	@Override
	public void update(final Observable o, final Object arg) {
		if (o instanceof MatchThreeModel && arg instanceof String) {
			String str = (String) arg;
			if(str.equals("remove")) {
				styleButtonsPanel.removeAll();
				repaint();
				revalidate();
			}
		}
	}
}
