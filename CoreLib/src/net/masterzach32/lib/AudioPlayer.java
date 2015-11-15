package net.masterzach32.lib;

import javax.sound.sampled.*;

/**
 * A class for playing audio files in the game. This is not used in
 * 
 * @author Zach Kozar
 */
public class AudioPlayer {

	private Clip clip;

	/**
	 * Creates a new audio clip.
	 * 
	 * @param ais
	 */
	public AudioPlayer(AudioInputStream ais) {
		try {
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
			AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
			clip = AudioSystem.getClip();
			clip.open(dais);
		} catch (Exception e) {
			e.printStackTrace();
			Utilities.createErrorDialog("Audio Error", "An unexpected error occured while creating an AudioPlayer for:\n" + e.toString(), e);
		}
	}

	public void play() {
		if (clip == null) return;
		stop();
		clip.setFramePosition(0);
		clip.start();
	}

	public void stop() {
		if (clip.isRunning()) clip.stop();
	}

	public void close() {
		stop();
		clip.close();
	}
}