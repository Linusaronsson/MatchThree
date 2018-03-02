package matchthree.view;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import java.awt.BorderLayout;

import matchthree.model.HighScore;

/**
 * Score menu.
 */
@SuppressWarnings("serial")
public class ScoreMenuView
    extends JPanel
{
    /** Buttons. */
    final JButton refresh = new JButton("Refresh");
    final JButton mainMenu = new JButton("Main Menu");
    
    /** Score list. */
    final JList<String> scoreTable = new JList<String>();
	
	/**
	 * Create `ScoreMenuView`.
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

    public void addMainMenuListener(final ActionListener listener) {
	mainMenu.addActionListener(listener);
    }
}
