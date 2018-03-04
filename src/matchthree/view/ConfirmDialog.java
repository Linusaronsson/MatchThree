package matchthree.view;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Floating save dialog.
 *
 * @author Erik Selstam
 */
@SuppressWarnings("serial")
public class ConfirmDialog
	extends JFileChooser
{
	/** Chosen button. */
	private int response = 0;
	
	/**
	 * Response type.
	 *
	 * @author Erik Selstam
	 */
	// TODO: Remove?
	public enum Response
	{
		/** The request was canceled. */
		CANCEL,
		
		/** The request was accepted. */
		OK
	}
	
	/**
	 * Constructor.
	 *
	 * @author Erik Selstam
	 * @param title Dialog title.
	 * @param body  Dialog body.
	 */
	public ConfirmDialog(
		final String title,
		final String body)
	{
		// Validate arguments //
		// TODO: Make `body` optional?
		if (title == null || title.length() <= 0) {
			throw new IllegalArgumentException("`title` not provided");
		}
		if (body == null || body.length() <= 0) {
			throw new IllegalArgumentException("`body` not provided");
		}
		
		// Display dialog //
		response = JOptionPane.showConfirmDialog(
			null,
			body,
			title,
			JOptionPane.OK_CANCEL_OPTION,
			JOptionPane.QUESTION_MESSAGE
		);
	}
	
	/**
	 * Get response.
	 *
	 * @author Erik Selstam
	 * @return The response from the dialog.
	 */
	public boolean getResponse() {
		switch (response) {
			case JOptionPane.CANCEL_OPTION: return false;
			case JOptionPane.CLOSED_OPTION: return false;
			case JOptionPane.NO_OPTION:     return false;
			//case JOptionPane.OK_OPTION:     return true;
			case JOptionPane.YES_OPTION:    return true;
			default:
				throw new IllegalStateException("Unknown value of `response`");
		}
	}
}
