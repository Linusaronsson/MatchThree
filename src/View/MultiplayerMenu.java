package View;

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
public class MultiplayerMenu
	extends JPanel
{
	/** ... */
	private JButton connect = new JButton("Connect to player");
	
	/** ... */
	private JTextField ip = new JTextField();
	
	/** ... */
	private JLabel ipText = new JLabel("IP: ");
	
	/** ... */
	private JTextField port = new JTextField();
	
	/** ... */
	private JLabel portText = new JLabel("Port: ");
	
	/**
	 * ...
	 */
	public MultiplayerMenu() {
		// TODO: Avoid magic numbers.
		ip.setPreferredSize(new Dimension(200, 24));
		port.setPreferredSize(new Dimension(200, 24));
		
		add(connect);
		add(ipText);
		add(ip);
		add(portText);
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
