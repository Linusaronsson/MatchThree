package matchthree.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import matchthree.model.HighScore;

/**
 * Score menu.
 *
 * @author David Olofsson
 * @author Erik Selstam
 */
@SuppressWarnings("serial")
public class ScoreMenuView
    extends Panel
{
	/** Main menu button. */
	final JButton mainMenu = new JButton("Main Menu");
	
	/** Refresh button. */
	final JButton refresh = new JButton("Refresh");
	
	/** Score list. */
	final JList<String> scoreTable = new JList<String>();
	
	/**
	 * Constructor.
	 *
	 * @author David Olofsson
	 * @author Erik Selstam
	 */
	public ScoreMenuView() {
		// Fill the score table //
		refresh();
		
		// Create scroll pane //
		Container scrollPane = new JScrollPane(scoreTable);
		
		// Set properties //
		Dimension size = new Dimension(200, 400);
		setPreferredSize(size);
		
		// Assemble view //
		add(refresh,    BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		add(mainMenu,   BorderLayout.SOUTH);
	}
	
	/**
	 * Refresh score list.
	 *
	 * @author David Olofsson
	 * @author Erik Selstam
	 */
	public void refresh() {
		String[] data = new HighScore().getScoreTable();
		scoreTable.setListData(data);
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
