package core;

import static org.junit.Assert.assertNotEquals;

import core.Player.State;
import junit.framework.TestCase;

public class GameTest extends TestCase {
    private Game game;

    public GameTest() {
        game = new Game();
    }

    public void testBlackjackPlayer() {
        String path = getClass().getResource("/blackjack_player.txt").getFile();
        game.playWithFile(path);
        assertEquals(game.getPlayer().getState(), State.WON);
        assertNotEquals(game.getDealer().getState(), State.WON);
    }

    public void testBlackjackDealer() {
        String path = getClass().getResource("/blackjack_dealer.txt").getFile();
        game.playWithFile(path);
        assertEquals(game.getDealer().getState(), State.WON);
        assertNotEquals(game.getPlayer().getState(), State.WON);
    }

    public void testPlayerHitting() {
        String path = getClass().getResource("/player_hitting.txt").getFile();
        game.playWithFile(path);
        assertEquals(game.getPlayer().getState(), State.BUSTED);
    }

    public void testPlayerStanding() {
        String path = getClass().getResource("/player_standing.txt").getFile();
        game.playWithFile(path);
        assertEquals(game.getPlayer().getState(), State.WON);
        assertEquals(game.getDealer().getState(), State.STANDING);
    }

    public void testDealerHitting() {
        String path = getClass().getResource("/dealer_hitting.txt").getFile();
        game.playWithFile(path);
        assertEquals(game.getDealer().getState(), State.BUSTED);
    }

    public void testDealerHittingSoft17() {
        String path = getClass().getResource("/dealer_hitting_soft17.txt").getFile();
        game.playWithFile(path);
        assertEquals(game.getDealer().getState(), State.WON);
        assertEquals(game.getPlayer().getState(), State.STANDING);
    }
}
