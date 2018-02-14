package view;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

import model.HighScore;


@SuppressWarnings("serial")
public class ScoreMenu
    extends JPanel
{
    //private String base = "Score:            Name:";

    final JList<String> scoreTable = new JList<String>();
    final JButton refresh = new JButton("Refresh");
    
    public ScoreMenu()
    {
	add(scoreTable);
	add(refresh);
    }

    public void refresh()
    {
	scoreTable.setListData(new HighScore().getScoreTable());
    }

    public void addRefreshListener(final ActionListener listener)
    {
	refresh.addActionListener(listener);
    }
}
