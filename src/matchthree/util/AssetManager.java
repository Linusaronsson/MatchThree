package matchthree.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Loads and caches external resources.
 *
 * @author Erik Selstam
 * @author Erik Tran
 */
public final class AssetManager
{
	/** Path to resource directory. */
	private static final String DIR_RESOURCES = "resources";
	
	/** Loaded audio assets. */
	private static Map<String, Clip> audio = new HashMap<String, Clip>();
	
	/** Loaded image assets. */
	@SuppressWarnings("unused")
	private static Map<String, BufferedImage> images =
		new HashMap<String, BufferedImage>();
	
	/**
	 * Audio file specifier.
	 *
	 * @author Erik Selstam
	 * @author Erik Tran
	 */
	public enum Audio
	{
		/** Invalid move audio. */
		INVALID,
		
		/** Swap audio. */
		SWAP,
		
		/** Mouse entered audio. */
		MOUSEOVER,
		
		/** Select audio. */
		SELECT
	}
	
	/**
	 * Forbidden constructor.
	 *
	 * @author Erik Selstam
	 */
	private AssetManager() {
		throw new IllegalStateException();
	}
	
	/**
	 * Get the filename of an audio asset.
	 *
	 * @author Erik Tran
	 * @author Erik Selstam
	 * @param audio Audio asset to get filename of.
	 * @return Filename of audio asset.
	 */
	private static String getAudioName(final Audio audio) {
		String name = null;
		switch (audio) {
			case INVALID:   name = "InvalidMove.wav"; break;
			case SWAP:      name = "Swap.wav";        break;
			case MOUSEOVER: name = "MouseOver.wav";   break;
			case SELECT:    name = "Select.wav";      break;
			default: throw new IllegalStateException();
		}
		return name;
	}
	
	/**
	 * Load an audio asset.
	 *
	 * @author Erik Selstam
	 * @author Erik Tran
	 * @param name Filename of asset.
	 * @return     Loaded audio clip.
	 */
	public static Clip loadAudio(final String name) {
		// Skip loading already loaded audio //
		Clip clip = audio.get(name);
		if (clip != null) {
			// Return only when .wav is inactive, else do new File() [clone] in
			// order to avoid "buzzy" sound Buzz sound cause: rewinding
			// [setFramePosition(0)] when current .wav file is still active.
			if (!audio.get(name).isActive()) {
				return clip;
			}
		}
		
		// Read audio from file //
		File audioFile = new File(DIR_RESOURCES, name).getAbsoluteFile();
		AudioInputStream audioStream = null;
		try {
			audioStream = AudioSystem.getAudioInputStream(audioFile);
		} catch (final IOException exception) {
			System.err.printf(
				"Error while reading \"%s\"%s",
				audioFile,
				System.lineSeparator()
			);
			System.err.printf(
				"\t%s%s",
				exception,
				System.lineSeparator()
			);
			
			// Soft return //
			return null;
		} catch (final UnsupportedAudioFileException exception) {
			System.err.printf(
				"File type not supported for \"%s\"%s",
				audioFile,
				System.lineSeparator()
			);
			System.err.printf(
				"\t%s%s",
				exception,
				System.lineSeparator()
			);
			
			// Soft return //
			return null;
		}
		
		// Send audio to system mixer //
		// TODO: Split try clause into two blocks.
		try {
			clip = AudioSystem.getClip();
			clip.open(audioStream);
		} catch (final IOException exception) {
			System.err.printf(
				"IO error while loading \"%s\"%s",
				audioFile,
				System.lineSeparator()
			);
			System.err.printf(
				"\t%s%s",
				exception,
				System.lineSeparator()
			);
			
			// Soft return //
			return null;
		} catch (final LineUnavailableException exception) {
			System.err.printf(
				"Error loading audio \"%s\": LineUnavailableException%s",
				audioFile,
				System.lineSeparator()
			);
			System.err.printf(
				"\t%s%s",
				exception,
				System.lineSeparator()
			);
			
			// Soft return //
			return null;
		}
		
		// Store reference to clip //
		// TODO: Assert element does not already exist.
		audio.put(name, clip); // TODO: Element re-assertion if .wav active
		
		return clip;
	}
	
	/**
	 * Load an image asset.
	 *
	 * @author Erik Selstam
	 * @author Erik Tran
	 * @param name Filename of asset.
	 * @return     Loaded image buffer.
	 */
	public static BufferedImage loadImage(final String name) {
		File file = new File(DIR_RESOURCES, name);
		
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
		} catch (final IOException exception) {
			System.err.printf(
				"Failed to read \"%s\":%s",
				file,
				System.lineSeparator()
			);
			System.err.printf(
				"\t%s%s",
				exception,
				System.lineSeparator()
			);
		}
		return image;
	}
	
	/**
	 * Play swap audio clip.
	 *
	 * @author Erik Selstam
	 * @param audio ...
	 */
	public static void playAudio(final Audio audio) {
		/*
		// Validate argument //
		if (audio == null) {
			throw new NullPointerException();
		}
		
		// Get a reference to clip //
		String name = getAudioName(audio);
		Clip clip = AssetManager.loadAudio(name);
		
		// Rewind and play clip //
		clip.setFramePosition(0);
		clip.start();
		*/
	}
	
	/**
	 * Preemptively load all audio resources.
	 *
	 * @author Erik Selstam
	 * @author Erik Tran
	 */
	@SuppressWarnings("unused")
	private static void preloadAudio() {
		// TODO: Run automatically for all values of `Audio`.
		AssetManager.loadAudio(getAudioName(Audio.INVALID));
		AssetManager.loadAudio(getAudioName(Audio.SWAP));
		AssetManager.loadAudio(getAudioName(Audio.MOUSEOVER));
		AssetManager.loadAudio(getAudioName(Audio.SELECT));
	}
	
	/**
	 * Unload audio asset from memory.
	 *
	 * @author Erik Selstam
	 * @param name Filename of the asset.
	 */
	public static void unloadAudio(final String name) {
		// TODO: Not implemented.
		throw new IllegalStateException();
	}
	
	/**
	 * Unload image asset from memory.
	 *
	 * @author Erik Selstam
	 * @param name Filename of the asset.
	 */
	public static void unloadImage(final String name) {
		// TODO: Not implemented.
		throw new IllegalStateException();
	}
}
