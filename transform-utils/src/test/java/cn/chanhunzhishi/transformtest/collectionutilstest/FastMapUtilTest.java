package cn.chanhunzhishi.transformtest.collectionutilstest;

import cn.chanhunzhishi.transform.colllectionutils.FastMapUtil;
import cn.chanhunzhishi.transform.objectutils.FastObjectUtil;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FastMapUtilTest {

	@Test
	public void testFillObject(){
		Map<String,Object> map = new HashMap<>();
		TestObject map_obj = new TestObject();
		map_obj.setStr("object_str");
		map.put("str","str_value");
		map.put("object", map_obj);
		map.put("map",Collections.singletonMap("map_key1","map_key2"));
		try {
			TestObject testObject = FastObjectUtil.fillObject(TestObject.class, map);
			System.out.println(testObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	public static class TestObject{
		private String str;
		private Object object;
		private Map map;

		public TestObject() {
		}

		public String getStr() {
			return str;
		}

		public void setStr(String str) {
			this.str = str;
		}

		public Object getObject() {
			return object;
		}

		public void setObject(Object object) {
			this.object = object;
		}

		public Map getMap() {
			return map;
		}

		public void setMap(Map map) {
			this.map = map;
		}

		@Override
		public String toString() {
			return "TestObject{" +
					"str='" + str + '\'' +
					", object=" + object +
					", map=" + map +
					'}';
		}
	}
}
