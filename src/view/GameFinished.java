package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * ...
 */
@SuppressWarnings("serial")
public class GameFinished
	extends JPanel
{
	/** ... */
	//new Color(0xbb, 0xf0, 0x49);
	private static final Color COLOR_RESULT_BACKGROUND = Color.WHITE;
	
	/** Label color. */
	private static final Color COLOR_LABEL = Color.BLACK;
	
	/** Border width. */
	private static final int BORDER_SIZE = 1;
	
	/** Border color. */
	private static final Color COLOR_BORDER = Color.BLUE;
	
	/** Border dark color. */
	private static final Color COLOR_BORDER_DARKER = COLOR_BORDER.darker();
	
	/** Font size. */
	private static final int FONT_SIZE = 20;
	
	/** ... */
	private JLabel title = null;
	
	/** ... */
	private JLabel message = null;
	
	/**
	 * Constructor.
	 *
	 * @param score ...
	 */
	public GameFinished(final int score) {
		super(new BorderLayout());
		title = new JLabel("Game finished");
		message = new JLabel("Score: " + score);
		
		title.setFont(new Font("", Font.BOLD, FONT_SIZE));
		message.setFont(new Font("", Font.BOLD, FONT_SIZE));
		
		title.setForeground(COLOR_LABEL);
		message.setForeground(COLOR_LABEL);
		
		JPanel panel = new JPanel(new GridLayout(2, 1));
		panel.add(title);
		panel.add(message);
		panel.setBackground(COLOR_RESULT_BACKGROUND);
		setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.BLACK, BORDER_SIZE + 1),
				BorderFactory.createLineBorder(COLOR_BORDER, BORDER_SIZE)
			),
			BorderFactory.createCompoundBorder(
				BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(
						COLOR_BORDER_DARKER,
						BORDER_SIZE
					),
					BorderFactory.createLineBorder(
						COLOR_BORDER,
						BORDER_SIZE
					)
				),
				BorderFactory.createCompoundBorder(
					BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(
							COLOR_BORDER_DARKER,
							BORDER_SIZE
						),
						BorderFactory.createLineBorder(
							COLOR_BORDER,
							BORDER_SIZE
						)
					),
					BorderFactory.createCompoundBorder(
						BorderFactory.createCompoundBorder(
							BorderFactory.createLineBorder(
								COLOR_BORDER_DARKER,
								BORDER_SIZE
							),
							BorderFactory.createLineBorder(
								COLOR_BORDER,
								BORDER_SIZE
							)
						),
						BorderFactory.createCompoundBorder(
							BorderFactory.createLineBorder(
								COLOR_BORDER_DARKER,
								BORDER_SIZE
							),
							BorderFactory.createLineBorder(
								Color.BLACK,
								BORDER_SIZE + 1
							)
						)
					)
				)
			)
		));
		
		setBackground(COLOR_RESULT_BACKGROUND);
		
		add(panel, BorderLayout.NORTH);
	}
}
