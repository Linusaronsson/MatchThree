package Multiplayer;

import java.awt.Component;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JOptionPane;

import Model.MatchThreeModel;
import View.SwapView;

/**
 * ...
 */
public class OpponentController extends Thread {
	MatchThreeModel model = null;
    private DatagramSocket opponent;
    private DatagramPacket  in;
    byte[] inBuffer;
    private int port;

    public OpponentController(int port, MatchThreeModel model) {
    	this.model = model;
        this.port = port;
        try {
            //Listen on port (2000)
        	opponent = new DatagramSocket(port);
            //Setup receiving packet
            inBuffer = new byte[2048];
            in = new DatagramPacket(inBuffer, inBuffer.length);
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    public void close() {
    	this.interrupt();
    	opponent.close();
    }

    @Override
    public void run() {
        try {
            while(!interrupted()) {
            	opponent.receive(in);
                ByteArrayInputStream byteInStream = new ByteArrayInputStream(inBuffer);
                ObjectInputStream inStream = new ObjectInputStream(byteInStream);
                UpdateCell m = (UpdateCell) inStream.readObject();
                model.set(m.getX(), m.getY(), m.getJewelType());
                System.out.println("OpponentController Recieved: \n" + m.toString());
                inStream.close();
            }
        } catch(SocketException e) {
            return;
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}