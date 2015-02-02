package com.brennytizer.jumg.sound;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
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
		Logging.log(LoggingSpice.MILD, "Attempting to play song: " + file.substring(file.indexOf("/")));
		try {
			InputStream inStream = null;
			if(inJar) {
				inStream = new BufferedInputStream(SoundPlayer.class.getResourceAsStream(file));
			} else {
				inStream = new BufferedInputStream(new FileInputStream(new File(file)));
			}
			if(midiSequencer != null && !isSequencerPlaying() && midiSequencer.isOpen()) {
				midiSequencer.setSequence(inStream);
				midiSequencer.start();
				Logging.log(LoggingSpice.MILD, "Success! Song is playing.");
			}
			if(inStream != null) inStream.close();
		} catch(IOException | InvalidMidiDataException e) {
			e.printStackTrace();
			Logging.log(LoggingSpice.HOT, "Oh oh! The song didn't play!.");
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
		Logging.log(LoggingSpice.MILD, "Stopping midi song.");
		midiSequencer.stop();
	}
	
	/**
	 * Sets the amount of times that the current and next songs should play.
	 * 
	 * @param loops
	 *        - The amount of times to loop.
	 */
	public static void setLoopCount(int loops) {
		Logging.log(LoggingSpice.MILD, "Setting loop count to: " + loops);
		midiSequencer.setLoopCount(loops);
	}
	
	/**
	 * Acquires the sequencer being held by the current {@link MidiSystem}.
	 * 
	 * @return True if the sequencer was acquired, false if there was an error.
	 */
	public static boolean acquireSequencer() {
		Logging.log(LoggingSpice.MILD, "Attempting to acquire Midi Sequencer...");
		try {
			SoundPlayer.midiSequencer = MidiSystem.getSequencer();
			SoundPlayer.midiSequencer.open();
		} catch(MidiUnavailableException e) {
			e.printStackTrace();
			Logging.log(LoggingSpice.HOT, "Oh oh! Couldn't acquire the sequencer.");
			return false;
		}
		Logging.log(LoggingSpice.MILD, "Success! Sequencer acquired.");
		return true;
	}
	
	static {
		acquireSequencer();
	}
}