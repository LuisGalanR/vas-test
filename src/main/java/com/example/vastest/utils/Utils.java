package com.example.vastest.utils;

import org.springframework.stereotype.Component;

@Component
public class Utils {

	public static boolean isNumeric(String s) {
		try {
			if(!s.isEmpty()) {
				Double.parseDouble(s);
			}
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
