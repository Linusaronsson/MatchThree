package matchthree.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Jewel type.
 *
 * @author Erik Selstam
 */
public enum Jewel
{
	/** Diamond. */
	DIAMOND,
	
	/** Emerald. */
	EMERALD,
	
	/** Ruby. */
	RUBY,
	
	/** Sapphire. */
	SAPPHIRE,
	
	/** Topaz. */
	TOPAZ;
	
	/** Jewel types. */
	// TODO: Use array instead?
	private static final List<Jewel> VALUES =
		Collections.unmodifiableList(Arrays.asList(values()));
	
	/** Jewel type count. */
	private static final int SIZE = VALUES.size();
	
	/** PRNG state. */
	private static final Random RANDOM = new Random();
	
	/**
	 * Return a random jewel.
	 *
	 * @author Erik Selstam
	 * @return A random jewel type.
	 */
	// TODO: Support returning a limited subset of jewels?
	public static Jewel random() {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
	
	/**
	 * Get the string representation of the jewel.
	 *
	 * @author Erik Selstam
	 * @return String representation.
	 */
	public String toString() {
		switch (this) {
			case DIAMOND:  return "Diamond";
			case EMERALD:  return "Emerald";
			case RUBY:     return "Ruby";
			case SAPPHIRE: return "Sapphire";
			case TOPAZ:    return "Topaz";
			default: throw new IllegalStateException();
		}
	}
}
