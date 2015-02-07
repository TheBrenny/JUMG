package com.brennytizer.jumg.keybinds;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import com.brennytizer.jumg.utils.Listener;

public class KeyBindings extends Listener {
	public static ArrayList<Input> inputs = new ArrayList<Input>();
	
	public static synchronized ArrayList<Input> getInputs() {
		return inputs;
	}
	
	public static void addInput(Input input) {
		getInputs().add(input);
	}
	
	public static void removeInput(Input input) {
		getInputs().remove(input);
	}
	
	public static Input getInput(String name) {
		for(Input i : getInputs()) {
			if(i.getName().equals(name)) return i;
		}
		return null;
	}
	
	public static Input getInput(int keyCode) {
		for(Input i : getInputs()) {
			if(i.getCode() == keyCode) return i;
		}
		return null;
	}
	
	public static ArrayList<Input> getTogglableInputs(boolean doesToggle) {
		ArrayList<Input> inputs = new ArrayList<Input>();
		for(Input i : getInputs()) {
			if(i.doesToggle() == doesToggle) inputs.add(i);
		}
		return inputs;
	}
	
	public static ArrayList<Input> getPressedInputs(boolean isPressed) {
		ArrayList<Input> inputs = new ArrayList<Input>();
		for(Input i : getInputs()) {
			if(i.isPressed() == isPressed) inputs.add(i);
		}
		return inputs;
	}
	
	public static boolean pressInput(int keyCode, boolean state) {
		Input i = getInput(keyCode);
		if(i != null) {
			i.press(state);
			return true;
		} else return false;
	}
	
	public static boolean pressInput(String keyName, boolean state) {
		Input i = getInput(keyName);
		if(i != null) {
			i.press(state);
			return true;
		} else return false;
	}
	
	public void mousePressed(MouseEvent e) {
		pressInput(e.getButton(), true);
	}
	
	public void mouseReleased(MouseEvent e) {
		pressInput(e.getButton(), false);
	}
	
	public void keyPressed(KeyEvent e) {
		pressInput(e.getKeyCode(), true);
	}
	
	public void keyReleased(KeyEvent e) {
		pressInput(e.getKeyCode(), false);
	}
}