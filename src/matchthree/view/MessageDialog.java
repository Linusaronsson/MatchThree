package matchthree.view;

import javax.swing.JOptionPane;

/**
 * Floating message dialog.
 *
 * @author Erik Selstam
 */
public class MessageDialog
{
	/**
	 * Create `MessageDialog`.
	 *
	 * @author Erik Selstam
	 * @param title   Title of the dialog.
	 * @param message Body of the dialog.
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
			message,
			title,
			JOptionPane.INFORMATION_MESSAGE
		);
	}
}
