package matchthree.view;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import matchthree.model.HighScore;

/**
 * Score menu.
 */
@SuppressWarnings("serial")
public class ScoreMenuView
    extends JPanel
{
	/** Refresh button. */
	final JButton refresh = new JButton("Refresh");
	
	/** Score list. */
	final JList<String> scoreTable = new JList<String>();
	
	/**
	 * Create `ScoreMenuView`.
	 */
	public ScoreMenuView() {
		add(scoreTable);
		add(refresh);
		setVisible(true);
	}
	
	/**
	 * Refresh score list.
	 */
	public void refresh() {
		scoreTable.setListData(new HighScore().getScoreTable());
	}
	
	/**
	 * Add event listener for refresh button.
	 *
	 * @param listener Event listener to use.
	 */
	public void addRefreshListener(final ActionListener listener) {
		refresh.addActionListener(listener);
	}
}
