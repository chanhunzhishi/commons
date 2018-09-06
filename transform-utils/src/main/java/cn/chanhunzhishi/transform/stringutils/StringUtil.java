package cn.chanhunzhishi.transform.stringutils;

import java.util.function.Function;

public class StringUtil {

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static boolean notEmpty(String str) {
		return str != null && str.length() != 0;
	}

	public static boolean isBlank(String str) {
		return str == null || str.trim().length() == 0;
	}

	public static boolean notBlank(String str) {
		return str != null && str.trim().length() != 0;
	}

	public static String toString(Object o, String defaultVal) {
		return o == null ? defaultVal : o.toString();
	}

	public static String toString(Object o) {
		return toString(o, null);
	}

	public static String trimToNull(String input) {
		String string = input.trim();
		return string.length() == 0 ? null : string;
	}

	public static Integer toInteger(String str, Integer defaultVal) {
		return baseFunc(trimToNull(str), Integer::valueOf, defaultVal);
	}

	public static Boolean toBoolean(String str, Boolean defaultVal) {
		return baseFunc(str, Boolean::valueOf, defaultVal);
	}

	public static Long toLong(String str, Long defaultVal) {
		return baseFunc(str, Long::valueOf, defaultVal);
	}

	private static <T> T baseFunc(String input, Function<String, T> function, T defaultVal) {
		input = trimToNull(input);
		if (input == null) {
			return defaultVal;
		}
		try {
			return function.apply(input);
		} catch (Exception e) {
			return defaultVal;
		}
	}
}