package com.example.texasholdem;

import java.util.HashMap;
import java.util.Map;

public class CardMapping {
	public static final Map<Character, Integer> RANK_MAP = createRankMap();
	public static final Map<Character, Integer> SUIT_MAP = createSuitMap();

	private static Map<Character, Integer> createRankMap() {
		Map<Character, Integer> map = new HashMap<>();
		char[] ranks = {'2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'};
		for (int i = 0; i < ranks.length; i++) {
			map.put(ranks[i], i + 2);
		}
		return map;
	}

	private static Map<Character, Integer> createSuitMap() {
		Map<Character, Integer> map = new HashMap<>();
		map.put('S', 1);
		map.put('H', 2);
		map.put('D', 3);
		map.put('C', 4);
		return map;
	}

	public static Character getKeyByValue(Map<Character, Integer> map, Integer value) {
		for (Map.Entry<Character, Integer> e : map.entrySet()) {
			if (e.getValue().equals(value)) {
				return e.getKey();
			}
		}
		return null;
	}
}
