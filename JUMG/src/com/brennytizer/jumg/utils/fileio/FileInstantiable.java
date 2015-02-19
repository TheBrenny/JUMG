package com.brennytizer.jumg.utils.fileio;

/**
 * Denotes whether this class can be instantiated from a JUMG file. If the
 * {@link FileInterpreter} tries to instantiate an object it will use this
 * interface's method. This means that if you try to instantiate an object that
 * doesn't inherit this class, you'll get a method not found error.
 * 
 * Also note that any class that implements this interface must have a nullary
 * constructor; a constructor with no parameters.
 * 
 * @author Jarod Brennfleck
 */
public interface FileInstantiable {
	public Object instantiateWithParams(String ... params);
}