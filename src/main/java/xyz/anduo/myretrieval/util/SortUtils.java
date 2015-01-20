package xyz.anduo.myretrieval.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SortUtils {

	/**
	 * 将map根据value值进行排序
	 * 
	 * @param map
	 * @param isDec
	 *            true为降序,false为升序
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map sortByValue(Map map, final boolean isDec) {
		Map result = new LinkedHashMap();
		List list = new LinkedList(map.entrySet());
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				if (isDec) {
					return ((Comparable) (((Map.Entry) o2).getValue())).compareTo((Comparable) (((Map.Entry) o1)
							.getValue()));
				} else {
					return ((Comparable) (((Map.Entry) o1).getValue())).compareTo((Comparable) (((Map.Entry) o2)
							.getValue()));
				}
			};
		});

		for (Object o : list) {
			Map.Entry entry = (Map.Entry) o;
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	/**
	 * 将map根据value值进行降序排序
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map sortByValue(Map map) {
		return sortByValue(map, true);
	}

}
