package cn.chanhunzhishi.transformtest.stringutilstest;


import cn.chanhunzhishi.transform.stringutils.StringGroupReplaceUtil;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringGroupReplaceUtilTest {
	@Test
	public void baseTest0() {
		StringGroupReplaceUtil replaceUtil = new StringGroupReplaceUtil("\\$\\{", "\\}");
		replaceUtil.addReplaceStr("a", "#########");
		replaceUtil.addReplaceStr("b", "jjjjjjjjjjjjjjj");
		replaceUtil.addReplaceStr("asfasfsaf", "rrrrrrrrrrrrrrrr");
		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < 100000; i++) {
			stringBuilder.append("afsf${a}asffasfaf${asfasfsaf}joigajsifj${b}af");
		}
		long l = System.currentTimeMillis();
		String fffffffffff = stringBuilder.toString().replaceAll("\\$\\{a\\}|\\$\\{b\\}|\\$\\{asfasfsaf\\}", "fffffffffff");

		System.out.println(System.currentTimeMillis() - l);
	}

	@Test
	public void baseTest() {
		StringGroupReplaceUtil replaceUtil = new StringGroupReplaceUtil("\\$\\{", "\\}");
		replaceUtil.addReplaceStr("a", "#########");
		replaceUtil.addReplaceStr("b", "jjjjjjjjjjjjjjj");
		replaceUtil.addReplaceStr("asfasfsaf", "rrrrrrrrrrrrrrrr");
		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < 100000; i++) {
			stringBuilder.append("afsf${a}asffasfaf${asfasfsaf}joigajsifj${b}af");
		}
		long l = System.currentTimeMillis();
		String replace = replaceUtil.replace(stringBuilder.toString(), "ffff");
		System.out.println(System.currentTimeMillis() - l);
	}

	@Test
	public void testObject() {
		Map<String, String> map = new HashMap<>();
		map.put("aaa", "TTTTTTTTTT");
		StringGroupReplaceUtil replaceUtil = new StringGroupReplaceUtil("\\$\\{", "\\}");
		replaceUtil.addReplaceStr("a", "#########");
		replaceUtil.addReplaceStr("b", "jjjjjjjjjjjjjjj");
		replaceUtil.addReplaceFunc("asfasfsaf", o -> ((Map<String, String>) o).get("aaa"));
		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < 1000; i++) {
			stringBuilder.append("afsf${a}asffasfaf${asfasfsaf}joigajsifj${b}af");
		}
		long l = System.currentTimeMillis();
		String replace = replaceUtil.replace(stringBuilder.toString(), map);
		System.out.println(System.currentTimeMillis() - l);
	}


	public TestService testService = new TestService();

	@Test
	public void testService() {
		Map<String, String> map = new HashMap<>();
		map.put("aaa", "TTTTTTTTTT");
		StringGroupReplaceUtil replaceUtil = new StringGroupReplaceUtil("\\$\\{", "\\}");
		replaceUtil.addReplaceFunc("a", testService::exec);
		Map<String, String> map1 = Collections.singletonMap("asfasf", "zzzzzzzzzz");
		System.out.println(replaceUtil.replace("afsf${a}asf", map1));
	}

}
