package controller;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import model.Coordinate;
import model.Jewel;
import model.MatchThreeModel;
import model.Serialize;
import model.Settings;
import model.Settings.Style;
import util.AssetManager;
import util.Properties;
import view.Cell;
import view.ErrorDialog;
import view.GameFinished;
import view.GridView;
import view.MatchThreeUI;
import view.MessageDialog;
import view.SaveDialog;

/**
 * MatchThree game controller.
 */
public class GridViewController
{
	
	/** ... */
	private static final Color COLOR_BACKGROUND =
		Properties.getColorBackground();
	
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
	 */
	final class CellHoverListener
		implements MouseListener
	{
		/** ... */
		private Cell cell = null;
		
		/**
		 * ...
		 *
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
	 * @param parent          Parent view to use.
	 * @param uiController    ...
	 * @param settings        ...
	 * @param matchThreeModel Model to use, if any.
	 */
	public GridViewController(
		final Container       parent,
		final UIController    uiController,
		final Settings        settings,
		final MatchThreeModel matchThreeModel,
		final MatchThreeUI	  matchThreeUI
	) {
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
		for (Cell cell : gridView.getBoard()) {
			// Handle cell hover actions //
			gridView.addCellHoverListener(new CellHoverListener(cell), cell);
		}
		
		// Add view to parent //
		parent.add(gridView);
	}
	
	/**
	 * Change images on buttons.
	 *
	 * @param style Visual style.
	 */
	public void changeSprites(final Style style) {
		gridView.changeSprites(style);
	}
	
	/**
	 * ...
	 *
	 * @return ...
	 */
	public MatchThreeModel getModel() {
		return matchThreeModel;
	}
	
	/**
	 * Handle board cell action (click).
	 *
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
}
