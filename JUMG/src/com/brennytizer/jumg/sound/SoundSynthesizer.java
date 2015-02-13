package com.brennytizer.jumg.sound;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

import com.brennytizer.jumg.utils.Logging;
import com.brennytizer.jumg.utils.Logging.LoggingSpice;
import com.brennytizer.jumg.utils.Math;

/**
 * This class takes care and control of all channels being used to play sounds
 * through the attached synthesizer which is collected on first mention of this
 * class.
 * 
 * @author Jarod Brennfleck
 * @see Synthesizer
 */
public class SoundSynthesizer {
	public static final byte BITMASK_SOLO = 0b0001;
	public static final byte BITMASK_OMNI = 0b0010;
	public static final byte BITMASK_MUTE = 0b0100;
	public static final byte BITMASK_MONO = 0b1000;
	public static Synthesizer midiSynthesizer;
	
	/**
	 * Plays a sound on a specific channel of the synthesizer for a certain time
	 * period.
	 * 
	 * @param channel
	 *        - The channel of the synthesizer to play on.
	 * @param pitch
	 *        - The pitch of the note to play.
	 * @param ms
	 *        - The time in milliseconds to play for.
	 */
	public static void playSound(int channelIndex, final int pitch, final byte velocity, final long ms) {
		final MidiChannel channel = midiSynthesizer.getChannels()[channelIndex];
		Runnable play = new Runnable() {
			public void run() {
				try {
					channel.noteOn(pitch, (int) velocity);
					Thread.sleep(ms);
					channel.noteOff(pitch);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		new Thread(play, "MIDI_NOTE_" + pitch).start();
	}
	
	/**
	 * Alters the channels characteristics according to the bits in the bitmask
	 * passed. Note that you must change all of the characteristics at once.
	 * 
	 * @param channelIndex
	 *        - The channel to target.
	 * @param bits
	 *        - The characteristics to enable/disable.
	 */
	public static void alterChannel(int channelIndex, byte bits) {
		MidiChannel channel = midiSynthesizer.getChannels()[channelIndex];
		channel.setSolo((bits & 0b0001) == 0b0001);
		channel.setOmni((bits & 0b0010) == 0b0010);
		channel.setMute((bits & 0b0100) == 0b0100);
		channel.setMono((bits & 0b1000) == 0b1000);
	}
	
	/**
	 * Tests if all of the characteristics outlined in the bitmask passed are
	 * active. Note that if only one of the characterstics are wrong, this
	 * method will return false;
	 * 
	 * @param channelIndex
	 *        - The channel to target.
	 * @param bits
	 *        - The characteristics to test.
	 * @return True if the bitmask passed is <b><u>EXACTLY</u><b> the same as
	 *         the bitmask for the channel.
	 */
	public static boolean channelCharacteristics(int channelIndex, byte bits) {
		Logging.log(LoggingSpice.MILD, "Altering Channel (S/O/Mu/Mo): " + Integer.toBinaryString(bits));
		MidiChannel channel = midiSynthesizer.getChannels()[channelIndex];
		byte channelBits = 0;
		if(channel.getSolo()) channelBits += 0b0001;
		if(channel.getOmni()) channelBits += 0b0010;
		if(channel.getMute()) channelBits += 0b0100;
		if(channel.getMono()) channelBits += 0b1000;
		return (channelBits & bits) == bits;
	}
	
	/**
	 * Bends the pitch of the target channel to allow for a wavey sound. This
	 * effect is immediate on the notes being played and all subsequent notes
	 * after. The distance is clamped between -16 and 16 (inclusive) where -16
	 * is maximum bend downwards, and 16 is maximum bend upwards, and 0 is no
	 * bend.
	 * 
	 * @param channelIndex
	 *        - The channel to target.
	 * @param distance
	 *        - The distance to bend.
	 */
	public static void setPitchBend(int channelIndex, float distance) {
		Logging.log(LoggingSpice.MILD, "Bending pitch: " + distance);
		distance = Math.clampFloat(distance, -64F, 64F);
		midiSynthesizer.getChannels()[channelIndex].setPitchBend((int) (distance * 128.0F));
	}
	
	/**
	 * Returns the pitch bend of the target channel.
	 * 
	 * @param channelIndex
	 *        - The channel to target.
	 * @return The bend in the pitch.
	 */
	public static int getPitchBend(int channelIndex) {
		return midiSynthesizer.getChannels()[channelIndex].getPitchBend();
	}
	
	/**
	 * Cuts all power to any notes being played. This method also has the option
	 * to turn off all sound in the target channel.
	 * 
	 * @param channelIndex
	 *        - The channel to target.
	 * @param allSound
	 *        - Whether or not to cut all sound in the channel.
	 */
	public static void cutAllNotes(int channelIndex, boolean allSound) {
		Logging.log(LoggingSpice.MILD, "Cutting all notes" + (allSound ? " to power off" : "") + ".");
		MidiChannel channel = midiSynthesizer.getChannels()[channelIndex];
		channel.allNotesOff();
		if(allSound) channel.allSoundOff();
	}
	
	/**
	 * Acquires the synthesizer being held by the current {@link MidiSystem}.
	 * 
	 * @return True if the synthesizer was acquired, false if there was an
	 *         error.
	 */
	public static boolean acquireSynthesizer() {
		Logging.log(LoggingSpice.MILD, "Attempting to acquire synthesizer...");
		try {
			SoundSynthesizer.midiSynthesizer = MidiSystem.getSynthesizer();
			midiSynthesizer.open();
		} catch(MidiUnavailableException e) {
			e.printStackTrace();
			Logging.log(LoggingSpice.HOT, "Oh oh! Couldn't acquire the synthesizer.");
			return false;
		}
		Logging.log(LoggingSpice.MILD, "Success! Synthesizer acquired.");
		return true;
	}
	
	static {
		boolean acquired = acquireSynthesizer();
		Logging.log(acquired ? LoggingSpice.MILD : LoggingSpice.HOT, "Synthesizer acquired: " + acquired);
	}
}