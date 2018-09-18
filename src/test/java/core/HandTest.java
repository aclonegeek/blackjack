package core;

import junit.framework.TestCase;

public class HandTest extends TestCase {
	public void testGetTotal() {
		Card hearts3 = new Card('H', "3");
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
	}
}
