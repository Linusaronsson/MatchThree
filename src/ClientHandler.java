import java.awt.Component;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 * ...
 */
class ClientHandler
{
	/**
	 * ...
	 *
	 * @param client    ...
	 * @param component ...
	 */
	ClientHandler(final Socket client, final Component component) {
		System.out.printf(
			"Client connected from \nport: %d\nip: %s\n",
			client.getPort(),
			client.getInetAddress()
		);
		JOptionPane.showMessageDialog(
			component,
			"Client connected",
			"Foo bar",
			JOptionPane.INFORMATION_MESSAGE
		);
	}
}