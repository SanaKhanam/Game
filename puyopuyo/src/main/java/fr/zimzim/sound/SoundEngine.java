/**
 * Sound events management
 */
package fr.zimzim.sound;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import fr.zimzim.util.Settings;

/**
 * The enum is used to play a sound related to a game specific event
 * @author Simon Jambu
 *
 */
public enum SoundEngine{
	AMBIANCE(Settings.AMBIANCE_URL),
	FLIP(Settings.FLIP_URL),
	KICK(Settings.KICK_URL),
	FINISH(Settings.FINISH_URL),
	PAUSE(Settings.PAUSE_URL);

	// Nested class for specifying volume
	public static enum Volume {
		MUTE, LOW, MEDIUM, HIGH
	}
	
	/**
	 * Sounds volume
	 */
	public static Volume volume = Volume.LOW;
	
	/**
	 * Sound clip
	 */
	private Clip clip;
	
	/**
	 * Constructor
	 * @param sound
	 */
	SoundEngine(String sound){
		try {
			// Use URL (instead of File) to read from disk and JAR.
			URL url = this.getClass().getResource(sound);
			// Set up an audio input stream piped from the sound file.
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
			// Get a clip resource.
			clip = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream.
			clip.open(audioInputStream);
			//	         if(sound.equals(Settings.AMBIANCE_URL)) {
			//	        	 clip.loop(Clip.LOOP_CONTINUOUSLY);
			//	         }
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Play the sound effect
	 */
	public void play() {
		if (volume != Volume.MUTE) {
			if (clip.isRunning()) clip.stop();   // Stop the player if it is still running
			clip.setFramePosition(0); // rewind to the beginning
			clip.start();     // Start playing
		}
	}
	
	/**
	 * Stop current sound and mute volume
	 * @param ambianceUrl 
	 */
	public void mute(String ambianceUrl) {
		if (clip.isRunning()){
			clip.stop();   // Stop the player if it is still running
			volume = Volume.MUTE;
		} else{
			volume = Volume.LOW;
			clip.start();
			if(ambianceUrl.equals(Settings.AMBIANCE_URL))
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}
	
	/**
	 * Called when game is paused
	 */
	public void pause() {
		if (volume != Volume.MUTE) {
			if (clip.isRunning()){
				clip.stop();   // Stop the player if it is still running
			} else{
				clip.start();
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
		}
	}

	/**
	 * Preloads sounds
	 */
	public static void init() {
		values(); // calls the constructor for all the elements
	}
	
	/**
	 * Sets a sound to loop infinitely
	 */
	public void setInfiniteLoop() {
		if (volume != Volume.MUTE) clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
}
