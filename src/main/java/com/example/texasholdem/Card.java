package com.example.texasholdem;

import static com.example.texasholdem.CardMapping.RANK_MAP;
import static com.example.texasholdem.CardMapping.SUIT_MAP;
import static com.example.texasholdem.CardMapping.getKeyByValue;

public class Card {
	int rank;
	int suit;

	Card(String cardStr) {
		if (cardStr.length() != 2) {
			throw new IllegalArgumentException("Некорректная карта: " + cardStr);
		}
		char r = cardStr.charAt(0);
		char s = cardStr.charAt(1);
		Integer rankVal = RANK_MAP.get(r);
		Integer suitVal = SUIT_MAP.get(s);
		if (rankVal == null || suitVal == null) {
			throw new IllegalArgumentException("Некорректная card: " + cardStr);
		}
		this.rank = rankVal;
		this.suit = suitVal;
	}

	@Override
	public String toString() {
		return "" + getKeyByValue(RANK_MAP, rank) + getKeyByValue(SUIT_MAP, suit);
	}
}