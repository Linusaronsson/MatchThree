package controller;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import controller.UIController.View;
import model.Settings;
import multiplayer.Message;
import multiplayer.Server;
import view.Button;
import view.ErrorDialog;
import view.MultiplayerMenuView;

/**
 * ...
 */
public class MultiplayerSetupController
	implements ViewController
{
	/** View to control. */
	private MultiplayerMenuView multiplayerMenuView = null;
	
	/** Reference to UI controller. */
	private UIController uiController = null;
	
	/**
	 * ...
	 */
	private final class HoverListener
		implements MouseListener
	{
		/** ... */
		private Button target = null;
		
		/**
		 * Constructor.
		 *
		 * @param target ...
		 */
		private HoverListener(final Button target) {
			this.target = target;
		}
		
		@Override public void mouseClicked(final MouseEvent e) { }
		
		@Override public void mousePressed(final MouseEvent e) { }
		
		@Override public void mouseReleased(final MouseEvent e) { }
		
		@Override public void mouseEntered(final MouseEvent e) {
			target.setMask(Color.BLACK, 0.3f);
		}
		
		@Override public void mouseExited(final MouseEvent e) {
			target.setMask(Color.BLACK, 0.0f);
		}
	}
	
	/**
	 * Constructor.
	 *
	 * @param parent       ...
	 * @param uiController ...
	 * @param settings     ...
	 */
	public MultiplayerSetupController(
		final Container    parent,
		final UIController uiController,
		final Settings     settings
	) {
		// Validate arguments //
		if (parent == null) {
			throw new IllegalArgumentException("`parent` must not be null");
		}
		if (uiController == null) {
			throw new IllegalArgumentException(
				"`uiController` must not be null"
			);
		}
		if (settings == null) {
			throw new IllegalArgumentException("`settings` must not be null");
		}
		
		this.uiController = uiController;
		
		// Create view //
		multiplayerMenuView = new MultiplayerMenuView();
		
		// Add event listeners //
		multiplayerMenuView.addConnectListener(event -> {
			// Initiate connection //
			connect();
		});
		Button connect = multiplayerMenuView.getConnectButton();
		multiplayerMenuView.addHoverListener(
			new HoverListener(connect),
			connect
		);
		
		multiplayerMenuView.addBackListener(event -> {
			// Initiate connection //
			back();
		});
		
		Button back = multiplayerMenuView.getBackButton();
		multiplayerMenuView.addHoverListener(
			new HoverListener(back),
			back
		);
		
		// Add view to parent //
		parent.add(multiplayerMenuView);
	}
	
	@Override
	public void closeView() { }
	
	/**
	 * Handle connect event.
	 */
	private void connect() {
		// Get value from fields //
		String hostText = multiplayerMenuView.getIp();
		String portText = multiplayerMenuView.getPort();
		
		// Parse values //
		InetAddress host = null;
		int         port = Integer.parseInt(portText);
		try {
			host = InetAddress.getByName(hostText);
		} catch (UnknownHostException exception) {
			new ErrorDialog("Unknown Host", "Could not resolve host");
			
			// Soft return //
			return;
		}
		
		// Connect //
		DatagramSocket socket = null;
		try {
			Message message = new Message(Message.MessageType.REQUESTED_GAME);
			socket = new DatagramSocket();
			Server.sendDatagram(message, socket, host, port);
		} catch (SocketException exception) {
			new ErrorDialog(
				"Network Error",
				"Socket error while sending game request"
			);
			exception.printStackTrace();
		} finally {
			if (socket != null) {
				socket.close();
			}
		}
	}
	
	/**
	 * Handle back event.
	 */
	private void back() {
		uiController.changeView(View.MAIN_MENU);
	}
}
