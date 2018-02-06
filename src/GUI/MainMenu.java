package GUI;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainMenu extends JPanel {
	private JButton singleplayer;
	private JButton multiplayer;
	
	public MainMenu() {
	    singleplayer = new JButton("Singleplayer");
	    multiplayer = new JButton("Multiplayer");
	    this.add(singleplayer);
	    this.add(multiplayer); 
	}
	
	public void addSingleplayerListener(ActionListener listener) {
		singleplayer.addActionListener(listener);
	}
	
	public void addMultiplayerListener(ActionListener listener) {
		multiplayer.addActionListener(listener);
	}
} 
