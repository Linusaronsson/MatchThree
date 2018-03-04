package matchthree.model;

/**
 * Serialize board content for storage.
 *
 * @author Erik Selstam
 */
public final class Serialize
{
	/**
	 * Forbidden constructor.
	 *
	 * @author Erik Selstam
	 */
	private Serialize() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Exception for unrecognized data.
	 *
	 * @author Erik Selstam
	 */
	@SuppressWarnings("serial")
	// TODO: Is it in any way ok to have this be a static class?
	public static class UnsupportedTypeException
		extends Exception
	{
		/**
		 * Constructor.
		 *
		 * @author Erik Selstam
		 */
		public UnsupportedTypeException() { }
	}
	
	/**
	 * Serialize an array of jewels.
	 *
	 * @author Erik Selstam
	 * @param jewels Array of jewels to serialize.
	 * @return       Serialized string.
	 * @throws UnsupportedTypeException On receiving unknown data.
	 */
	public static String serialize(final Jewel[] jewels)
		throws UnsupportedTypeException
	{
		String string = "";
		for (Jewel jewel : jewels) {
			switch (jewel) {
				case DIAMOND:  string += "d"; break;
				case EMERALD:  string += "e"; break;
				case RUBY:     string += "r"; break;
				case SAPPHIRE: string += "s"; break;
				case TOPAZ:    string += "t"; break;
				default: throw new UnsupportedTypeException();
			}
		}
		return string;
	}
}
