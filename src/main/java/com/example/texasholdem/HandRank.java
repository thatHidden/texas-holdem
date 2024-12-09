package com.example.texasholdem;

import java.util.List;

public class HandRank implements Comparable<HandRank> {
	int rankType;
	List<Integer> values;

	HandRank(int rankType, List<Integer> values) {
		this.rankType = rankType;
		this.values = values;
	}

	@Override
	public int compareTo(HandRank o) {
		if (this.rankType != o.rankType) {
			return Integer.compare(o.rankType, this.rankType);
		}
		for (int i = 0; i < Math.min(this.values.size(), o.values.size()); i++) {
			int cmp = Integer.compare(this.values.get(i), o.values.get(i));
			if (cmp != 0) {
				return cmp > 0 ? -1 : 1;
			}
		}
		return 0;
	}
}