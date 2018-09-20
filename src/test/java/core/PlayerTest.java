package core;

import java.util.Arrays;

import core.Player.State;
import junit.framework.TestCase;

public class PlayerTest extends TestCase {
    public void testGetBestHandTotal() {
        Card hearts3 = new Card('H', "3");
        Card clubs3 = new Card('C', "3");
        Card spadesJack = new Card('S', "J");
        Card diamondsAce = new Card('D', "A");

        Deck deck = new Deck();
        deck.create();

        // Test for one hand.
        Player player = new Player("Player");
        player.createHand();
        player.setCurrentHand(player.getHand(0));
        player.hit(diamondsAce);
        player.hit(spadesJack);
        assertEquals(player.getBestHandTotal(), 21);

        // Test for two hands.
        Player player2 = new Player("Player");
        player2.createHand();
        player2.setCurrentHand(player2.getHand(0));
        player2.hit(hearts3);
        player2.hit(clubs3);
        player2.split(deck);
        assertEquals(player2.getBestHandTotal(), 14);
    }

    public void testSwitchHand() {
        Card hearts3 = new Card('H', "3");
        Card clubs3 = new Card('C', "3");

        Deck deck = new Deck();
        deck.create();

        Player player = new Player("Player");
        player.createHand();
        player.setCurrentHand(player.getHand(0));

        player.hit(hearts3);
        player.hit(clubs3);
        player.split(deck);

        
    }

    public void testCheckHitResult() {
        Card hearts3 = new Card('H', "3");
        Card clubs7 = new Card('C', "7");
        Card spadesJack = new Card('S', "J");
        Card spadesQueen = new Card('S', "Q");
        Card diamondsAce = new Card('D', "A");

        Player player = new Player("Player");
        player.createHand();
        player.setCurrentHand(player.getHand(0));

        player.hit(diamondsAce);
        assertEquals(player.getHand(0).getHand(), Arrays.asList(diamondsAce));
        assertEquals(player.getHand(0).getTotal(), 11);
        assertEquals(player.getState(), State.PLAYING);

        player.hit(hearts3);
        assertEquals(player.getHand(0).getHand(), Arrays.asList(diamondsAce, hearts3));
        assertEquals(player.getHand(0).getTotal(), 14);
        assertEquals(player.getState(), State.PLAYING);

        player.hit(spadesJack);
        assertEquals(player.getHand(0).getHand(), Arrays.asList(diamondsAce, hearts3, spadesJack));
        assertEquals(player.getHand(0).getTotal(), 14);
        assertEquals(player.getState(), State.PLAYING);

        player.hit(clubs7);
        assertEquals(player.getHand(0).getHand(), Arrays.asList(diamondsAce, hearts3, spadesJack, clubs7));
        assertEquals(player.getHand(0).getTotal(), 21);
        assertEquals(player.getState(), State.WON);

        player.hit(spadesQueen);
        assertEquals(player.getHand(0).getHand(), Arrays.asList(diamondsAce, hearts3, spadesJack, clubs7, spadesQueen));
        assertEquals(player.getHand(0).getTotal(), 31);
        assertEquals(player.getState(), State.BUSTED);
    }

    public void testSplit() {
        Card hearts3 = new Card('H', "3");
        Card clubs3 = new Card('C', "3");

        Deck deck = new Deck();
        deck.create();

        Player player = new Player("Player");
        player.createHand();
        player.setCurrentHand(player.getHand(0));
        assertEquals(player.getHands().size(), 1);
        assertTrue(player.getHand(0).getHand().isEmpty());
        assertEquals(player.hasSecondHand(), false);

        player.hit(hearts3);
        player.hit(clubs3);
        assertEquals(player.getHand(0).getHand(), Arrays.asList(hearts3, clubs3));

        player.split(deck);
        assertEquals(player.getHands().size(), 2);
        assertEquals(player.hasSecondHand(), true);
        assertEquals(player.getHand(0).getHand(), Arrays.asList(hearts3, new Card('S', "A")));
        assertEquals(player.getHand(1).getHand(), Arrays.asList(clubs3));
    }

    public void testNoSplit() {
        Card hearts3 = new Card('H', "3");
        Card clubs4 = new Card('C', "4");

        Deck deck = new Deck();
        deck.create();

        // Test no splitting due to differing cards.
        Player player = new Player("Player");
        player.createHand();
        player.setCurrentHand(player.getHand(0));
        assertEquals(player.getHands().size(), 1);
        assertTrue(player.getHand(0).getHand().isEmpty());
        assertEquals(player.hasSecondHand(), false);

        player.hit(hearts3);
        player.hit(clubs4);
        assertEquals(player.getHand(0).getHand(), Arrays.asList(hearts3, clubs4));

        player.split(deck);
        assertEquals(player.getHands().size(), 1);
        assertEquals(player.hasSecondHand(), false);
        assertEquals(player.getHand(0).getHand(), Arrays.asList(hearts3, clubs4));
    }
}
