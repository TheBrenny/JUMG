package com.brennytizer.jumg.utils.fileio;

import java.util.ArrayList;

/**
 * The interpreter class which allows for interpreting of read files.
 * 
 * @author Jarod Brennfleck
 */
public interface Interpreter {
	/**
	 * Returns the key space to be used in the reading of lines.
	 */
	public String getKeySpace();
	
	/**
	 * Reads multiple lines all at once.
	 * 
	 * @param lines - The lines you want to read.
	 */
	public void read(ArrayList<String> lines);
}