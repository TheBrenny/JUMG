package com.brennytizer.jumg.utils;

public class Words {
	public static String padTo(String word, int amount, String padding) {
		for(int i = 0; i < amount && word.length() < amount; i++) word += padding;
		return word.substring(0, amount);
	}
}