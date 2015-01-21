package xyz.anduo.myretrieval.util;

import java.util.HashMap;
import java.util.Map;

import xyz.anduo.myretrieval.config.Constants;
import junit.framework.TestCase;

public class SortUtilsTest extends TestCase {

	public void testSortByValue() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("a", 12);
		map.put("b", 1);
		map.put("c", 16);
		map.put("d", 22);
		map.put("e", 3);
		@SuppressWarnings("unchecked")
		Map<String, Integer> rs = SortUtils.sortByValue(map, true);
		for (Object key : rs.keySet()) {
			System.out.println(key + Constants.TABLE + rs.get(key));
		}
	}

}
