package matchthree.view;

import javax.swing.JOptionPane;

/**
 * Floating error dialog.
 *
 * @author Erik Selstam
 */
public class ErrorDialog
{
	/**
	 * Create `ErrorDialog`.
	 *
	 * @author Erik Selstam
	 * @param title   Title of the dialog.
	 * @param message Message of the dialog.
	 */
	public ErrorDialog(final String title, final String message) {
		// Validate arguments //
		if (title == null) {
			throw new NullPointerException();
		}
		if (message == null) {
			throw new NullPointerException();
		}
		
		// Display error dialog //
		JOptionPane.showMessageDialog(
			null,
			message,
			title,
			JOptionPane.ERROR_MESSAGE
		);
	}
}
