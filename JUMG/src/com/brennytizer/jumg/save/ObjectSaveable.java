package com.brennytizer.jumg.save;

import com.brennytizer.jumg.utils.fileio.FileInstantiable;

/**
 * An interface that allows for objects to be saved as save files with JUMG
 * syntax, (or any other syntax...)
 * 
 * @author Jarod Brennfleck
 */
public interface ObjectSaveable extends FileInstantiable {
	/**
	 * Returns the objects class and package.
	 */
	public String getObjectClass();
	
	/**
	 * Returns the object's name that is used throughout the save file.
	 */
	public String getObjectName();
	
	/**
	 * Returns the parameters to use when instantiating the object.
	 */
	public String[] getObjectParams();
	
	/**
	 * Returns the object data to set which aren't being set through the
	 * instantiating parameters.
	 */
	public String[][] getObjectData();
}