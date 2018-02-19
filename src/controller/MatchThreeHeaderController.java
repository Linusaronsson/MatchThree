package controller;

import java.awt.Container;
import model.MatchThreeModel;
import model.Settings;
import view.Score;

public class MatchThreeHeaderController
{
	public MatchThreeHeaderController(
		final Container       parent,
		final UIController    uiController,
		final Settings        settings,
		final MatchThreeModel matchThreeModel
	) {
		// Validate arguments //
		if (parent == null) {
			throw new NullPointerException();
		}
		if (matchThreeModel == null) {
			throw new NullPointerException();
		}
		
		// Create view //
		// TODO: Consider separate controller.
		Container matchThreeUI = new Score(matchThreeModel);
		
		// Add view to parent //
		parent.add(matchThreeUI);
	}
}
