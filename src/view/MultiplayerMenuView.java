package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import util.AssetManager;
import util.Properties;

/**
 * Multiplayer setup screen.
 */
@SuppressWarnings("serial")
public class MultiplayerMenuView
	extends JPanel
{
	/** ... */
	private Button connect = new Button();
	
	/** ... */
	private ButtonPanel backPanel = new ButtonPanel();
	
	/** ... */
	private JTextField ip = new JTextField();
	
	/** ... */
	private JTextField port = new JTextField();
	
	/** ... */
	private JLabel ip_label = new JLabel("IP: ");
	
	/** ... */
	private JLabel port_label = new JLabel("Port: ");
		
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
		connect.setBackground(Color.BLACK);
		connect.setPreferredSize(new Dimension(160, 80));
		connect.setSize(new Dimension(160, 80));
		connect.setContentAreaFilled(true);
		connect.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.BLACK, 1),
				BorderFactory.createLineBorder(Color.DARK_GRAY, 1)),
			BorderFactory.createLineBorder(Color.BLACK, 1)));
		connect.setBorderPainted(true);
		
		Font font = new Font("Impact", Font.PLAIN, 20);
		
		JLabel connectLabel = new JLabel(" Connect to player");
		connectLabel.setForeground(Color.BLACK);
		connectLabel.setFont(font);
		connect.add(connectLabel);
		
		ip_label.setFont(font);
		port_label.setFont(font);
		ip_label.setForeground(Color.WHITE);
		port_label.setForeground(Color.WHITE);
		
		setLayout(new FlowLayout());
		setBackground(Properties.getColorBackground());
		ip.setBackground(Properties.getColorBackground());
		port.setBackground(Properties.getColorBackground());
		
		ip.setForeground(Color.WHITE);
		port.setForeground(Color.WHITE);
		ip.setCaretColor(Color.WHITE);
		port.setCaretColor(Color.WHITE);
		
		JPanel ippanel = new JPanel();
		ippanel.add(ip_label);
		ippanel.add(ip);
		ippanel.setBackground(Properties.getColorBackground());
		
		JPanel portpanel = new JPanel();
		portpanel.add(port_label);
		portpanel.add(port);
		portpanel.setBackground(Properties.getColorBackground());
		
		JPanel bothpanel = new JPanel();
		bothpanel.setLayout(new GridLayout(2, 1));
		bothpanel.add(ippanel);
		bothpanel.add(portpanel);
		bothpanel.setBackground(Properties.getColorBackground());
		
		backPanel.setBackground(Properties.getColorBackground());
		
		add(connect);
		add(bothpanel);
		add(backPanel);
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
	public void addBackListener(final ActionListener listener) {
		getBackButton().addActionListener(listener);
	}
	
	/**
	 * ...
	 *
	 * @param listener ...
	 * @param target   ...
	 */
	public void addHoverListener(
		final MouseListener listener,
		final Button        target
	) {
		target.addMouseListener(listener);
	}
	
	/**
	 * ...
	 *
	 * @return ...
	 */
	public Button getBackButton() {
		return backPanel.getBackButton();
	}
	
	/**
	 * ...
	 *
	 * @return ...
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