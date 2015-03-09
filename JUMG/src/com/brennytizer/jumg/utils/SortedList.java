package com.brennytizer.jumg.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.brennytizer.jumg.utils.Logging.LoggingSpice;

public class SortedList<E> extends ArrayList<E> {
	private static final long serialVersionUID = 1L;

	/**
	 * Retrieve the first element from the list.
	 * 
	 * @return The first element from the list,
	 */
	public E peek() {
		return get(0);
	}
	
	/**
	 * Retrieves the first element from the list and removes it from the list.
	 * 
	 * @return The first element from the list.
	 */
	public E pop() {
		E e = peek();
		remove(0);
		return e;
	}
	
	/**
	 * Empties the list.
	 */
	public void clear() {
		clear();
	}
	
	/**
	 * Pushes the element to the top (very first index) of the list. This is the
	 * same as calling `add(e, 0);`.
	 * 
	 * @param e
	 *        - The element to push.
	 */
	public void push(E e) {
		add(0, e);
	}
	
	public void sort(Comparator<E> c) {
		try {
			Collections.sort(this, c);
		} catch(Exception e) {
			e.printStackTrace();
			Logging.log(LoggingSpice.HOT, "Oh no! Something when wrong when trying to sort the elements of this sorted list: " + this);
		}
	}
}