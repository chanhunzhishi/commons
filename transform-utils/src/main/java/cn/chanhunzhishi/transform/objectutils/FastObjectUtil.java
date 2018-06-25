package cn.chanhunzhishi.transform.objectutils;

public class FastObjectUtil {
	/**
	 * 判断对象是否为8种基本类型或者他们的包装类
	 */
	public static boolean checkBaseType(Class clazz){
		if(int.class == clazz){
			return true;
		}
		if(char.class == clazz){
			return true;
		}
		if(byte.class == clazz){
			return true;
		}
		if(short.class == clazz){
			return true;
		}
		if(float.class == clazz){
			return true;
		}
		if(double.class == clazz){
			return true;
		}
		if(boolean.class == clazz){
			return true;
		}
		return false;
	}
	public boolean checkPackageObject(Class clazz){
		if(Integer.class == clazz || Integer.class.equals(clazz)){
			return true;
		}
		if(Character.class == clazz || Character.class.equals(clazz)){
			return true;
		}
		if(Byte.class == clazz || Byte.class.equals(clazz)){
			return true;
		}
		if(Short.class == clazz || Short.class.equals(clazz)){
			return true;
		}
		if(Float.class.equals(clazz)){
			return true;
		}
		if(Double.class.equals(clazz)){
			return true;
		}
		if(String.class.equals(clazz)){
			return true;
		}
		if(Boolean.class.equals(clazz)){
			return true;
		}
		return false;
	}
}
