package core;

import static org.junit.Assert.assertNotEquals;

import java.util.Objects;

import junit.framework.TestCase;

public class DeckTest extends TestCase {
	public void testCreate() {
		Deck deck = new Deck();

		assertTrue(deck.getDeck().isEmpty());
		deck.create();
		assertFalse(deck.getDeck().isEmpty());
		assertEquals(deck.getDeck().size(), 52);
	}

	public void testShuffle() {
		Deck deck = new Deck();
		Deck shuffledDeck = new Deck();
		deck.create();
		shuffledDeck.create();

		assertEquals(deck.getDeck(), shuffledDeck.getDeck());
		// Protect against the case of shuffling the exact same deck by shuffling
		// until we get a different list.
		while (Objects.equals(deck.getDeck(), shuffledDeck.getDeck())) {
			shuffledDeck.shuffle();
		}
		assertNotEquals(deck, shuffledDeck);
	}
}
