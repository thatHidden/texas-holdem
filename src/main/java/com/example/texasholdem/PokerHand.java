package com.example.texasholdem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PokerHand implements Comparable<PokerHand> {
	private final List<Card> cards;
	private final HandRank handRank;

	public PokerHand(String hand) {
		String[] cards = hand.trim().split("\\s+");
		if (cards.length != 5) {
			throw new IllegalArgumentException("Рука должна содержать 5 карт");
		}

		this.cards = Arrays.stream(cards)
				.map(Card::new)
				.sorted(Comparator.comparingInt((Card c) -> c.rank).reversed())
				.collect(Collectors.toList());

		this.handRank = evaluateHandRank();
	}

	@Override
	public int compareTo(PokerHand o) {
		return this.handRank.compareTo(o.handRank);
	}

	@Override
	public String toString() {
		return cards.toString();
	}

	private HandRank evaluateHandRank() {
		boolean isFlush = isFlush();
		boolean isStraight = isStraight();

		Map<Integer, Long> rankFrequencies = cards.stream()
				.collect(Collectors.groupingBy(c -> c.rank, Collectors.counting()));

		List<Map.Entry<Integer, Long>> freqList = new ArrayList<>(rankFrequencies.entrySet());
		freqList.sort((a, b) -> {
			int cmp = Long.compare(b.getValue(), a.getValue());

			if (cmp == 0) {
				return Integer.compare(b.getKey(), a.getKey());
			}

			return cmp;
		});

		if (isStraight && isFlush) {
			int highestRank = cards.get(0).rank;

			if (highestRank == 14 &&
					cards.get(1).rank == 13 &&
					cards.get(2).rank == 12 &&
					cards.get(3).rank == 11 &&
					cards.get(4).rank == 10) {
				return new HandRank(9, Collections.emptyList());
			}

			return new HandRank(8, Collections.singletonList(highestRank));
		}

		if (freqList.get(0).getValue() == 4) {
			return new HandRank(7, Arrays.asList(freqList.get(0).getKey(), freqList.get(1).getKey()));
		}

		if (freqList.get(0).getValue() == 3 && freqList.get(1).getValue() == 2) {
			return new HandRank(6, Arrays.asList(freqList.get(0).getKey(), freqList.get(1).getKey()));
		}

		if (isFlush) {
			List<Integer> ranks = cards.stream().map(c -> c.rank).collect(Collectors.toList());
			return new HandRank(5, ranks);
		}

		if (isStraight) {
			int highestRank = cards.getFirst().rank;
			return new HandRank(4, Collections.singletonList(highestRank));
		}

		if (freqList.get(0).getValue() == 3) {
			List<Integer> kickers = freqList.subList(1, freqList.size()).stream()
					.map(Map.Entry::getKey)
					.collect(Collectors.toList());
			return new HandRank(3, concatLists(Collections.singletonList(freqList.getFirst().getKey()),
					kickers));
		}

		if (freqList.get(0).getValue() == 2 && freqList.get(1).getValue() == 2) {
			return new HandRank(2, Arrays.asList(
					freqList.get(0).getKey(),
					freqList.get(1).getKey(),
					freqList.get(2).getKey()));
		}

		if (freqList.get(0).getValue() == 2) {
			List<Integer> kickers = freqList.subList(1, freqList.size()).stream()
					.map(Map.Entry::getKey)
					.collect(Collectors.toList());
			return new HandRank(1, concatLists(Collections.singletonList(freqList.getFirst().getKey()),
					kickers));
		}

		List<Integer> allRanks = freqList.stream().map(Map.Entry::getKey).collect(Collectors.toList());
		return new HandRank(0, allRanks);
	}

	private boolean isFlush() {
		int suit = cards.getFirst().suit;
		for (Card c : cards) {
			if (c.suit != suit) {
				return false;
			}
		}
		return true;
	}

	private boolean isStraight() {
		for (int i = 0; i < 4; i++) {
			if (cards.get(i).rank - cards.get(i + 1).rank != 1) {
				return false;
			}
		}
		return true;
	}

	private static List<Integer> concatLists(List<Integer> first, List<Integer> second) {
		List<Integer> result = new ArrayList<>(first);
		result.addAll(second);
		return result;
	}
}
