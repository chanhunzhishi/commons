package cn.chanhunzhishi.transform.objectutils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FastObjectUtil {
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

	private static boolean checkNeedFileObject(Class clazz) {
		return !(BaseObjectUtil.checkBaseOrPackageType(clazz) && clazz.equals(Date.class) && clazz.equals(Enum.class));
	}
}
