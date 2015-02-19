package com.brennytizer.jumg.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	public static String swapParts(String word, String oldPart, String newPart) {
		return word.replace(oldPart, newPart);
	}
	
	public static String cutAt(String word, int whereToCut) {
		return word.substring(0, whereToCut <= 0 ? 0 : whereToCut > word.length() ? word.length() : whereToCut);
	}
	
	public static String cutFrom(String word, int whereToCut) {
		return word.substring(whereToCut <= 0 ? 0 : whereToCut >= word.length() ? word.length() : whereToCut);
	}

	public static String capitaliseFirstLetter(String word) {
		word = word.toLowerCase();
		return word.substring(0, 1).toUpperCase() + word.substring(1);
	}

	public static String multiplty(String word, int amount) {
		String newWord = "";
		for(int i = 0; i < amount; i++) newWord += word;
		return newWord;
	}
	
	public static int indexOf(String word, String what) {
		return indexOf(word, what, 0);
	}
	public static int indexOf(String word, String what, int index) {
		int counter = 0;
		if(index >= 0) {
			for(int i = 0; i < word.length() - what.length(); i++) {
				if(word.startsWith(what, i)) counter++;
				if(counter > index) return i;
			}
		} else {
			index = Math.absolute(index);
			for(int i = word.length() - what.length(); i > 0; i--) {
				if(word.startsWith(what, i)) counter++;
				if(counter >= index) return i;
			}
		}
		return -1;
	}
	
	public static int find(String word, String regex) {
		return find(word, regex, 0);
	}
	
	public static int find(String word, String regex, int skips) {
		Matcher m = Pattern.compile(regex).matcher(word);
		return m.find() ? m.start(skips) : -1;
	}
	
	public static String findWord(String word, String regex) {
		return findWord(word, regex, 0);
	}
	
	public static String findWord(String word, String regex, int skips) {
		Matcher m = Pattern.compile(regex).matcher(word);
		return m.find() ? word.substring(m.start(skips), m.end(skips)) : "";
	}
}