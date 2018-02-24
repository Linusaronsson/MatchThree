package view;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameFinished
	extends JPanel {
	private JLabel title = null;
	private JLabel message = null;
	public GameFinished(final int score) {
		super(new BorderLayout());
		title = new JLabel("Game finished");
		message = new JLabel("Score: " + score);
		add(title, BorderLayout.NORTH);
		add(message, BorderLayout.CENTER);
	}
}
