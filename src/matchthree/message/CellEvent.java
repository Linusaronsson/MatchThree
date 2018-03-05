package matchthree.message;

import matchthree.model.Coordinate;
import matchthree.model.Jewel;

/**
 * Cell update event.
 *
 * @author Linus Aronsson
 */
public class CellEvent
{
	/** Cell type. */
	private Jewel cellType;
	
	/** Cell position. */
	private Coordinate position;
	
	/**
	 * Constructor.
	 *
	 * @author Linus Aronsson
	 * @param position Cell position to embed.
	 * @param cellType Cell type to embed.
	 */
	public CellEvent(final Coordinate position, final Jewel cellType) {
		this.cellType = cellType;
		this.position = position;
	}
	
	/**
	 * Get cell position.
	 *
	 * @author Linus Aronsson
	 * @return The cell position.
	 */
	public Coordinate getPos() {
		return position;
	}
	
	/**
	 * Get cell type.
	 *
	 * @author Linus Aronsson
	 * @return The cell type.
	 */
	public Jewel getType() {
		return cellType;
	}
}
