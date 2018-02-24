package controller;

import java.awt.Container;
import view.CreditsView;

/**
 * ...
 */
public class CreditsViewController
	implements ViewController
{
	/**
	 * Constructor.
	 *
	 * @param parent ...
	 */
	public CreditsViewController(final Container parent) {
		// Validate arguments //
		if (parent == null) {
			throw new IllegalArgumentException("`parent` must not be null");
		}
		
		// Create credits view //
		CreditsView creditsView = new CreditsView();
		
		// Add view to parent //
		parent.add(creditsView);
	}
	
	@Override
	public void closeView() { }
}
