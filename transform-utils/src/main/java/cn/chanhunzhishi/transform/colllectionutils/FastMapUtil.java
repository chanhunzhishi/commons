package cn.chanhunzhishi.transform.colllectionutils;


import cn.chanhunzhishi.transform.objectutils.FastObjectUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Map和Object的转化工具类，提供flatmap等功能
 */
public class FastMapUtil {
	private static String getWord = "get";

	/**
	 * 根据类的属性对应public set方法，提取map中的同名属性,可以设置不被提取的名字
	 */
	public static <T> T fillObject(Class<T> clazz, Map<String, Object> map)throws Exception{
		return fillObject(clazz, map, (String[]) null);
	}
	public static <T> T fillObject(Class<T> clazz, Map<String, Object> map, String... excepts) throws Exception {
		T t = clazz.newInstance();
		Method[] methods = clazz.getMethods();
		Set<String> expectSet;
		if(excepts==null){
			expectSet = null;
		}else {
			expectSet = new HashSet<>(Arrays.asList(excepts));
		}
		Set<String> fieldNameSet = new HashSet<>();
		for (Field field : clazz.getDeclaredFields()) {
			fieldNameSet.add(field.getName());
		}

		for (Method method : methods) {
			if (!method.getName().contains("set")) {
				continue;
			}
			char[] charArray = method.getName().substring(3).toCharArray();
			charArray[0] = Character.toLowerCase(charArray[0]);
			String fieldName = new String(charArray);
			if (expectSet !=null && !expectSet.contains(fieldName)) {
				continue;
			}
			try {
				Object obj = map.get(fieldName);
				method.invoke(t, obj);
			} catch (Exception e) {

			}
		}
		return t;
	}

	/**
	 * 根据get方法提取类中的属性到map中，不会进行递归
	 * @param obj 原对象
	 * @param includeNull 是否提取为null的属性
	 * @return 提取出的map
	 */
	public static Map<String, Object> getObjFields(Object obj,boolean includeNull)  {
		Map<String, Object> map = new HashMap<>();
		Class<?> class1 = obj.getClass();
		Method[] methods = class1.getMethods();
		for (Method method : methods) {
			if (!method.getName().contains(getWord)) {
				continue;
			}
			char[] charArray = method.getName().substring(3).toCharArray();
			Object invoke = null;
			try {
				invoke = method.invoke(obj);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}

			charArray[0] = Character.toLowerCase(charArray[0]);
			String fieldName = new String(charArray);
			try {
				class1.getDeclaredField(fieldName);
			} catch (Exception e) {
				continue;
			}
			if (includeNull || invoke != null) {
				map.put(fieldName, invoke);
			}
		}
		return map;
	}


	/**
	 * 返回一个扁平化的Map，将只存在一层键值对，同名的键值对将被直接覆盖，只封装含有同名get方法的值
	 *
	 *
	 */
	//TODO 感觉这个没卵用，没想好怎么写
	@SuppressWarnings("unchecked")
	private static Map<Object, Object> makeFlatMap(Object obj) throws Exception {
		if (Map.class.isAssignableFrom(obj.getClass())) {
			return makeFlatMapWithMap((Map<Object, Object>) obj, new HashMap<>());
		} else {
			return makeFlatMapWithObj(obj, new HashMap<>());
		}
	}

	/**
	 * 该方法将对象中的属性封装到新的map中，如果值中含有对象或者map，则对其进行递归操作
	 * 但是只能封装带有getSet方法的对象，因为使用的是其反射
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	private static Map<Object, Object> makeFlatMapWithObj(Object obj, Map<Object, Object> newMap) throws Exception {
		Class<?> class1 = obj.getClass();
		Method[] methods = class1.getMethods();
		for (Method method : methods) {
			//获取get方法，并根据get方法获取对应属性值
			if (!method.getName().contains(getWord)) {
				continue;
			}
			char[] charArray = method.getName().substring(3).toCharArray();
			Object invoke;
			//可能有名称为getXX却有入参的函数
			try {
				invoke = method.invoke(obj);
			} catch (Exception e) {
				continue;
			}
			if (invoke == null) {
				continue;
			}
			charArray[0] = Character.toLowerCase(charArray[0]);
			String fieldName = new String(charArray);
			//没有对应属性，跳过
			Field field;
			try {
				field = class1.getDeclaredField(fieldName);
			} catch (Exception e) {
				continue;
			}

			//判断该属性的类型，递归调用对应的Map或者Object方法
			if (FastObjectUtil.checkBaseOrPackageType(invoke.getClass())) {
				newMap.put(fieldName, invoke);
			} else {
				if (Map.class.isAssignableFrom(invoke.getClass())) {
					makeFlatMapWithMap((Map<Object, Object>) invoke, newMap);
				} else {
					makeFlatMapWithObj(invoke, newMap);
				}
			}
		}
		return newMap;
	}

	/**
	 * 将多层对象扁平化为单层Map
	 */
	@SuppressWarnings({"unchecked"})
	private static Map<Object, Object> makeFlatMapWithMap(Map<Object, Object> map, Map<Object, Object> newMap) throws Exception {
		for (Entry<Object, Object> next : map.entrySet()) {
			Object value = next.getValue();
			if (FastObjectUtil.checkBaseOrPackageType(value.getClass())) {
				newMap.put(next.getKey(), value);
			} else {
				if (Map.class.isAssignableFrom(value.getClass())) {
					makeFlatMapWithMap((Map<Object, Object>) value, newMap);
				} else {
					makeFlatMapWithObj(value, newMap);
				}
			}
		}
		return newMap;
	}


}
