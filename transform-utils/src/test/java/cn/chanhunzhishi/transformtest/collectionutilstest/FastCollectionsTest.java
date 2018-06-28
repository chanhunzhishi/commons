package cn.chanhunzhishi.transformtest.collectionutilstest;

import cn.chanhunzhishi.transform.colllectionutils.FastCollections;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FastCollectionsTest {
	/**
	 * 将List中的基础类型处理后作为Map的Key
	 */
	@Test
	public void toMapTest1() {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			list.add("Item_" + i);
		}


		Map<String, String> stringStringMap = FastCollections.listToMap(list, item -> item.split("_")[1]);
		stringStringMap.forEach((k, v) -> System.out.println("key:" + k + " value:" + v));
	}

	/**
	 * 将List 中的对象属性提取出来作为Map的Key
	 */

	@Test
	public void toMapTest2() {
		List<TestObject> list = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			list.add(new TestObject("Value1_"+i,"Value2_"+i));
		}


		Map<String, TestObject> stringStringMap = FastCollections.listToMap(list, TestObject::getValue1);
		stringStringMap.forEach((k, v) -> System.out.println("key:" + k + " value:" + v));
	}




	class TestObject{
		private String value1;
		private String value2;
		public TestObject(){}
		public TestObject(String value1, String value2) {
			this.value1 = value1;
			this.value2 = value2;
		}

		public String getValue1() {
			return value1;
		}

		public void setValue1(String value1) {
			this.value1 = value1;
		}

		public String getValue2() {
			return value2;
		}

		public void setValue2(String value2) {
			this.value2 = value2;
		}

		@Override
		public String toString() {
			return "TestObject{" +
					"value1='" + value1 + '\'' +
					", value2='" + value2 + '\'' +
					'}';
		}
	}
}
