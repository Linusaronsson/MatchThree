import java.awt.Component;
import java.awt.PopupMenu;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

import Model.*;
import Controller.*;
import GUI.GUI;
import View.*;

/**
 * MatchThree game.
 */
public class MatchThree
{
	class clientHandler {
		public clientHandler(Socket client, Component c) {
				JOptionPane.showMessageDialog(c,
				                              "Client connected",
				                              "asd",
				                              JOptionPane.INFORMATION_MESSAGE);
			}
	}
	
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
		
		/*
		ServerSocket server  = new ServerSocket(9000);
	
		//Setup server socket listener
		new Thread() {
			public void run() {
				while(true) {
					Socket client = server.accept();
					new clientHandler(client, ui);
				}
			}
		};
		*/
	}
}
