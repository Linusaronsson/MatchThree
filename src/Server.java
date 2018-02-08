import java.awt.Component;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ...
 */
class Server
	extends Thread
{
	private Component    component = null;
	private ServerSocket listener  = null;
	private int          port      = 0;
	
	/**
	 * ...
	 */
	public Server(int port, Component component)
	{
		try {
			listener = new ServerSocket(port);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		this.component = component;
		this.port      = port;
	}
	
	/**
	 * ...
	 */
	public void run()
	{
		Socket socket = null;
		while (true) {
			try {
				socket = listener.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			new ClientHandler(socket, component);
		}
	}
}
