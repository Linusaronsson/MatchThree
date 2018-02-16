package view;

import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;

@SuppressWarnings("serial")
public class SaveDialog
	extends JFileChooser
{
	private int choice = 0;
	
	public SaveDialog() {
		// TODO: Use `FileDialog` instead?
		//FileDialog fileDialog = new FileDialog(this);
		//fileDialog.setVisible(true);
		
		// Create dialog //
		//JFileChooser chooser = new JFileChooser();
		
		// Set file extension filter //
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
			"MatchThree Save File",
			"jewel"
		);
		
		setFileFilter(filter);
		
		// Display dialog //
		choice = showSaveDialog(null);
	}
	
	public File getResult() {
		switch (choice) {
			case JFileChooser.APPROVE_OPTION: return getSelectedFile();
			case JFileChooser.CANCEL_OPTION:  return null;
			case JFileChooser.ERROR_OPTION:   return null;
			default: throw new IllegalStateException();
		}
	}
}
