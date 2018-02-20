package view;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Multiplayer setup screen.
 */
@SuppressWarnings("serial")
public class MultiplayerMenuView
	extends JPanel
{
	/** ... */
	private JButton connect = new JButton("Connect to player");
	
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
