package com.brennytizer.jumg.utils;

import com.brennytizer.jumg.utils.Logging.LoggingSpice;

public class Words {
	public static String padTo(String word, int amount, String padding, boolean append) {
		for(int i = 0; i < amount && word.length() < amount; i++)
			if(append) word += padding;
			else word = padding + word;
		return word.substring(0, amount);
	}
	
	public static String swapChars(String word, String oldChars, String newChars) {
		if(oldChars.length() != newChars.length()) {
			Logging.log(LoggingSpice.MEDIUM, "Couldn't swap chars, the length of the swaps were inconsistent. Returning the default word.");
			return word;
		}
		for(int i = 0; i < oldChars.length(); i++)
			word.replace(oldChars.charAt(i), newChars.charAt(i));
		return word;
	}
	
	public static String cutAt(String word, int whereToCut) {
		return word.substring(0, whereToCut > 0 ? whereToCut : 0);
	}

	public static String capitaliseFirstLetter(String word) {
		word = word.toLowerCase();
		return word.substring(0, 1).toUpperCase() + word.substring(1);
	}
}