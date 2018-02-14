package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Coordinate;
import model.MatchThreeModel;
import view.Cell;
import view.MatchThreeUI;
import view.Window;

/**
 * MatchThree game controller.
 */
public class MatchThreeController
{
	/** ... */
	private Coordinate activeCell = null;
	
	/** ... */
	private MatchThreeModel model = null;
	
	/** ... */
	private MatchThreeUI view = null;
	
	/** ... */
	private Window window = null;
	
	/**
	 * Listens for board cell actions (clicks).
	 */
	class BoardListener
		implements ActionListener
	{
		@Override
		public void actionPerformed(final ActionEvent event) {
			// Validate argument //
			if (event == null) {
				throw new NullPointerException();
			}
			
			// Get cell coordinates //
			// TODO: Assert event values?
			// TODO: Implement `clone`.
			Cell       cell        = (Cell) event.getSource();
			Coordinate tmp         = cell.getPosition();
			Coordinate clickedCell = new Coordinate(tmp.getX(), tmp.getY());
			
			// Activate cell if appropriate //
			if (activeCell == null) {
				setActiveCell(clickedCell);
				return;
			}
			
			// Deactivate and swap cells //
			Coordinate from = activeCell;
			setActiveCell(null);
			moveCell(from, clickedCell);
			
			// Update score //
			view.updateScore();
		}
	}
	
	/**
	 * Constructor for `MatchThreeController`.
	 *
	 * @param model Model to use.
	 * @param view  View to use.
	 */
	public MatchThreeController(
		final MatchThreeModel model,
		final MatchThreeUI    view
	) {
		// Validate arguments //
		if (model == null || view == null) {
			throw new NullPointerException();
		}
		
		// Register event listeners //
		view.addBoardListener(new BoardListener());
		
		this.model = model;
		this.view  = view;
	}
	
	/**
	 * Swap two cells.
	 *
	 * @param from Coordinates of first cell.
	 * @param to   Coordinates of second cell.
	 */
	private void moveCell(final Coordinate from, final Coordinate to) {
		// Validate arguments //
		// TODO: Do bounds-checking on coordinates?
		if (from == null || to == null) {
			throw new NullPointerException();
		}
		
		// Swap cells //
		switch (model.move(from, to)) {
			case OK:     view.playAudio(MatchThreeUI.Audio.SWAP);    break;
			case BAD:    view.playAudio(MatchThreeUI.Audio.INVALID); break;
			case CANCEL: break;
			default: throw new IllegalStateException();
		}
		
		// Update view //
		//view.update();
	}
	
	/**
	 * Resets model to its initial state.
	 */
	protected void restartGame() {
		// Reset active cell //
		setActiveCell(null);
		
		// Reinitialize model //
		model.init();
		
		// Update view //
		//view.update();
		view.updateScore();
	}
	
	/**
	 * Set or unset the currently active cell.
	 *
	 * @param position Coordinates of the cell to make active, or null if none.
	 */
	private void setActiveCell(final Coordinate position) {
		// Deactivate cell if appropriate //
		if (activeCell != null) {
			view.setCellState(activeCell, false);
		}
		
		// Activate new cell if appropriate //
		if (position != null) {
			view.setCellState(position, true);
		}
		
		// Update reference //
		activeCell = position;
	}
	
	/**
	 * Set reference to parent window.
	 *
	 * @param window The parent window.
	 */
	public void setWindow(final Window window) {
		this.window = window;
	}
}
