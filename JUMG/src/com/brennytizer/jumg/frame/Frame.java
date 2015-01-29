package com.brennytizer.jumg.frame;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.brennytizer.jumg.utils.Listener;

/**
 * A Frame class that helps create a JFrame for use when making a game. All
 * methods in here return itself as an object to make for easy method stacking
 * and initialization of a JFrame. All variables have been made public to enable
 * for the fact that others may want to alter them at any time during runtime.
 * 
 * @author jarod
 */
public class Frame {
	public String title;
	public Dimension size;
	public JFrame frame;
	
	/**
	 * Initializes a JFrame using a Title to display, and the Size to use.
	 * 
	 * @param title
	 *        - The title of the JFrame.
	 * @param size
	 *        - The size of the JFrame.
	 */
	public Frame(String title, Dimension size) {
		this.title = title;
		this.size = size;
		frame = new JFrame(title);
		frame.setPreferredSize(size);
		frame.setMinimumSize(size);
		frame.setMaximumSize(size);
		frame.setSize(size);
		frame.setLocationRelativeTo(null);
		frame.setFocusTraversalKeysEnabled(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Adds a {@link Listener} to the frame.
	 * 
	 * @param l
	 *        - The {@link Listener} to use.
	 * @return Itself - The {@link Frame} instantiated.
	 */
	public Frame addListener(Listener l) {
		frame.addMouseListener(l);
		frame.addMouseMotionListener(l);
		frame.addMouseWheelListener(l);
		frame.addKeyListener(l);
		return this;
	}
	
	/**
	 * Adds a {@link Display} to the frame.
	 * 
	 * @param d
	 *        - The {@link Display} to use.
	 * @return Itself - The {@link Frame} instantiated.
	 */
	public Frame addDisplay(Display d) {
		frame.getContentPane().add(d, BorderLayout.CENTER);
		return this;
	}
	
	/**
	 * Packs and sets the frame visible according to the boolean {@code flag}.
	 * 
	 * @param flag
	 *        - Whether to show or hide the frame.
	 * @return Itself - The {@link Frame} instantiated.
	 */
	public Frame show(boolean flag) {
		frame.pack();
		frame.setVisible(flag);
		return this;
	}
	
	/**
	 * Sets the cursor to be used in the frame.
	 * 
	 * @param cursor
	 *        - The image to use.
	 * @param hotspot
	 *        - The location of the pointer.
	 * @param name
	 *        - The name of the cursor.
	 * @return Itself.
	 */
	public Frame setCursor(Image cursor, Point hotspot, String name) {
		Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(cursor, hotspot, name);
		frame.setCursor(c);
		return this;
	}
}
