package com.brennytizer.jumg.gui.event;

import java.awt.Point;

import com.brennytizer.jumg.utils.geom.Point2D;

/**
 * <p>
 * A mouse event is an event that stores information about a mouse action when
 * it is triggered. This class aims to fulfill this in the easiest possible way.
 * </p>
 * 
 * <p>
 * If any mouse button is pressed, a new MouseEvent will be created. This also
 * includes any mouse movement actions. If the mouse moves, a new MouseEvent
 * will be created, but this does not mean that the previous mouse position was
 * recorded.
 * </p>
 * 
 * @author Jarod Brennfleck
 */
public class MouseEvent {
	public static final byte MIDDLE_MOUSE_UP = -3;
	public static final byte RIGHT_MOUSE_UP = -2;
	public static final byte LEFT_MOUSE_UP = -1;
	public static final byte NO_MOUSE_BUTTON = 0;
	public static final byte LEFT_MOUSE_DOWN = 1;
	public static final byte RIGHT_MOUSE_DOWN = 2;
	public static final byte MIDDLE_MOUSE_DOWN = 3;
	public Point2D location;
	public byte mouseButton;
	public short scrollAmount;
	
	/**
	 * Constructs a new MouseEvent using a location and mouse button and scroll
	 * amount.
	 * 
	 * @param point
	 *        - The location of the mouse when the event occured.
	 * @param mouseButton
	 *        - The button pressed or 0 if no button pressed.
	 * @param scrollAmount
	 *        - The amount scrolled.
	 */
	public MouseEvent(Point point, byte mouseButton, short scrollAmount) {
		this.location = new Point2D(point.x, point.y);
		this.mouseButton = mouseButton;
		this.scrollAmount = scrollAmount;
	}
	
	/**
	 * Returns the location of the mouse at the time of the event.
	 * 
	 * @return {@link Point2D} - The location of the mouse.
	 */
	public Point2D getLocation() {
		return location;
	}
	
	/**
	 * Returns the mouse button pressed or -1 if there was no button pressed.
	 * 
	 * @return {@link Byte} - The mouse button.
	 */
	public byte getMouseButton() {
		return mouseButton;
	}
	
	/**
	 * Returns the amount that had just been scrolled using the scroll wheel.
	 * 
	 * @return {@link Short} - The scroll amount.
	 */
	public short getScrollAmount() {
		return scrollAmount;
	}
}