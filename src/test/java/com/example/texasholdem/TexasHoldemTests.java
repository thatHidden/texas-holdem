package com.example.texasholdem;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TexasHoldemTests {

	@Test
	public void testHighCardComparison() {
		PokerHand h1 = new PokerHand("2C 4D 6H 8S TC");
		PokerHand h2 = new PokerHand("2D 4H 6C 8H 9S");
		assertTrue(h1.compareTo(h2) < 0);
	}

	@Test
	public void testPairs() {
		PokerHand pairA = new PokerHand("AH AS 5C 7D 9H");
		PokerHand pairK = new PokerHand("KH KS 5D 7C 9D");
		assertTrue(pairA.compareTo(pairK) < 0);
	}

	@Test
	public void testSameTypeFullHouse() {
		PokerHand fh1 = new PokerHand("3H 3D 3C 2H 2C");
		PokerHand fh2 = new PokerHand("4H 4D 4C 2D 2S");
		assertTrue(fh1.compareTo(fh2) > 0);
	}

	@Test
	public void testRoyalFlushVsStraightFlush() {
		PokerHand rf = new PokerHand("AS KS QS JS TS");
		PokerHand sf = new PokerHand("9S 8S 7S 6S 5S");
		assertTrue(rf.compareTo(sf) < 0);
	}

	@Test
	public void testSorting() {
		PokerHand h1 = new PokerHand("KS 2H 5C JD TD");
		PokerHand h2 = new PokerHand("AS KS QS JS TS");
		PokerHand h3 = new PokerHand("2C 3C AC 4C 5C");
		List<PokerHand> hands = Arrays.asList(h1, h2, h3);
		Collections.sort(hands);
		assertEquals(h2, hands.getFirst());
	}

}
