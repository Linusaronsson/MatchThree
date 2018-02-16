package view;

import javax.swing.JOptionPane;

public class MessageDialog
{
	/**
	 * ...
	 *
	 * @param title   Title of the dialog.
	 * @param message Message of the dialog.
	 */
	public MessageDialog(final String title, final String message) {
		// Validate arguments //
		if (title == null) {
			throw new NullPointerException();
		}
		if (message == null) {
			throw new NullPointerException();
		}
		
		// Display message dialog //
		JOptionPane.showMessageDialog(
			null,
			title,
			message,
			JOptionPane.INFORMATION_MESSAGE
		);
	}
}
