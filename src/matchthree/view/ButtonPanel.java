package matchthree.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import matchthree.model.MatchThreeModel;
import matchthree.util.AssetManager;
import matchthree.util.Properties;

/**
 * Game button panel.
 *
 * @author Erik Selstam
 * @author Erik Tran
 * @author Linus Aronsson
 */
@SuppressWarnings({"deprecation", "serial"})
public class ButtonPanel
	extends JPanel
	implements Observer
{
	/** ... */
	private static final int GAP_HORIZONTAL = 4;
	
	/** ... */
	private static final int GAP_VERTICAL = 4;
	
	/** Background color. */
	private static final Color COLOR_BACKGROUND =
		Properties.getColorBackground();
	
	/** Panel color. */
	private static final Color COLOR_PANEL =
		COLOR_BACKGROUND.brighter().brighter();
	
	/** Visual style 1 image. */
	private static final BufferedImage IMAGE_V1 =
		AssetManager.loadImage("V1.png");
	
	/** Visual style 2 image. */
	private static final BufferedImage IMAGE_V2 =
		AssetManager.loadImage("V2.png");
	
	/** Back button. */
	private BackButton back = new BackButton();
	
	/** Visual style 1 button. */
	private Button buttonV1 = null;
	
	/** Visual style 2 button. */
	private Button buttonV2 = null;
	
	/** Visual style button container. */
	private Container styleButtonsPanel = new JPanel();
	
	/**
	 * Constructor.
	 *
	 * @author Erik Selstam
	 * @author Linus Aronsson
	 * @author Erik Tran
	 * @param model MatchThree game model to use.
	 */
	public ButtonPanel(final MatchThreeModel model) {
		// Set layout //
		BorderLayout layout = new BorderLayout(
			GAP_HORIZONTAL,
			GAP_VERTICAL
		);
		setLayout(layout);
		
		// Set properties //
		setBackground(COLOR_BACKGROUND);
		
		// Create version 1 button //
		buttonV1 = new Button();
		buttonV1.setIcon(new ImageIcon(IMAGE_V1));
		
		// Create version 2 button //
		buttonV2 = new Button();
		buttonV2.setIcon(new ImageIcon(IMAGE_V2));
		
		// Create panel for the version buttons //
		styleButtonsPanel = createVersionButtonsPanel(buttonV1, buttonV2);
		styleButtonsPanel.setBackground(COLOR_BACKGROUND);
		
		// Assemble view //
		model.addObserver(this);
		add(back, BorderLayout.PAGE_START);
		add(styleButtonsPanel);
	}
	
	/**
	 * Constructor for panel without style buttons.
	 *
	 * @author Linus Aronsson
	 * @author Erik Tran
	 */
	public ButtonPanel() {
		// Set layout //
		BorderLayout layout = new BorderLayout(
			GAP_HORIZONTAL,
			GAP_VERTICAL
		);
		setLayout(layout);
		
		// Set properties //
		setBackground(COLOR_BACKGROUND);
		
		// Assemble view //
		add(back, BorderLayout.PAGE_START);
	}
	
	/**
	 * ...
	 *
	 * @author Erik Tran
	 * @param buttons ...
	 * @return        ...
	 */
	public Container createVersionButtonsPanel(final Button... buttons) {
		Container panel = new JPanel();
		for (final Button button : buttons) {
			panel.add(button);
		}
		panel.setBackground(COLOR_PANEL);
		return panel;
	}
	
	/**
	 * Add listener for back button.
	 *
	 * @author Erik Selstam
	 * @author Erik Tran
	 * @param listener Event listener to use.
	 */
	public void addBackListener(final ActionListener listener) {
		back.addActionListener(listener);
	}
	
	/**
	 * Add listener for back button.
	 *
	 * @author Erik Tran
	 * @param listener Event listener to use.
	 */
	public void addBackListener(final MouseListener listener) {
		back.addMouseListener(listener);
	}
	
	/**
	 * Add listener for version 1 button.
	 *
	 * @author Erik Selstam
	 * @author Erik Tran
	 * @param listener Event listener to use.
	 */
	public void addButtonV1Listener(final ActionListener listener) {
		buttonV1.addActionListener(listener);
	}
	
	/**
	 * Add listener for version 1 button.
	 *
	 * @author Erik Tran
	 * @param listener Event listener to use.
	 */
	public void addButtonV1Listener(final MouseListener listener) {
		buttonV1.addMouseListener(listener);
	}
	
	/**
	 * Add listener for version 2 button.
	 *
	 * @author Erik Selstam
	 * @author Erik Tran
	 * @param listener Event listener to use.
	 */
	public void addButtonV2Listener(final ActionListener listener) {
		buttonV2.addActionListener(listener);
	}
	
	/**
	 * Add listener for version 2 button.
	 *
	 * @author Erik Tran
	 * @param listener Event listener to use.
	 */
	public void addButtonV2Listener(final MouseListener listener) {
		buttonV2.addMouseListener(listener);
	}
	
	/**
	 * ...
	 *
	 * @author Erik Tran
	 * @return ...
	 */
	public Button getBackButton() {
		// TODO Auto-generated method stub
		return back;
	}
	
	/**
	 * ...
	 *
	 * @author Erik Tran
	 * @return ...
	 */
	public Button getV1Button() {
		// TODO Auto-generated method stub
		return buttonV1;
	}
	
	/**
	 * ...
	 *
	 * @author Erik Tran
	 * @return ...
	 */
	public Button getV2Button() {
		// TODO Auto-generated method stub
		return buttonV2;
	}

	@Override
	public void update(final Observable o, final Object arg) {
		if (o instanceof MatchThreeModel && arg instanceof String) {
			String str = (String) arg;
			if ("remove".equals(str)) {
				styleButtonsPanel.removeAll();
				repaint();
				revalidate();
			}
		}
	}
}
