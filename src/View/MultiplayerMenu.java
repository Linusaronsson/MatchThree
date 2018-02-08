package View;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class MultiplayerMenu
	extends JPanel
{
	private JButton    connect   = new JButton("Connect to player");
	private JTextField ip        = new JTextField();
	private JLabel     ip_text   = new JLabel("IP: ");
	private JTextField port      = new JTextField();
	private JLabel     port_text = new JLabel("Port: ");
	
	public MultiplayerMenu()
	{
		ip.setPreferredSize(new Dimension(200, 24));
		port.setPreferredSize(new Dimension(200, 24));
		
		this.add(connect);
		this.add(ip_text);
		this.add(ip);
		this.add(port_text);
		this.add(port);
	}
	
	public void addConnectListener(ActionListener listener)
	{
		connect.addActionListener(listener);
	}
	
	public String getIp()
	{
		return ip.getText();
	}
	
	public String getPort()
	{
		return port.getText();
	}
}
