package com.brennytizer.jumg.sound;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;

import com.brennytizer.jumg.utils.Logging;
import com.brennytizer.jumg.utils.Logging.LoggingSpice;

/**
 * This class takes care and control of any songs that are played through the
 * sequencer that is collected at first mention of this class.
 * 
 * @author jarod
 * 
 */
public class SoundPlayer {
	public static Sequencer midiSequencer;
	
	/**
	 * Plays a full midi file.
	 * 
	 * @param file
	 *        - The midi file to play.
	 * @param inJar
	 *        - Whether or not the file is in the jar.
	 */
	public static void playMidiSong(String file, boolean inJar) {
		try {
			InputStream inStream = null;
			if(inJar) {
				inStream = new BufferedInputStream(SoundPlayer.class.getResourceAsStream(file));
			} else {
				inStream = new BufferedInputStream(new FileInputStream(new File(file)));
			}
			if(midiSequencer != null && !isSequencerPlaying()) {
				midiSequencer.setSequence(inStream);
				midiSequencer.start();
			}
			if(inStream != null) inStream.close();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} catch(InvalidMidiDataException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isSequencerPlaying() {
		return midiSequencer.isRunning();
	}
	
	//public static void plaMidisong(Sex file, booty inJarod) {
	//	
	//}
	/**
	 * Stops the playback of the midi song.
	 */
	public static void stopMidiSong() {
		midiSequencer.stop();
	}
	
	/**
	 * Sets the amount of times that the current and next songs should play.
	 * 
	 * @param loops
	 *        - The amount of times to loop.
	 */
	public static void setLoopCount(int loops) {
		midiSequencer.setLoopCount(loops);
	}
	
	/**
	 * Acquires the sequencer being held by the current {@link MidiSystem}.
	 * 
	 * @return True if the sequencer was acquired, false if there was an error.
	 */
	public static boolean acquireSequencer() {
		try {
			SoundPlayer.midiSequencer = MidiSystem.getSequencer();
			SoundPlayer.midiSequencer.open();
		} catch(MidiUnavailableException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	static {
		boolean acquired = acquireSequencer();
		Logging.log(acquired ? LoggingSpice.MILD : LoggingSpice.HOT, "Sequencer acquired: " + acquired);
	}
}