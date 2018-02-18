package view;

import controller.MatchThreeController;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.LayoutManager;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import model.MatchThreeModel;
import view.GridView.Audio;

/**
 * ...
 */
@SuppressWarnings("serial")
public class SingleplayerView
	extends JPanel
{
	/** ... */
	private static final int GAP_HORIZONTAL = 10;
	
	/** ... */
	private static final int GAP_VERTICAL = 10;
	
	/** ... */
	private ButtonPanel buttonPanel = new ButtonPanel();
	
	/** ... */
	private GridView gridView = null;
	
	/** ... */
	private MatchThreeController matchThreeController = null;
	
	/** ... */
	private MatchThreeUI matchThreeUI = null;
	
	/**
	 * ...
	 *
	 * @param matchThreeModel ...
	 */
	public SingleplayerView(
			final MatchThreeModel matchThreeModel,
			final int jewelVersion
			) {
		// Create game //
		// TODO: Move this logic elsewhere.
		gridView = new GridView(matchThreeModel, jewelVersion);
		matchThreeUI = new MatchThreeUI(matchThreeModel, gridView);
		matchThreeController = new MatchThreeController(
			matchThreeModel,
			matchThreeUI,
			gridView
		);
		
		// Set properties //
		// TODO: Stopgap hack.
		setBackground(new Color(0x11, 0x11, 0x11));
		
		// Set layout //
		LayoutManager layout = new BoxLayout(this, BoxLayout.X_AXIS);
		setLayout(layout);
		
		// Assemble view //
		add(buttonPanel);
		add(matchThreeUI);
		
	}
	
	public void addBackListener(final MouseListener listener) {
		buttonPanel.addBackListener(listener);
	}
	
	public void addBackClickListener(final ActionListener actionListener) {
		// TODO Auto-generated method stub
		buttonPanel.addBackClickListener(actionListener);
	}
	
	public void addButtonV1Listener(final MouseListener listener) {
		buttonPanel.addButtonV1Listener(listener);
	}
	
	public void addButtonV2Listener(final MouseListener listener) {
		buttonPanel.addButtonV2Listener(listener);
	}
	
	/**
	 * ...
	 *
	 * @return ...
	 */
	// TODO: Remove this method.
	public GridView getGridView() {
		return gridView;
	}
	
	/**
	 * ...
	 *
	 * @return ...
	 */
	// TODO: Try to remove this method.
	public MatchThreeController getMatchThreeController() {
		return matchThreeController;
	}
	
	/**
	 * ...
	 *
	 * @return ...
	 */
	// TODO: Remove this method.
	public MatchThreeUI getView() {
		return matchThreeUI;
	}
	
	/**
	 * Set reference to parent window.
	 *
	 * @param window The parent window.
	 */
	public void setWindow(final Window window) {
		matchThreeController.setWindow(window);
	}
	
	
	
	public void changeSprites(int i) {
		gridView.changeSprites(i);
	}

	public void playAudio(Audio audio) {
		// TODO Auto-generated method stub
		gridView.playAudio(audio);
		
	}
	public Button getBackButton() {
		return buttonPanel.getBackButton();
	}
	public Button getV1Button() {
		return buttonPanel.getV1Button();
	}
	public Button getV2Button() {
		return buttonPanel.getV2Button();
	}
}
