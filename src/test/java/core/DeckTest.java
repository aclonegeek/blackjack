package core;

import static org.junit.Assert.assertNotEquals;

import junit.framework.TestCase;

public class DeckTest extends TestCase {
	public void testCreate() {
		Deck deck = new Deck();

		assertTrue(deck.getDeck().isEmpty());
		deck.create();
		assertTrue(!deck.getDeck().isEmpty());
	}
	
	public void testShuffle() {
		Deck deck = new Deck();
		Deck shuffledDeck = new Deck();

		shuffledDeck.shuffle();
		assertNotEquals(deck, shuffledDeck);
	}
}
