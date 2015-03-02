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
		for(int i = 0; i < amount; i++)
			newWord += word;
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
	
	public static int count(String word, String search) {
		return count(word, search, false);
	}
	
	public static int count(String word, String search, boolean stack) { // stacking is when '$$' matches $$$$ thrice instead of twice..
		int counter = 0;
		for(int i = 0; i < word.length() - search.length(); i++) {
			if(word.substring(0, search.length()).equals(search)) counter++;
			if(!stack) word = word.substring(search.length());
		}
		return counter;
	}
	
	public static String join(String argSplitter, Iterable<?> iter) {
		String ret = "";
		for(Object o : iter)
			ret += o.toString() + argSplitter;
		return ret.substring(0, ret.lastIndexOf(argSplitter));
	}
	public static String join(String argSplitter, Object... iter) {
		String ret = "";
		for(Object o : iter)
			ret += o.toString() + argSplitter;
		return ret.substring(0, ret.lastIndexOf(argSplitter));
	}
	
	public static String random(int amount, String keySpace) {
		String ret = "";
		for(int i = 0; i < amount; i++)
			ret += keySpace.charAt(Math.random(keySpace.length())) + "";
		return ret;
	}
	
	public enum KeySpace {
		INVALID(""),
		ALPHA_UPPER("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
		ALPHA_LOWER("abcdefghijklmnopqrstuvwxyz"),
		NUMBERS("1234567890"),
		SYMBOLS("`-=[]\\;',./~!@#$%^&*()_+{}|:\"<>?");
		public String keySpace;
		
		KeySpace(String keySpace) {
			this.keySpace = keySpace;
		}
		
		public String getKeySpace() {
			return this.keySpace;
		}
		
		public static KeySpace belongsTo(String s) {
			s = s.charAt(0) + "";
			for(KeySpace ks : KeySpace.values())
				if(ks.getKeySpace().contains(s)) return ks;
			return INVALID;
		}
		
		public static String getKeySpace(KeySpace ... keySpaces) {
			String ret = "";
			for(KeySpace ks : keySpaces)
				ret += ks.getKeySpace();
			return ret;
		}
	}
}