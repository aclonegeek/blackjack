package core;

import junit.framework.TestCase;

public class HandTest extends TestCase {
	public void testGetTotal() {
		Card hearts3 = new Card('H', "3");
		Card clubs9 = new Card('C', "9");
		Card spadesJack = new Card('S', "J");
		Card diamondsAce = new Card('D', "A");

		Hand hand = new Hand();
		// Test number branch.
		hand.addCard(hearts3);
		assertEquals(hand.getTotal(), 3);
		// Test characters (J, Q, K) branch.
		// The value is 13 because J (10) + the 3 from above.
		hand.addCard(spadesJack);
		assertEquals(hand.getTotal(), 13);
		// Test ace being set to 1 so we don't bust.
		hand.addCard(diamondsAce);
		assertEquals(hand.getTotal(), 14);

		Hand hand2 = new Hand();
		// Test ace being set to 11.
		hand2.addCard(diamondsAce);
		assertEquals(hand2.getTotal(), 11);
		hand2.addCard(hearts3);
		// Test having an ace at 11 and an ace at 1.
		hand2.addCard(diamondsAce);
		assertEquals(hand2.getTotal(), 15);

		// Test 2 aces counting as 1.
		Hand hand3 = new Hand();
		hand3.addCard(hearts3);
		hand3.addCard(clubs9);
		hand3.addCard(diamondsAce);
		hand3.addCard(diamondsAce);
		assertEquals(hand3.getTotal(), 14);
	}
}
