package cn.chanhunzhishi.transformtest.stringutilstest;

import java.util.Map;

public class TestService {
	public String exec(Map<String,String> map) {
		return map.entrySet().iterator().next().getValue();
	}
}
