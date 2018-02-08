package View;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainMenu
	extends JPanel
{
	private JButton multiplayer;
	private JButton singleplayer;
	
	public MainMenu()
	{
		multiplayer  = new JButton("Multiplayer");
		singleplayer = new JButton("Singleplayer");
		
		this.add(singleplayer);
		this.add(multiplayer);
	}
	
	public void addSingleplayerListener(ActionListener listener)
	{
		singleplayer.addActionListener(listener);
	}
	
	public void addMultiplayerListener(ActionListener listener)
	{
		multiplayer.addActionListener(listener);
	}
}
