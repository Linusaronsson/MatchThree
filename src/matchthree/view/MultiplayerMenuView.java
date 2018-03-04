package matchthree.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import matchthree.util.Properties;

/**
 * Multiplayer setup screen.
 *
 * @author Linus Aronsson
 * @author Erik Tran
 * @author Erik Selstam
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
	private JLabel ipLabel = new JLabel("IP: ");
	
	/** ... */
	private JLabel portLabel = new JLabel("Port: ");
		
	/**
	 * ...
	 *
	 * @author Erik Tran
	 * @author Linus Aronsson
	 * @author Erik Selstam
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
		
		ipLabel.setFont(font);
		portLabel.setFont(font);
		ipLabel.setForeground(Color.WHITE);
		portLabel.setForeground(Color.WHITE);
		
		setLayout(new FlowLayout());
		setBackground(Properties.getColorBackground());
		ip.setBackground(Properties.getColorBackground());
		port.setBackground(Properties.getColorBackground());
		
		ip.setForeground(Color.WHITE);
		port.setForeground(Color.WHITE);
		ip.setCaretColor(Color.WHITE);
		port.setCaretColor(Color.WHITE);
		
		Container ippanel = new JPanel();
		ippanel.add(ipLabel);
		ippanel.add(ip);
		ippanel.setBackground(Properties.getColorBackground());
		
		Container portpanel = new JPanel();
		portpanel.add(portLabel);
		portpanel.add(port);
		portpanel.setBackground(Properties.getColorBackground());
		
		Container bothpanel = new JPanel();
		bothpanel.setLayout(new GridLayout(2, 1));
		bothpanel.add(ippanel);
		bothpanel.add(portpanel);
		bothpanel.setBackground(Properties.getColorBackground());
		
		backPanel.setBackground(Properties.getColorBackground());
		
		add(connect);
		add(bothpanel);
		add(backPanel);
		
		// Set border //
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.WHITE, 2),
				BorderFactory.createLineBorder(Color.BLACK, 2)));
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
