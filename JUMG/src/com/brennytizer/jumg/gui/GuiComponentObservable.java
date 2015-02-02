package com.brennytizer.jumg.gui;

import java.util.ArrayList;

import com.brennytizer.jumg.utils.Listener;
import com.brennytizer.jumg.utils.Logging;
import com.brennytizer.jumg.utils.Logging.LoggingSpice;

public class GuiComponentObservable extends Listener {
	public static GuiComponentObservable OBSERVABLE;
	public ArrayList<GuiComponentListener> listeners;
	public byte buttonDown;
	
	public GuiComponentObservable() {
		if(OBSERVABLE == null) {
			OBSERVABLE = this;
			Logging.log(LoggingSpice.MILD, "Making new component observable!");
		}
		listeners = new ArrayList<GuiComponentListener>();
	}
	
	public void mouseMove(java.awt.event.MouseEvent e, boolean pressed) {
		byte mouseButton = (byte) (pressed ? e.getButton() : -e.getButton());
		MouseEvent me = new MouseEvent(e.getPoint(), mouseButton, (short) 0);
		synchronized(listeners) {
			for(GuiComponentListener l : listeners) {
				l.onMouseMove(me);
			}
		}
	}
	public void mouseButton(java.awt.event.MouseEvent e, boolean pressed) {
		byte mouseButton = (byte) (pressed ? e.getButton() : -e.getButton());
		if(mouseButton == 0) {
			mouseButton = (byte) (pressed ? buttonDown : -buttonDown);
		}
		
		MouseEvent me = new MouseEvent(e.getPoint(), mouseButton, (short) 0);
		synchronized(listeners) {
			for(GuiComponentListener l : listeners) {
				l.onMouseButton(me);
			}
		}
	}
	public void mouseScroll(java.awt.event.MouseWheelEvent e) {
		MouseEvent me = new MouseEvent(e.getPoint(), (byte) 0, (short) e.getScrollAmount());
		synchronized(listeners) {
			for(GuiComponentListener l : listeners) {
				l.onMouseScroll(me);
			}
		}
	}
	public void keyPress(java.awt.event.KeyEvent e, boolean pressed) {
		KeyEvent ke = new KeyEvent(e.getKeyChar(), KeyEvent.getByteModifiers(e), pressed);
		synchronized(listeners) {
			for(GuiComponentListener l : listeners) {
				l.onKeyPress(ke);
			}
		}
	}
	
	public void mouseMoved(java.awt.event.MouseEvent e) {
		mouseMove(e, false);
	}
	public void mousePressed(java.awt.event.MouseEvent e) {
		mouseButton(e, true);
		buttonDown = (byte) e.getButton();
	}
	public void mouseReleased(java.awt.event.MouseEvent e) {
		mouseButton(e, false);
		buttonDown = 0;
	}
	public void mouseDragged(java.awt.event.MouseEvent e) {
		mouseMove(e, true);
		mouseButton(e, true);
	}
	public void mouseWheelMoved(java.awt.event.MouseWheelEvent e) {
		mouseScroll(e);
	}
	public void keyPressed(java.awt.event.KeyEvent e) {
		keyPress(e, true);
	}
	public void keyReleased(java.awt.event.KeyEvent e) {
		keyPress(e, false);
	}
	
	public static void addListener(GuiComponentListener listener) {
		if(OBSERVABLE != null && OBSERVABLE.listeners != null) {
			Logging.log(LoggingSpice.MILD, "Adding a listener: " + listener.getClass().getName());
			OBSERVABLE.listeners.add(listener);
		}
	}
	
	public static GuiComponentListener[] getListeners() {
		if(OBSERVABLE != null && OBSERVABLE.listeners != null) {
			return (GuiComponentListener[]) OBSERVABLE.listeners.toArray();
		}
		return null;
	}
	
	public static void reset() {
		Logging.log(LoggingSpice.MILD, "Resetting observable...");
		OBSERVABLE = null;
		new GuiComponentObservable();
	}
}