package com.brennytizer.jumg.frame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * The Listener class works in a very similar way to the MouseAdapter and
 * KeyAdapter classes that Oracle provides. It inherits all the methods from
 * each component listener and allows you to override the methods that you want
 * rather than all of them.
 * <br><br>
 * None of the methods contained will be JavaDoc'd because they all should be self-explanatory.
 * 
 * @author jarod
 */
//@formatter:off
public class Listener implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
	public void mouseDragged(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	public void mouseWheelMoved(MouseWheelEvent e) {}
}
//@formatter:on