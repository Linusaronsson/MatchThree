import Controller.*;
import GUI.GUI;
import Model.*;
import View.*;
import java.awt.Component;
import java.awt.PopupMenu;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

/**
 * MatchThree game.
 */
	class ClientHandler {
		public ClientHandler(Socket client, Component c) {
				System.out.printf("Client connected from \nport: %d\nip: %s\n", client.getPort(), client.getInetAddress());
				JOptionPane.showMessageDialog(c,
				                              "Client connected",
				                              "asd",
				                              JOptionPane.INFORMATION_MESSAGE);
			}
	}
	
	
	class Server extends Thread {
		ServerSocket listener = null;
		int port;
		Component c;
		public Server(int port, Component c) {
			this.port = port;
			this.c = c;
			
			try {
				listener = new ServerSocket(port);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		public void run() {
			Socket s = null;
			while(true) {
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

	public class MatchThree {

		/**
		 * Program entry point.
		 *
		 * @param args Program arguments.
		 * @throws IOException 
		 */
		public static void main(String[] args) throws IOException
		{
		
			//Initialize GUI
			GUI ui = new GUI();
			
			//Setup server listener
			//Server s = new Server(3333, ui);
			//s.start();
	
			
		}
	}