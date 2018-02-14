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

    final JList scoreTable = new JList();
    final JButton refresh = new JButton("Refresh");
    
    public ScoreMenu()
    {
	add(scoreTable);
	add(refresh);
    }

    public void refresh()
    {
	scoreTable.setModel(new HighScore.getScoreTable());
    }

    public void addRefreshListener(final ActionListener listener)
    {
	refresh.addActionListener(listener);
    }
}
