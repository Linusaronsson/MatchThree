package view;

import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;

/**
 * Floating save dialog.
 */
@SuppressWarnings("serial")
public class SaveDialog
	extends JFileChooser
{
	/** Chosen button. */
	private int choice = 0;
	
	/**
	 * Create `SaveDialog`.
	 */
	public SaveDialog() {
		// TODO: Use `FileDialog` instead?
		//FileDialog fileDialog = new FileDialog(this);
		//fileDialog.setVisible(true);
		
		// Set file extension filter //
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
			"MatchThree Save File",
			"jewel"
		);
		setFileFilter(filter);
		
		// Display dialog //
		choice = showSaveDialog(null);
	}
	
	/**
	 * Get chosen file.
	 *
	 * @return The chosen file.
	 */
	public File getResult() {
		switch (choice) {
			case JFileChooser.APPROVE_OPTION: return getSelectedFile();
			case JFileChooser.CANCEL_OPTION:  return null;
			case JFileChooser.ERROR_OPTION:   return null;
			default: throw new IllegalStateException();
		}
	}
}
