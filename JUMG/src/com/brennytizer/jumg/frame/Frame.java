package com.brennytizer.jumg.frame;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import com.brennytizer.jumg.utils.Listener;
import com.brennytizer.jumg.utils.Logging;
import com.brennytizer.jumg.utils.Logging.LoggingSpice;
import com.brennytizer.jumg.utils.engine.Engine;

/**
 * A Frame class that helps create a JFrame for use when making a game. All
 * methods in here return itself as an object to make for easy method stacking
 * and initialization of a JFrame. All variables have been made public to enable
 * for the fact that others may want to alter them at any time during runtime.
 * 
 * @author Jarod Brennfleck
 */
public class Frame {
	public String title;
	public Dimension size;
	public JFrame frame;
	public Display display;
	
	/**
	 * Initializes a JFrame using a Title to display, and the Size to use.
	 * 
	 * @param title
	 *        - The title of the JFrame.
	 * @param size
	 *        - The size of the JFrame.
	 */
	public Frame(String title, Dimension size) {
		Logging.log(LoggingSpice.MILD, "Creating frame.");
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
		frame.setLayout(new BorderLayout());
		frame.setResizable(false);
		frame.addWindowListener(new WinListen());
		Logging.log(LoggingSpice.MILD, "Frame created.");
	}
	
	/**
	 * Adds a {@link Listener} to the frame.
	 * 
	 * @param l
	 *        - The {@link Listener} to use.
	 * @return Itself - The {@link Frame} instantiated.
	 */
	public Frame addListener(Listener l) {
		Logging.log(LoggingSpice.MILD, "Adding listener: " + l.getClass().getName());
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
		Logging.log(LoggingSpice.MILD, "Adding display: " + d.getClass().getName());
		display = d;
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
		Logging.log(LoggingSpice.MILD, flag ? "Showing frame." : "Hiding frame.");
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
		Logging.log(LoggingSpice.MILD, "Setting cursor to: " + name);
		Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(cursor, hotspot, name);
		frame.setCursor(c);
		return this;
	}
	
	/**
	 * Sets the cursor to be used in the frame.
	 * 
	 * @param cursor
	 *        - The cursor object to use.
	 * @return Itself.
	 */
	public Frame setCursor(Cursor cursor) {
		Logging.log(LoggingSpice.MILD, "Setting cursor to: " + cursor.getName());
		frame.setCursor(cursor);
		return this;
	}
	
	/**
	 * Sets the title of the frame.
	 * 
	 * @param title
	 *        - The new title.
	 */
	public void setTitle(String title) {
		this.frame.setTitle(this.title = title);
	}
	
	//@formatter:off
	public class WinListen implements WindowListener {
		public void windowOpened(WindowEvent e) {}
		public void windowClosing(WindowEvent e) {
			if(Engine.INSTANCE != null) Engine.INSTANCE.stop();
		}
		public void windowClosed(WindowEvent e) {}
		public void windowIconified(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowActivated(WindowEvent e) {}
		public void windowDeactivated(WindowEvent e) {}
	}
	//@formatter:on
}