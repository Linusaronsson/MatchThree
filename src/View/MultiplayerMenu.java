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
	private JButton    connect;
	private JTextField ip;
	private JLabel     ip_text;
	private JTextField port;
	private JLabel     port_text;
	
	public MultiplayerMenu()
	{
		connect   = new JButton("Connect to player");
		ip        = new JTextField();
		ip_text   = new JLabel("IP: ");
		port      = new JTextField();
		port_text = new JLabel("Port: ");
		
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
