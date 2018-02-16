package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import util.AssetManager;

@SuppressWarnings("serial")
public class ButtonPanel
	extends JPanel
{
	/** ... */
	private static final int GAP_HORIZONTAL = 10;
	
	/** ... */
	private static final int GAP_VERTICAL = 10;
	
	/** ... */
	private static final BufferedImage IMAGE_BACK =
		AssetManager.loadImage("Back.png");
	
	/** ... */
	private static final BufferedImage IMAGE_V1 =
		AssetManager.loadImage("V1.png");
	
	/** ... */
	private static final BufferedImage IMAGE_V2 =
		AssetManager.loadImage("V2.png");
	
	private Button back     = null;
	private Button buttonV1 = null;
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
	
	public ButtonPanel() {
		// Set layout //
		// TODO: Do not use `GridLayout`.
		int rows = 3;
		int cols = 1;
		GridLayout layout2 = new GridLayout(
			rows,
			cols,
			GAP_HORIZONTAL,
			GAP_VERTICAL
		);
		setLayout(layout2);
		
		// Set properties //
		setBackground(new Color(0x33, 0x33, 0x33));
		
		// Create back button //
		back = new Button();
		back.setIcon(new ImageIcon(IMAGE_BACK));
		back.setPreferredSize(new Dimension(50, 50));
		back.setBackground(new Color(0x33, 0x33, 0x33));
		back.setMnemonic(ButtonEnum.BACK.ordinal());
		
		// Create version 1 button //
		buttonV1 = new Button();
		buttonV1.setIcon(new ImageIcon(IMAGE_V1));
		buttonV1.setPreferredSize(new Dimension(80, 80));
		buttonV1.setBackground(new Color(0x33, 0x33, 0x33));
		buttonV1.setMnemonic(ButtonEnum.V1.ordinal());
		
		// Create version 2 button //
		buttonV2 = new Button();
		buttonV2.setIcon(new ImageIcon(IMAGE_V2));
		buttonV2.setPreferredSize(new Dimension(80, 80));
		buttonV2.setBackground(new Color(0x33, 0x33, 0x33));
		buttonV2.setMnemonic(ButtonEnum.V2.ordinal());
		
		// Assemble view //
		add(back);
		add(buttonV1);
		add(buttonV2);
	}
	
	public void addBackListener(final ActionListener listener) {
		back.addActionListener(listener);
	}
	
	public void addButtonV1Listener(final ActionListener listener) {
		buttonV2.addActionListener(listener);
	}
	
	public void addButtonV2Listener(final ActionListener listener) {
		buttonV1.addActionListener(listener);
	}
}
