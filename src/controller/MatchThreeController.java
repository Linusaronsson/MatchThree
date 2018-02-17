package controller;

import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import model.Coordinate;
import model.Jewel;
import model.MatchThreeModel;
import model.Serialize;
import view.Cell;
import view.ErrorDialog;
import view.GridView;
import view.MatchThreeUI;
import view.MessageDialog;
import view.SaveDialog;
import view.Window;

/**
 * MatchThree game controller.
 */
public class MatchThreeController
{
	/** ... */
	private Coordinate activeCell = null;
	
	/** ... */
	private GridView gridView = null;
	
	/** ... */
	private MatchThreeModel matchThreeModel = null;
	
	/** ... */
	private MatchThreeUI matchThreeUI = null;
	
	/** ... */
	private Window window = null;
	
	/**
	 * Constructor for `MatchThreeController`.
	 *
	 * @param model Model to use.
	 * @param view  View to use.
	 */
	public MatchThreeController(
		final MatchThreeModel model,
		final MatchThreeUI    view,
		final GridView        gridView
	) {
		// Validate arguments //
		if (model == null || view == null) {
			throw new NullPointerException();
		}
		
		// Register event listeners //
		gridView.addBoardListener(event -> {
			// Handle click //
			handleAction(event);
		});
		
		this.gridView        = gridView;
		this.matchThreeModel = model;
		this.matchThreeUI    = view;
	}
	
	/**
	 * Handle board cell action (click).
	 */
	public void handleAction(final ActionEvent event) {
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
		switch (matchThreeModel.move(from, to)) {
			case OK:     gridView.playAudio(GridView.Audio.SWAP);    break;
			case BAD:    gridView.playAudio(GridView.Audio.INVALID); break;
			case CANCEL: break;
			default: throw new IllegalStateException();
		}
		
		// Update view //
		//matchThreeUI.update();
	}
	
	/**
	 * Resets model to its initial state.
	 */
	protected void restartGame() {
		// Reset active cell //
		setActiveCell(null);
		
		// Reinitialize model //
		// TODO: Make sure model notifies observers.
		matchThreeModel.init();
	}
	
	/**
	 * Set or unset the currently active cell.
	 *
	 * @param position Coordinates of the cell to make active, or null if none.
	 */
	private void setActiveCell(final Coordinate position) {
		// Deactivate cell if appropriate //
		if (activeCell != null) {
			gridView.setCellState(activeCell, false);
		}
		
		// Activate new cell if appropriate //
		if (position != null) {
			gridView.setCellState(position, true);
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
	
	/**
	 * Save the game.
	 */
	public void saveGame() {
		// Serialize board content //
		Jewel[] board = matchThreeModel.getBoard();
		String serial = null;
		try {
			serial = Serialize.serialize(board);
		} catch (Serialize.UnsupportedTypeException e) {
			new ErrorDialog(
				"Serialization failed",
				"Could not serialize model contents"
			);
			return;
		}
		
		// Get score //
		int score = matchThreeModel.getScore();
		
		// Get width //
		int width = matchThreeModel.getWidth();
		
		// Get save destination //
		File file = new SaveDialog().getResult();
		
		// Cancel if no file was chosen //
		if (file == null) {
			return;
		}
		
		// Save game //
		BufferedWriter out = null;
		try {
			System.out.printf(
				"Saving game to \"%s\"...%s",
				file.toString(),
				System.lineSeparator()
			);
			
			FileWriter writer = new FileWriter(file);
			out = new BufferedWriter(writer);
			
			out.write("MatchThree Save Data Version 1.0\n");
			out.write("score: ");
			out.write(String.valueOf(score));
			out.write("\n");
			out.write("width: ");
			out.write(String.valueOf(width));
			out.write("\n");
			out.write("board: ");
			out.write(serial.toString());
			out.write("\n");
		} catch (IOException exception) {
			new ErrorDialog("Save game failed", "Failed to save game");
			System.err.println(exception);
			return;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException exception) {
					System.err.println("Failed to close file");
					System.err.println(exception);
				}
			}
		}
		
		// Display confirmation //
		new MessageDialog("Game saved", "Game saved successfully");
	}
}
