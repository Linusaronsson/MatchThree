package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import model.Coordinate;
import model.Jewel;
import model.MatchThreeModel;

/**
 * MatchThree game view.
 */
@SuppressWarnings({"deprecation", "serial"})
public class MatchThreeUI
	extends JPanel
	implements Observer
{
	/** ... */
	private static final String CELL_FONT_NAME = "Helvetica Neue";
	
	/** ... */
	private static final Color COLOR_BACKGROUND = new Color(0x11, 0x11, 0x11);
	
	/** ... */
	private static final Color COLOR_FOREGROUND = new Color(0xEE, 0xEE, 0xEE);
	
	/** ... */
	private static final String DIR_RESOURCES = "resources";
	
	/** ... */
	private JLabel label = new JLabel("");
	
	/** ... */
	private MatchThreeModel model = null;
	
	/**
	 * Constructor for `MatchThreeUI`.
	 *
	 * @param model Model to use
	 */
	// TODO: Break into multiple methods?
	// TODO: Call parent constructor?
	public MatchThreeUI(final MatchThreeModel model, final GridView gridView) {
		// Validate argument //
		if (model == null) {
			throw new NullPointerException();
		}
		
		this.model = model;
		
		// Bind to model //
		model.addObserver(this);
		
		// Initialize components //
		updateScore();
		
		// Set layout //
		setLayout(new BorderLayout());
		
		// Construct header //
		JPanel header = new JPanel(new FlowLayout());
		label.setForeground(Color.WHITE);
		label.setFont(new Font(CELL_FONT_NAME, Font.BOLD, 20));
		
		header.add(label);
		header.setBackground(Color.BLACK);
		add(header, BorderLayout.PAGE_START);
		
		add(gridView, BorderLayout.CENTER);
		
		// Update window with content //
		//setContentPane(content);
		//pack();
	}
	
	/**
	 * ...
	 */
	@Override
	public void update(final Observable o, final Object arg) { }
	
	/**
	 * Update score label.
	 */
	public void updateScore() {
		int score = model.getScore();
		label.setText("Score: " + score);
	}
}
