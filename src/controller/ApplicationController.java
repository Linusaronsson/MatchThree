package controller;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ApplicationController
{
	public ApplicationController() {
		// Set application-wide properties //
		setProperties();
		
		// Create main window //
		new MainWindowController();
	}
	
	/**
	 * Set application properties.
	 */
	private static void setProperties() {
		// Use native menu bar on macOS/OS X //
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		// TODO: Does not appear important or is misused.
		System.setProperty(
			"com.apple.mrj.application.apple.menu.about.name",
			"Test"
		);
		
		// Set look and feel //
		String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (ClassNotFoundException exception) {
			System.err.println(exception);
		} catch (InstantiationException exception) {
			System.err.println(exception);
		} catch (IllegalAccessException exception) {
			System.err.println(exception);
		} catch (UnsupportedLookAndFeelException exception) {
			System.err.println(exception);
		}
	}
}
