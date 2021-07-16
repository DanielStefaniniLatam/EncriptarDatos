package com.giros.service;

public final class HSMUtil {

	public static String lpadMultipleOfEight(String target) {
		String result = "";
		int l = target.length();
		int num = 1;
		while ((8 * num) < l) {
			num++;
		}
		int numpad = 2 * ((8 * num) - 8);
		if (numpad == 0)
			numpad = 8;
		for (int i = 1; i <= (numpad - l); i++) {
			result += "0";
		}
		result += target;
		return result;
	}
	
	public static String rpadMultipleOfEight(String target) {
		String result = "";
		int l = target.length();
		int num = 1;
		while ((8 * num) < l) {
			num++;
		}
		int numpad = 2 * ((8 * num) - 8);
		if (numpad == 0)
			numpad = 8;
		for (int i = 1; i <= (numpad - l); i++) {
			result += "0";
		}
		result = target + result;
		return result;
	}

}
