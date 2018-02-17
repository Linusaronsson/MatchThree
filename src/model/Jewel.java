package model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Jewel type.
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
	 * @return A random jewel type.
	 */
	// TODO: Support returning a limited subset of jewels?
	public static Jewel random() {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
}
