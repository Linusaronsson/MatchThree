package matchthree.view;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import matchthree.model.HighScore;

/**
 * Score menu.
 *
 * @author David Olofsson
 * @author Erik Selstam
 */
@SuppressWarnings("serial")
public class ScoreMenuView
    extends JPanel
{
	/** Main menu button. */
	final JButton mainMenu = new JButton("Main Menu");
	
	/** Refresh button. */
	final JButton refresh = new JButton("Refresh");
	
	/** Score list. */
	final JList<String> scoreTable = new JList<String>();
	
	/**
	 * Create `ScoreMenuView`.
	 *
	 * @author David Olofsson
	 */
	public ScoreMenuView() {
		// Set layout //
		setLayout(new BorderLayout());
		
		// Fill the score table //
		refresh();
		
		// Add the objects //
		add(scoreTable, BorderLayout.CENTER);
		add(refresh, BorderLayout.NORTH);
		add(mainMenu, BorderLayout.SOUTH);
	}
	
	/**
	 * Refresh score list.
	 *
	 * @author David Olofsson
	 */
	public void refresh() {
		scoreTable.setListData(new HighScore().getScoreTable());
	}
	
	/**
	 * Add event listener for refresh button.
	 *
	 * @author David Olofsson
	 * @param listener Event listener to use.
	 */
	public void addRefreshListener(final ActionListener listener) {
		refresh.addActionListener(listener);
	}
	
	/**
	 * Add event listener for main menu button.
	 *
	 * @author David Olofsson
	 * @param listener Event listener to use.
	 */
	public void addMainMenuListener(final ActionListener listener) {
		mainMenu.addActionListener(listener);
	}
}
