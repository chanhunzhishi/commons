package cn.chanhunzhishi.transform.colllectionutils;


import cn.chanhunzhishi.transform.objectutils.FastObjectUtil;

import java.lang.reflect.Field;
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
    private static String getWord ="get";
	/**
	 * 根据类的属性对应public set方法，提取map中的同名属性,可以设置不被提取的名字
     *
	 */
	public static <T> T fillObject(T t,Map<String,Object> map,String ...excepts) throws Exception{
		Class<?> clazz = t.getClass();
		Method[] methods = clazz.getMethods();
		Set<String> expectSet = new HashSet<>(Arrays.asList(excepts));
		for(Method method:methods){
			if(!method.getName().contains("set")){
				continue;
			}
			char[] charArray = method.getName().substring(3).toCharArray();
			charArray[0] = Character.toLowerCase(charArray[0]);
			String fieldName = new String(charArray);
			if(!expectSet.contains(fieldName)){
				continue;
			}
			try{
				Object obj = map.get(fieldName);
				method.invoke(t,obj);
			}catch (Exception e) {

			}
		}
		return t;
	}
	
	/**
	 * 根据get方法提取类中的属性到map中，不会进行递归
     *
	 */
	@SuppressWarnings("unused")
	public static Map<String,Object> getObjFields(Object obj) throws Exception{
		Map<String,Object> map = new HashMap<>();
		Class<?> class1 = obj.getClass();
		Method[] methods = class1.getMethods();
		for (Method method : methods) {
			if(!method.getName().contains(getWord)){
				continue;
			}
			char[] charArray = method.getName().substring(3).toCharArray(); 
			Object invoke = method.invoke(obj);
			charArray[0] = Character.toLowerCase(charArray[0]);
			String Fieldname = new String(charArray);
			try{
				Field field = class1.getDeclaredField(Fieldname);
			}catch (Exception e) {
				continue;
			}
			map.put(Fieldname, invoke);
		}
		return map;
	}
	/**
	 * 将对象提取成为map 不包含null的属性
	 */
	public static Map<String,Object> getObjFieldsWithOutNull(Object obj) throws Exception{
		Map<String,Object> map = new HashMap<>();
		Class<?> class1 = obj.getClass();
		Method[] methods = class1.getMethods();
		for (Method method : methods) {
			if(!method.getName().contains(getWord)){
				continue;
			}
			char[] charArray = method.getName().substring(3).toCharArray();
			Object invoke = method.invoke(obj);
			charArray[0] = Character.toLowerCase(charArray[0]);
			String Fieldname = new String(charArray);
			try{
				class1.getDeclaredField(Fieldname);
			}catch (Exception e) {
				continue;
			}
			if(invoke!=null){
				map.put(Fieldname, invoke);
			}
		}
		return map;
	}
	/**
	 * 返回一个扁平化的Map，将只存在一层键值对，同名的键值对将被直接覆盖，只封装含有同名get方法的值
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> makeFlatMap(Object obj) throws Exception{
		if(Map.class.isAssignableFrom(obj.getClass())){
			return makeFlatMapWithMap((Map<String, Object>)obj,new HashMap<String, Object>());
		}else{
			return makeFlatMapWithObj(obj,new HashMap<String, Object>());
		}
	}
	/**
	 * 该方法将对象中的属性封装到新的map中，如果值中含有对象或者map，则对其进行递归操作
	 * 但是只能封装带有getSet方法的对象，因为使用的是其反射
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	private static Map<String,Object> makeFlatMapWithObj(Object obj,Map<String,Object> newMap) throws Exception{
		Class<?> class1 = obj.getClass();
		Field[] declaredFields = class1.getDeclaredFields();
		Method[] methods = class1.getMethods();
		for (Method method : methods) {
			if(!method.getName().contains(getWord)){
				continue;
			}
			char[] charArray = method.getName().substring(3).toCharArray();
			Object invoke = method.invoke(obj);
			charArray[0] = Character.toLowerCase(charArray[0]);
			String Fieldname = new String(charArray);
			try{
				Field field = class1.getDeclaredField(Fieldname);
			}catch (Exception e) {
				continue;
			}
			Class fieldType = class1.getDeclaredField(Fieldname).getType();
			if(FastObjectUtil.checkBaseType(fieldType)){
				newMap.put(Fieldname, invoke);
			}else{
				if(Map.class.isAssignableFrom(fieldType)){
					makeFlatMapWithMap((Map<String,Object>)invoke,newMap);
				}else{
					makeFlatMapWithObj(invoke,newMap);
				}
			}
		}
		return newMap;
	}
	/**
	 * 将多层对象扁平化为单层Map
     *
	 */
	@SuppressWarnings({ "unchecked" })
	private static Map<String,Object> makeFlatMapWithMap(Map<String,Object> map,Map<String,Object> newMap) throws Exception{
        for (Entry<String, Object> next : map.entrySet()) {
            Object value = next.getValue();
            if(FastObjectUtil.checkBaseType(value.getClass())){
                newMap.put(next.getKey(), value);
            }else{
                if(Map.class.isAssignableFrom(value.getClass())){
                    makeFlatMapWithMap((Map<String, Object>) value,newMap);
                }else{
                    makeFlatMapWithObj(value,newMap);
                }
            }
        }
		return newMap;
	}


}
