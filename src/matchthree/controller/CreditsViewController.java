package matchthree.controller;

import java.awt.Container;
import matchthree.view.CreditsView;

/**
 * ...
 *
 * @author Erik Tran
 * @author Erik Selstam
 */
public class CreditsViewController
	implements ViewController
{
	/**
	 * Constructor.
	 *
	 * @author Erik Tran
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
