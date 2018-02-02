import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Cell symbol enum.
 */
public enum Jewel
{
	DIAMOND,
	EMERALD,
	RUBY,
	SAPPHIRE,
	TOPAZ;
	
	// TODO: Use array instead?
	private static final List<Jewel> VALUES =
		Collections.unmodifiableList(Arrays.asList(values()));
	private static final int    SIZE   = VALUES.size();
	private static final Random RANDOM = new Random();
	
	/**
		* Return a random jewel.
		*
		* @return A random jewel type.
		*/
	// TODO: Support returning a limited subset of jewels?
	public static Jewel random()
	{
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
}
