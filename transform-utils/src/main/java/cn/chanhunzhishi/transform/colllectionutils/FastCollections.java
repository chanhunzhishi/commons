package cn.chanhunzhishi.transform.colllectionutils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 简单集合工具
 */
public class FastCollections {

	/**
	 * 将List转换成Map
	 *
	 * @param list List
	 * @param func 提取Key的函数
	 * @param <T>  提取出的Key
	 * @param <E>  Value的泛型
	 * @return 新的Map
	 */
	public static <T, E> Map<T, E> listToMap(List<E> list, Function<E, T> func) {
		Map<T, E> result = new HashMap<>();
		list.forEach(e -> result.put(func.apply(e), e));
		return result;
	}

	/**
	 * 切割集合
	 * @param collection 原有集合
	 * @param maxLength 切割长度
	 */
	public static <T extends Collection> List<T> splitCollection(T collection, int maxLength)  {
		assert collection != null;
		assert maxLength > 0;
		Iterator iterator = collection.iterator();
		List<T> result = new ArrayList<>();
		int count = 0;
		T list = null;
		while (iterator.hasNext()) {
			if (count % maxLength == 0) {
				try {
					list = (T) collection.getClass().newInstance();
				} catch (InstantiationException |IllegalAccessException e) {
					e.printStackTrace();
					throw new CollectionsException("创建失败");
				}
				result.add(list);
				count = 0;
			}
			list.add(iterator.next());
			count++;
		}
		return result;
	}
}
