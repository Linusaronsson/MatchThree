package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import Model.*;
import Model.MatchThreeModel;
import View.*;

/**
 * MVC controller.
 */
public class MatchThreeController
{
	private Coordinate      activeCell = null;
	private MatchThreeModel model      = null;
	private MatchThreeUI    view       = null;
	private Window          window     = null;
	
	/**
	 * Listens for board cell actions (clicks).
	 */
	class BoardListener
		implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			// Validate argument //
			if (event == null) {
				throw new NullPointerException();
			}
			
			// Get cell coordinates //
			// TODO: Assert event values?
			Cell       cell        = (Cell) event.getSource();
			Coordinate tmp         = cell.getPosition(); // TODO: Implement `clone`.
			Coordinate clickedCell = new Coordinate(tmp.x, tmp.y);
			
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
		MatchThreeModel model,
		MatchThreeUI view
	) {
		// Validate arguments //
		if (model == null || view == null) {
			throw new NullPointerException();
		}
		
		this.model = model;
		this.view  = view;
		
		// Register event listeners //
		view.addBoardListener(new BoardListener());
	}
	
	/**
	 * Swap two cells.
	 *
	 * @param position1 Coordinates of first cell.
	 * @param position2 Coordinates of second cell.
	 */
	private void moveCell(Coordinate from, Coordinate to)
	{
		// Validate arguments //
		// TODO: Do bounds-checking on coordinates?
		if (from == null || to == null) {
			throw new NullPointerException();
		}
		
		// Swap cells //
		switch (model.move(from, to)) {
			case OK:
				// Play swap audio //
				view.playAudioSwap();
				break;
			case BAD:
				// TODO: Handle null values.
				window.showError("Invalid move");
				break;
			case CANCEL:
				break;
			default:
				// TODO: Throw exception.
				break;
		}
		
		// Update view //
		//view.update();
	}
	
	/**
	 * Resets model to its initial state.
	 */
	protected void restartGame()
	{
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
	private void setActiveCell(Coordinate position)
	{
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
	public void setWindow(Window window)
	{
		this.window = window;
	}
}
