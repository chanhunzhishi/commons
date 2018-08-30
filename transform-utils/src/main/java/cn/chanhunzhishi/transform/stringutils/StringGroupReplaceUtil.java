package cn.chanhunzhishi.transform.stringutils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 将字符串内，多个字符替换为指定内容
 */
public class StringGroupReplaceUtil {
	private String prefixes;
	private String suffix;
	private Map<String, ReplacePackage> replaceObjects = new HashMap<>();

	/**
	 *
	 * @param prefixes 替换字符前缀
	 * @param suffix 替换字符后缀
	 */
	public StringGroupReplaceUtil(String prefixes, String suffix) {
		assert prefixes != null && prefixes.length() > 0;
		assert suffix != null && suffix.length() > 0;
		this.prefixes = prefixes;
		this.suffix = suffix;
	}

	/**
	 * 增加替换字符
	 */
	public void addReplaceStr(String originWord, String finalWord) {
		replaceObjects.put(originWord, new ReplacePackage(finalWord, null));
	}

	/**
	 * 增加替换函数
	 *
	 * @param originWord 原始文字
	 * @param function   替换函数
	 * @param <T>        入参泛型
	 */
	public <T> void addReplaceFunc(String originWord, Function<T, String> function) {
		replaceObjects.put(originWord, new ReplacePackage(null, function));
	}

	public <T> String replace(String originStr, T t) {
		if (originStr == null || originStr.length() <= suffix.length() + prefixes.length() + 1) {
			return originStr;
		}


		//查找出替换的位置
		Pattern p = Pattern.compile(prefixes);
		Matcher m = p.matcher(originStr);
		Pattern p2 = Pattern.compile(suffix);
		Matcher m2 = p2.matcher(originStr);
		LinkedList<String> ori = new LinkedList<>();
		LinkedList<String> str = new LinkedList<>();
		int lastPoint = 0;
		while (m.find() && m2.find()) {
			ori.add(originStr.substring(lastPoint, m.toMatchResult().start()));
			str.add(originStr.substring(m.toMatchResult().end(), m2.toMatchResult().start()));
			lastPoint = m2.toMatchResult().end();
		}
		ori.add(originStr.substring(lastPoint, originStr.length()));

		LinkedList<ReplacePackage> linkedList = new LinkedList<>();
		str.forEach(s -> {
			ReplacePackage pack = replaceObjects.get(s);
			if (pack != null) {
				linkedList.add(pack);
			}
		});
		str = new LinkedList<>();
		for (ReplacePackage next : linkedList) {
			String apply = StringUtil.toString(next.getReplaceObj() != null ? next.getReplaceObj() : next.getFunction().apply(t), null);
			str.add(apply);
		}
		Iterator<String> iterator = ori.iterator();
		Iterator<String> iterator1 = str.iterator();
		StringBuilder sb = new StringBuilder();
		boolean b1 = iterator.hasNext();
		boolean b = iterator1.hasNext();
		while (b1 || b) {
			if (b1) {
				sb.append(iterator.next());
			}
			if (b) {
				sb.append(iterator1.next());
			}
			b1 = iterator.hasNext();
			b = iterator1.hasNext();
		}
		return sb.toString();
	}

	private class ReplacePackage<T> {
		private T replaceObj;
		private Function<T, String> function;

		public T getReplaceObj() {
			return replaceObj;
		}

		public Function<T, String> getFunction() {
			return function;
		}

		public ReplacePackage(T replaceObj, Function<T, String> function) {
			this.replaceObj = replaceObj;
			this.function = function;
		}
	}

}
