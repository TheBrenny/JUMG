package com.brennytizer.jumg.gui;

import java.util.ArrayList;

public class GuiComponentObservable {
	public static GuiComponentObservable OBSERVABLE;
	public ArrayList<GuiComponentListener> listeners;
	
	public GuiComponentObservable() {
		if(OBSERVABLE == null) OBSERVABLE = this;
		listeners = new ArrayList<GuiComponentListener>();
	}
	
	public void mouseMove(java.awt.event.MouseEvent e, boolean pressed) {
		byte mouseButton = (byte) (pressed ? -e.getButton() : e.getButton());
		MouseEvent me = new MouseEvent(e.getPoint(), mouseButton, (short) 0);
		synchronized(listeners) {
			for(GuiComponentListener l : listeners) {
				if(mouseButton == 0) l.onMouseMove(me);
				else l.onMouseButton(me);
			}
		}
	}
	
	public void mouseWheel(java.awt.event.MouseWheelEvent e) {
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
	
	public static void addListener(GuiComponentListener listener) {
		if(OBSERVABLE != null && OBSERVABLE.listeners != null) {
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
		OBSERVABLE = null;
		new GuiComponentObservable();
	}
}