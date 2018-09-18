package core;

import static org.junit.Assert.assertNotEquals;

import junit.framework.TestCase;

public class CardTest extends TestCase {
	public void testEquals() {
		Card c1 = new Card('S', "10");
		Card c2 = new Card('S', "10");
		Card c3 = new Card('D', "J");

		assertEquals(c1, c1);
		assertEquals(c1, c2);
		assertNotEquals(c1, c3);
	}
}
