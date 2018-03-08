package matchthree.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Multiplayer setup screen.
 *
 * @author Linus Aronsson
 * @author Erik Tran
 * @author Erik Selstam
 */
@SuppressWarnings("serial")
public class MultiplayerMenuView
	extends Panel
{
	/** Background color. */
	private static final Color COLOR_BACKGROUND = new Color(0x22, 0x22, 0x22);
	
	/** Font. */
	private static final Font FONT = new Font("Impact", Font.PLAIN, 20);
	
	/** ... */
	private Button connect = new Button();
	
	/** ... */
	private ButtonPanel backPanel = new ButtonPanel();
	
	/** ... */
	private JTextField ip = new JTextField();
	
	/** ... */
	private JTextField port = new JTextField();
	
	/**
	 * ...
	 *
	 * @author Erik Tran
	 * @author Linus Aronsson
	 * @author Erik Selstam
	 */
	public MultiplayerMenuView() {
		// Set connect button properties //
		connect.setBackground(Color.BLACK);
		connect.setContentAreaFilled(true);
		connect.setForeground(Color.WHITE);
		connect.setPreferredSize(new Dimension(160, 80));
		connect.setSize(new Dimension(160, 80));
		
		// Create connect label //
		JLabel connectLabel = new JLabel(" Connect to player");
		connectLabel.setForeground(Color.WHITE);
		connectLabel.setFont(FONT);
		connect.add(connectLabel);
		
		// Create IP label //
		JLabel ipLabel = new JLabel("IP: ");
		ipLabel.setFont(FONT);
		ipLabel.setForeground(Color.WHITE);
		
		// Create port label //
		JLabel portLabel = new JLabel("Port: ");
		portLabel.setFont(FONT);
		portLabel.setForeground(Color.WHITE);
		
		// Create IP field //
		ip.setBackground(Color.WHITE);
		ip.setCaretColor(Color.BLACK);
		ip.setForeground(Color.BLACK);
		ip.setPreferredSize(new Dimension(200, 24));
		
		// Create port label //
		port.setBackground(Color.WHITE);
		port.setCaretColor(Color.BLACK);
		port.setForeground(Color.BLACK);
		port.setPreferredSize(new Dimension(200, 24));
		
		// Create IP container //
		Container ippanel = new SubPanel();
		ippanel.setLayout(new FlowLayout());
		ippanel.add(ipLabel);
		ippanel.add(ip);
		
		// Create ports container //
		Container portpanel = new SubPanel();
		portpanel.setLayout(new FlowLayout());
		portpanel.add(portLabel);
		portpanel.add(port);
		
		// Create fields container //
		Container bothpanel = new SubPanel();
		bothpanel.setLayout(new GridLayout(2, 1));
		bothpanel.add(ippanel);
		bothpanel.add(portpanel);
		
		// Set properties //
		setBackground(COLOR_BACKGROUND);
		
		// Set layout //
		setLayout(new FlowLayout());
		
		// Assemble view //
		add(connect);
		add(bothpanel);
		add(backPanel);
	}
	
	/**
	 * ...
	 *
	 * @author Linus Aronsson
	 * @param listener ...
	 */
	public void addConnectListener(final ActionListener listener) {
		connect.addActionListener(listener);
	}
	
	/**
	 * ...
	 *
	 * @author Linus Aronsson
	 * @param listener ...
	 */
	public void addBackListener(final ActionListener listener) {
		getBackButton().addActionListener(listener);
	}
	
	/**
	 * ...
	 *
	 * @author Erik Tran
	 * @param listener ...
	 * @param target   ...
	 */
	public void addHoverListener(
		final MouseListener listener,
		final Button        target)
	{
		target.addMouseListener(listener);
	}
	
	/**
	 * ...
	 *
	 * @author Linus Aronsson
	 * @return ...
	 */
	public Button getBackButton() {
		return backPanel.getBackButton();
	}
	
	/**
	 * ...
	 *
	 * @author Erik Tran
	 * @return ...
	 */
	public Button getConnectButton() {
		return connect;
	}
	
	/**
	 * ...
	 *
	 * @author Linus Aronsson
	 * @return ...
	 */
	public String getIp() {
		return ip.getText();
	}
	
	/**
	 * ...
	 *
	 * @author Linus Aronsson
	 * @return ...
	 */
	public String getPort() {
		return port.getText();
	}
}
