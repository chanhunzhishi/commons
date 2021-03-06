package cn.chanhunzhishi.transform.objectutils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FastObjectUtil {
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
			String getWord = "get";
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
	 * 将结果为null的 String 属性设置为 ""空
	 * 将会获取对象的所有方法和
	 */
	private static <T> T fillNull(T obj) {
		Class<?> aClass = obj.getClass();
		Map<String, Method> fieldMap = new HashMap<>();
		for (Method method : aClass.getDeclaredMethods()) {
			fieldMap.put(method.getName(), method);
		}

		for (Field field : aClass.getDeclaredFields()) {
			try {
				Class type = field.getType();
				char[] chars = field.getName().toCharArray();
				chars[0] = Character.toUpperCase(chars[0]);
				String newName = new String(chars);
				Method getMethod = fieldMap.get("get" + newName);
				if (getMethod == null) {
					continue;
				}
				Method setMethod = fieldMap.get("set" + newName);
				if (setMethod == null) {
					continue;
				}
				if (type.equals(String.class)) {
					Object invoke = getMethod.invoke(obj);
					if (invoke != null) {
						continue;
					}
					setMethod.invoke(obj, "");
				} else if (type.isArray()) {
					Object invoke = getMethod.invoke(obj);
					if (invoke == null) {
						continue;
					}
					Object[] array = (Object[]) invoke;
					if (array.length == 0) {
						continue;
					}
					Class itemClass = type.getComponentType();
					if (itemClass.equals(String.class)) {
						for (int i = 0; (i < array.length); i++) {
							if (array[i] == null) {
								array[i] = "";
							}
						}
					} else if (checkNeedFileObject(itemClass)) {
						for (int i = 0; (i < array.length) && (array[i] != null); i++) {
							array[i] = fillNull(array[i]);
						}
					}
				} else {
					if (!checkNeedFileObject(type)) {
						Object invoke = getMethod.invoke(obj);
						if (invoke == null) {
							continue;
						}
						setMethod.invoke(obj, fillNull(invoke));
					}
				}
			} catch (Exception e) {
			}
		}
		return obj;
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
			String getWord = "get";
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
			if (BaseObjectUtil.checkBaseOrPackageType(invoke.getClass())) {
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
		for (Map.Entry<Object, Object> next : map.entrySet()) {
			Object value = next.getValue();
			if (BaseObjectUtil.checkBaseOrPackageType(value.getClass())) {
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

	private static boolean checkNeedFileObject(Class clazz) {
		return !(BaseObjectUtil.checkBaseOrPackageType(clazz) && clazz.equals(Date.class) && clazz.equals(Enum.class));
	}
}
