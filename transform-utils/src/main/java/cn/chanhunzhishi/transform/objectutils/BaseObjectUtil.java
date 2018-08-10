package cn.chanhunzhishi.transform.objectutils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BaseObjectUtil {

	/**
	 * 获取属性对应的Getter方法
	 * @param field Class的目标属性
	 * @return 对应的getter方法
	 */
	public String getGetterMethodName(Field field) {
		Class<?> declaringClass = field.getDeclaringClass();
		String name = field.getName();
		String returnName;
		if (isBoolean(declaringClass)) {
			if (name.startsWith("is")) {
				returnName = "is" + getUpperFirstName(name);
			} else {
				returnName = name;
			}
		} else {
			returnName = "get" + getUpperFirstName(name);
		}
		return returnName;
	}

	/**
	 * 根据getter方法，获取属性名称
	 * 对于boolean属性，返回的属性名是没有is开头的。
	 * @param getterMethod getter方法
	 * @return 可能的属性名
	 */
	public static String getFieldNameByGetter(Method getterMethod){
		String returnName;
		String name = getterMethod.getName();
		boolean aBoolean = isBoolean(getterMethod.getReturnType());
		if(aBoolean && name.startsWith("is") && name.length() > 2){
			returnName = getLowerFristName(name.substring(0,2));
		}else if(!aBoolean && name.startsWith("get") && name.length() > 3){
			returnName = getLowerFristName(name.substring(0,3));
		}else {
			throw new RuntimeException();
		}
		return returnName;
	}




	private static boolean isBoolean(Class clazz){
		return clazz == boolean.class || Boolean.class.equals(clazz);
	}

	/**
	 * 获取首字母大写的名称
	 *
	 * @param attrName 原名称
	 * @return 首字母大写的名称
	 */
	public static String getUpperFirstName(String attrName) {
		char[] chars = attrName.toCharArray();
		chars[0] = Character.toUpperCase(chars[0]);
		return new String(chars);
	}

	/**
	 * 获取首字母小写的名称
	 * 如果孤立的首字母大写，则将首字母转为小写，
	 * 如果首字母大写且第二个字母也大写，则无视
	 * @param attrName 原名称
	 * @return 首字母小写的名称
	 */
	public static String getLowerFristName(String attrName) {
		char[] chars = attrName.toCharArray();
		if (chars.length > 1 && Character.toUpperCase(chars[1]) == chars[1]) {
			return attrName;
		}else {
			chars[0] = Character.toLowerCase(chars[0]);
			return new String(chars);
		}
	}


	public static boolean checkBaseOrPackageType(Class clazz) {
		return checkBaseType(clazz) || checkPackageObject(clazz);
	}

	/**
	 * 判断对象是否为8种基本类型
	 */
	public static boolean checkBaseType(Class clazz) {
		if (int.class == clazz) {
			return true;
		}
		if (long.class == clazz) {
			return true;
		}
		if (char.class == clazz) {
			return true;
		}
		if (byte.class == clazz) {
			return true;
		}
		if (short.class == clazz) {
			return true;
		}
		if (float.class == clazz) {
			return true;
		}
		if (double.class == clazz) {
			return true;
		}
		if (boolean.class == clazz) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否为基础的包装类
	 *
	 * @param clazz
	 * @return
	 */
	public static boolean checkPackageObject(Class clazz) {
		Class clazz1 = clazz;
		if (Integer.class == clazz || Integer.class.equals(clazz)) {
			return true;
		}
		if (Long.class == clazz || Long.class.equals(clazz)) {
			return true;
		}
		if (Character.class == clazz || Character.class.equals(clazz)) {
			return true;
		}
		if (Byte.class == clazz || Byte.class.equals(clazz)) {
			return true;
		}
		if (Short.class == clazz || Short.class.equals(clazz)) {
			return true;
		}
		if (Float.class.equals(clazz)) {
			return true;
		}
		if (Double.class.equals(clazz)) {
			return true;
		}
		if (String.class.equals(clazz)) {
			return true;
		}
		if (Boolean.class.equals(clazz)) {
			return true;
		}
		return false;
	}
}
