package cn.chanhunzhishi.transform.stringutils;

public class StringUtil {
	public static String toString(Object o, String s) {
		return o == null ? s : o.toString();
	}
}