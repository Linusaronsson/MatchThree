package matchthree.controller;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import matchthree.model.Coordinate;
import matchthree.model.Jewel;
import matchthree.model.MatchThreeModel;
import matchthree.model.Serialize;
import matchthree.model.Settings;
import matchthree.model.Settings.Style;
import matchthree.util.AssetManager;
import matchthree.view.Cell;
import matchthree.view.ErrorDialog;
import matchthree.view.GridView;
import matchthree.view.MatchThreeUI;
import matchthree.view.MessageDialog;
import matchthree.view.SaveDialog;

/**
 * MatchThree game controller and view controller for `GridView`.
 *
 * @author Erik Selstam
 * @author Erik Tran
 * @author Linus Aronsson
 */
public class GridViewController
{
	/** Background color. */
	private static final Color COLOR_BACKGROUND = new Color(0x22, 0x22, 0x22);
	
	/** Currently active cell. */
	private Coordinate activeCell = null;
	
	/** Grid view. */
	private GridView gridView = null;
	
	/** MatchThree model. */
	private MatchThreeModel matchThreeModel = null;
	
	/** Game view. */
	private MatchThreeUI matchThreeUI = null;
	
	/** Reference to UI controller. */
	private UIController uiController = null;
	
	/**
	 * Listens for board cell actions (mouse hover).
	 *
	 * @author Erik Tran
	 * @author Erik Selstam
	 */
	final class CellHoverListener
		implements MouseListener
	{
		/** ... */
		private Cell cell = null;
		
		/**
		 * ...
		 *
		 * @author Erik Tran
		 * @param cell ...
		 */
		private CellHoverListener(final Cell cell) {
			// TODO Auto-generated constructor stub
			this.cell = cell;
		}
		
		@Override
		public void mouseClicked(final MouseEvent e) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void mouseEntered(final MouseEvent e) {
			// TODO Auto-generated method stub
			if (!cell.isActive()) {
				cell.setMask(COLOR_BACKGROUND, 0.5f);
			}
		}
		
		@Override
		public void mouseExited(final MouseEvent e) {
			if (!cell.isActive()) {
				cell.setMask(COLOR_BACKGROUND, 0f);
			}
		}
		
		@Override
		public void mousePressed(final MouseEvent e) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void mouseReleased(final MouseEvent e) {
			// TODO Auto-generated method stub
		}
	}
	
	/**
	 * Constructor.
	 *
	 * @author Erik Selstam
	 * @author Erik Tran
	 * @author Linus Aronsson
	 * @param parent          Parent view to use.
	 * @param uiController    UI controller to use.
	 * @param settings        Application settings.
	 * @param matchThreeModel Model to use, if any.
	 * @param matchThreeUI    ...
	 */
	// TODO: Is this controller allowed to access `MatchThreeUI`?
	public GridViewController(
		final Container       parent,
		final UIController    uiController,
		final Settings        settings,
		final MatchThreeModel matchThreeModel,
		final MatchThreeUI    matchThreeUI)
	{
		// Validate arguments //
		if (parent == null) {
			throw new IllegalArgumentException("`parent` must not be null");
		}
		if (uiController == null) {
			throw new IllegalArgumentException(
				"`uiController` must not be null"
			);
		}
		if (settings == null) {
			throw new IllegalArgumentException("`settings` must not be null");
		}
		if (matchThreeModel == null) {
			throw new IllegalArgumentException(
				"`matchThreeModel` must not be null"
			);
		}
		
		this.matchThreeModel = matchThreeModel;
		this.uiController    = uiController;
		this.matchThreeUI    = matchThreeUI;
		
		// Create view //
		gridView = new GridView(matchThreeModel, settings.getStyle());
		
		// Add event listeners //
		gridView.addBoardListener(event -> {
			// Handle click //
			handleAction(event);
		});
		for (final Cell cell : gridView.getBoard()) {
			// Handle cell hover actions //
			gridView.addCellHoverListener(new CellHoverListener(cell), cell);
		}
		
		// Add view to parent //
		parent.add(gridView);
	}
	
	/**
	 * Change images on buttons.
	 *
	 * @author Erik Tran
	 * @param style Visual style.
	 */
	public void changeSprites(final Style style) {
		gridView.changeSprites(style);
	}
	
	/**
	 * Get a reference to the game model.
	 *
	 * @author Erik Selstam
	 * @return The game model.
	 */
	public MatchThreeModel getModel() {
		return matchThreeModel;
	}
	
	/**
	 * Handle board cell action (click).
	 *
	 * @author Erik Selstam
	 * @param event Event object.
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
	 * @author Erik Selstam
	 * @author Erik Tran
	 * @author Linus Aronsson
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
			case OK:
				AssetManager.playAudio(AssetManager.Audio.SWAP);
				matchThreeModel.setMovesLeft();
				break;
			case BAD:
				AssetManager.playAudio(AssetManager.Audio.INVALID);
				break;
			case CANCEL:
				break;
			default:
				throw new IllegalStateException();
		}
		
		// Update view //
		//matchThreeUI.update();
	}
	
	/**
	 * Resets model to its initial state.
	 *
	 * @author Erik Selstam
	 * @author Erik Tran
	 */
	protected void restartGame() {
		// Reset active cell //
		setActiveCell(null);
		
		// Reinitialize model //
		// TODO: Make sure model notifies observers.
		matchThreeModel.init();
	}
	
	/**
	 * Save the game.
	 *
	 * @author Erik Selstam
	 */
	public void saveGame() {
		// Serialize board content //
		Jewel[] board = matchThreeModel.getBoard();
		String serial = null;
		try {
			serial = Serialize.serialize(board);
		} catch (final Serialize.UnsupportedTypeException exception) {
			new ErrorDialog(
				"Serialization failed",
				"Could not serialize model contents"
			);
			return;
		}
		
		// Get save destination //
		File file = new SaveDialog().getResult();
		
		// Cancel if no file was chosen //
		if (file == null) {
			return;
		}
		
		// Save game //
		BufferedWriter out = null;
		try {
			// Log intent //
			System.out.printf(
				"Saving game to \"%s\"...%s",
				file.toString(),
				System.lineSeparator()
			);
			
			// Create output buffer //
			FileWriter writer = new FileWriter(file);
			out = new BufferedWriter(writer);
			
			// Get state //
			int score = matchThreeModel.getScore();
			int width = matchThreeModel.getWidth();
			
			// Print to file //
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
		} catch (final IOException exception) {
			new ErrorDialog("Save game failed", "Failed to save game");
			System.err.println(exception);
			return;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (final IOException exception) {
					System.err.println("Failed to close file");
					System.err.println(exception);
				}
			}
		}
		
		// Display confirmation //
		new MessageDialog("Game saved", "Game saved successfully");
	}
	
	/**
	 * Set or unset the currently active cell.
	 *
	 * @author Erik Selstam
	 * @author Erik Tran
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
}
