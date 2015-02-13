package com.brennytizer.jumg.gui.event;

/**
 * <p>
 * A key event is an event that stores information about a key when it is
 * pressed or released. This class aims to fulfill this in the easiest possible
 * way.
 * </p>
 * <p>
 * Modifiers to the key presses are all bit modifiers, as in, if the left shift
 * and right alt buttons were held at the same time of the key press, the
 * modifier given would be <code>byte mod = 33; // 0010 0001</code>. Adding up
 * all the modifiers you would get <code>byte mod = 127; // 0111 1111</code>.
 * This is the highest number for Java's primitive byte type. Keep that in
 * mind...
 * </p>
 * 
 * <p>
 * One would be questioning right now, do the modifiers send a KeyEvent when
 * pressed? This is correct. If a modifier key is pressed, the character will
 * look very strange but if this key is filtered away from visual placement,
 * then all is good!
 * </p>
 * 
 * @author Jarod Brennfleck
 */
public class KeyEvent {
	public static final byte LEFT_SHIFT = 1;
	public static final byte RIGHT_SHIFT = 2;
	public static final byte LEFT_CONTROL = 4;
	public static final byte RIGHT_CONTROL = 8;
	public static final byte LEFT_ALT = 16;
	public static final byte RIGHT_ALT = 32;
	public static final byte FUNCTION = 64;
	
	public char keyChar;
	public byte modifiers;
	public boolean pressed;
	
	/**
	 * Constructs a KeyEvent by using a character for the letter and a byte for
	 * the modifiers.
	 * 
	 * @param keyChar
	 *        - The character which triggered the event.
	 * @param modifiers
	 *        - Any modifiers created using bits.
	 */
	public KeyEvent(char keyChar, byte modifiers, boolean pressed) {
		this.keyChar = keyChar;
		this.modifiers = modifiers;
		this.pressed = pressed;
	}
	
	/**
	 * Returns if and only if the given modifier(s) are all switched on for this
	 * key event. If a given modifier is not turned on then the false will be
	 * returned.
	 * 
	 * @param modifier
	 *        - The modifiers to test.
	 * @return {@link Boolean} - Whether all passed modifiers are active.
	 */
	public boolean isModifierActive(byte modifier) {
		return (modifiers & modifier) == modifier;
	}
	
	/**
	 * Returns the key character of the pressed key. This will be the visual
	 * representation of the key and should be susceptible to key modifiers.
	 * 
	 * @return {@link Character} - The visual character.
	 */
	public char getKeyChar() {
		return keyChar;
	}
	
	/**
	 * Returns any modifiers as a bit shifted array.
	 * 
	 * @return {@link Byte} - Modifiers as a number.
	 */
	public byte getModifiers() {
		return modifiers;
	}
	
	/**
	 * Returns whether or not this event was triggered by a key press or a key
	 * release.
	 * 
	 * @return {@link Boolean} - Whether key was pressed or released.
	 */
	public boolean isPressed() {
		return pressed;
	}
	
	/**
	 * Returns the bit mask of all the held down modifiers for the AWT KeyEvent
	 * given. Due to the lack of separation of which modifier key was pressed
	 * (between left and right sides) the bit mask will contain both sides even
	 * if only one of the sides was pressed.
	 * 
	 * NOTE: As far as I know, there is no way of capturing the "Fn" key found
	 * on most keyboards. I thought it was
	 * {@link java.awt.event.KeyEvent#isMetaDown()} but then soon found out that
	 * this is Mac's 'Command' button apparently.
	 * 
	 * @param e
	 *        - The AWT Key Event.
	 * @return {@link Byte} - A bit mask of the held down modifiers at the time.
	 */
	public static byte getByteModifiers(java.awt.event.KeyEvent e) {
		byte bytes = 0;
		if(e.isShiftDown()) {
			bytes += LEFT_SHIFT;
			bytes += RIGHT_SHIFT;
		}
		if(e.isAltDown()) {
			bytes += LEFT_ALT;
			bytes += RIGHT_ALT;
		}
		if(e.isControlDown()) {
			bytes += LEFT_CONTROL;
			bytes += RIGHT_CONTROL;
		}
		return bytes;
	}
}