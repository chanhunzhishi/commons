package cn.chanhunzhishi.transform.colllectionutils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 简单集合工具
 */
public class FastCollections {

	/**
	 * 将List转换成Map
	 * @param list List
	 * @param func 提取Key的函数
	 * @param <T> 提取出的Key
	 * @param <E> Value的泛型
	 * @return 新的Map
	 */
	public static <T, E> Map<T, E> listToMap(List<E> list, Function<E, T> func) {
		Map<T, E> result = new HashMap<>();
		list.forEach(e -> result.put(func.apply(e), e));
		return result;
	}

}
