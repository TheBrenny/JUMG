package com.brennytizer.jumg.utils.io.compression;

import java.util.ArrayList;

public class BrennyPress {
	public static String keySpace = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789. ";
	
	public static void changeKeySpace(String keySpace) {
		BrennyPress.keySpace = padTo(keySpace, 64, ".", true);
	}
	
	public static String convert(String data, boolean compress) {
		return compress ? compress(data) : decompress(data);
	}
	public static String compress(String data) {
		String compressed = "";
		String bits = "";
		while (data.length() != 0) {
			byte c = (byte) keySpace.indexOf(data.charAt(0));
			data = data.substring(1);
			bits += padTo(Integer.toBinaryString(c), 6, "0", false);
		}
		byte addOn = (byte) (8 - (bits.length() % 8));
		bits = padTo(bits, bits.length() + addOn, "0", true);
		while(bits.length() != 0) {
			 char c = (char) Integer.parseInt(bits.substring(0, 8), 2);
			 bits = bits.substring(8);
			 compressed += c + "";
		}
		return compressed;
	}
	public static String decompress(String data) {
		String converted = "";
		String bits = "";
		while(data.length() != 0) {
			char c = data.charAt(0);
			data = data.substring(1);
			bits += padTo(Integer.toBinaryString(c), 8 , "0", false);
		}
		int last = bits.length() - (bits.length() % 6);
		bits = padTo(bits, last, "", true);
		while(bits.length() != 0) {
			String sixBits = bits.substring(0, 6);
			bits = bits.substring(6);
			converted += keySpace.charAt(Integer.parseInt(sixBits, 2));
		}
		return converted;
	}
	public static ArrayList<String> convert(ArrayList<String> data, boolean compress) {
		ArrayList<String> newData = new ArrayList<String>();
		for(String line : data) newData.add(convert(line, compress));
		return newData;
	}
	public static String padTo(String word, int amount, String padding, boolean append) {
		for(int i = 0; i < amount && word.length() < amount; i++)
			if(append) word += padding;
			else word = padding + word;
		return word.substring(0, amount);
	}
}