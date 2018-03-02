package matchthree.view;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.BoxLayout;

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
    final String[] test = {"test 123", "est 23"};
    final JList<String> scoreTable = new JList<String>(test);
	
	/**
	 * Create `ScoreMenuView`.
	 */
	public ScoreMenuView() {

	    // Set layout //
	    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	    
		add(scoreTable);
		add(refresh); //currently not functional
		setVisible(true);
	}
	
	/**
	 * Refresh score list.
	 */
	public void refresh() {
		scoreTable.setListData(new HighScore().getScoreTable());
		System.out.println("" + new HighScore().getScoreTable());
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
