package com.brennytizer.jumg.keybinds;

public interface Input {
	public String getName();
	public int getCode();
	public void press(boolean state);
	public boolean isPressed();
	public boolean doesToggle();
}