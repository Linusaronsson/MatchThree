package util;

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

public class AssetManager
{
	/** ... */
	private static final String DIR_RESOURCES = "resources";
	
	/** ... */
	private static Map<String, Clip> audio = new HashMap<String, Clip>();
	
	/** ... */
	private static Map<String, BufferedImage> images =
		new HashMap<String, BufferedImage>();
	
	/**
	 * Load an audio resource.
	 *
	 * @param file File to read from.
	 * @return     Loaded audio clip.
	 */
	public static Clip loadAudio(final String name) {
		// Skip loading already loaded audio //
		Clip clip = audio.get(name);
		if (clip != null) {
			/**
			* Return only when .wav is inactive, 
			* else do new File() [clone] in order to avoid "buzzy" sound
			* Buzz sound cause: rewinding [setFramePosition(0)] when 
			* current .wav file is still active
			*/
			if(!audio.get(name).isActive()) return clip;
		}
		
		// Read audio from file //
		File audioFile = new File(DIR_RESOURCES, name).getAbsoluteFile();
		AudioInputStream audioStream = null;
		try {
			audioStream = AudioSystem.getAudioInputStream(audioFile);
		} catch (IOException exception) {
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
		} catch (UnsupportedAudioFileException exception) {
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
		} catch (IOException exception) {
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
		} catch (LineUnavailableException exception) {
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
	 * Load an image resource.
	 *
	 * @param file File to read from.
	 * @return     Loaded image buffer.
	 */
	public static BufferedImage loadImage(final String name) {
		File file = new File(DIR_RESOURCES, name);
		
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			System.err.printf(
				"Failed to read \"%s\":%s",
				file,
				System.lineSeparator()
			);
			System.err.printf(
				"\t%s%s",
				e,
				System.lineSeparator()
			);
		}
		return image;
	}
	
	public static void unloadAudio(final String name) {
		// TODO: Not implemented.
		throw new IllegalStateException();
	}
	
	public static void unloadImage(final String name) {
		// TODO: Not implemented.
		throw new IllegalStateException();
	}
}
