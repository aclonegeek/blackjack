package core;

import static org.junit.Assert.assertNotEquals;

import core.Player.State;
import junit.framework.TestCase;

public class DealerTest extends TestCase {
    private Game game;

    public DealerTest() {
        game = new Game();
    }

    public void testSplit() {
        String path = getClass().getResource("/dealer_splits_and_player_wins.txt").getFile();
        game.playWithFile(path);
        assertEquals(game.getPlayer().getState(), State.WON);
        assertNotEquals(game.getDealer().getState(), State.WON);
    }
}
