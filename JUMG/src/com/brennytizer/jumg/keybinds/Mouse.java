package com.brennytizer.jumg.keybinds;

public class Mouse implements Input {
	public String name;
	public int keyCode;
	public boolean doesToggle;
	public boolean isPressed;
	public boolean isBeingPressed;
	
	public Mouse(String name, int keyCode, boolean doesToggle) {
		this.name = name;
		this.keyCode = keyCode;
		this.doesToggle = doesToggle;
		this.isPressed = false;
		this.isBeingPressed = false;
	}
	
	public void press(boolean state) {
		if(doesToggle) {
			if(!isBeingPressed) {
				isPressed = !isPressed;
			}
		} else {
			isPressed = state;
		}
		isBeingPressed = state;
	}
	
	public boolean isPressed() {
		return this.isPressed;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getCode() {
		return this.keyCode;
	}
	
	public boolean doesToggle() {
		return this.doesToggle;
	}
}
