package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import util.AssetManager;

/**
 * Multiplayer setup screen.
 */
@SuppressWarnings("serial")
public class MultiplayerMenuView
	extends JPanel
{
	/** ... */
	private Button connect = new Button(new ImageIcon(AssetManager.loadImage("ConnectButtonBackground.png"))); // "Connect to player";
	
	/** ... */
	private JTextField ip = new JTextField();
	
	/** ... */
	private JTextField port = new JTextField();
		
	/**
	 * ...
	 */
	public MultiplayerMenuView() {
		// Set properties //
		// TODO: Avoid magic numbers.
		ip.setPreferredSize(new Dimension(200, 24));
		port.setPreferredSize(new Dimension(200, 24));
		
		// Assemble view //
		// TODO: Add a back button here somehow
		//add(back);
		
		// Set connect button layout //
		connect.setPreferredSize(new Dimension(160, 80));
		connect.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.BLACK, 1),
				BorderFactory.createLineBorder(Color.DARK_GRAY, 1)),
			BorderFactory.createLineBorder(Color.BLACK, 1)));
		connect.setBorderPainted(true);
		JLabel connectLabel = new JLabel("    Connect to player");
		connectLabel.setFont(new Font("", Font.PLAIN, 15));
		connect.add(connectLabel);
		
		add(connect);
		add(new JLabel("IP: "));
		add(ip);
		add(new JLabel("Port: "));
		add(port);
	}
	
	/**
	 * ...
	 *
	 * @param listener ...
	 */
	public void addConnectListener(final ActionListener listener) {
		connect.addActionListener(listener);
	}
	
	/**
	 * ...
	 *
	 * @param listener ...
	 */
	public void addHoverListener(final MouseListener listener, final Button target) {
		target.addMouseListener(listener);
	}
	
	/**
	 * ...
	 *
	 * @return
	 */
	public Button getConnectButton() {
		return connect;
	}
	
	/**
	 * ...
	 *
	 * @return ...
	 */
	public String getIp() {
		return ip.getText();
	}
	
	/**
	 * ...
	 *
	 * @return ...
	 */
	public String getPort() {
		return port.getText();
	}
}
