import java.awt.Component;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class Server
	extends Thread
{
	ServerSocket listener = null;
	int          port;
	Component    c;
	
	public Server(int port, Component c)
	{
		this.port = port;
		this.c    = c;
		
		try {
			listener = new ServerSocket(port);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void run()
	{
		Socket s = null;
		while (true) {
			try {
				s = listener.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			new ClientHandler(s, c);
		}
	}
}
