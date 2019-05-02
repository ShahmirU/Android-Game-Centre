package fall2018.csc207.GameCentre;

import org.junit.Test;

import java.util.ArrayList;

import fall2018.csc207.GameCentre.slidingtiles.SlidingTilesScoreBoardController;

import static junit.framework.Assert.assertTrue;

public class SlidingTilesScoreBoardTest {

    ArrayList<Player> setUpPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("Bob", "1234", "Bob");
        p1.setHighScore("Sliding Tiles 3x3", 5);
        Player p2 = new Player("Harry", "12", "Harry");
        p2.setHighScore("Sliding Tiles 3x3", 10);

        players.add(p1);
        players.add(p2);

        return players;
    }

    @Test
    public void testSetPlayer() {
        ArrayList<Player> loadedPlayers = setUpPlayers();
        SlidingTilesScoreBoardController slidingTilesScoreBoardController = new SlidingTilesScoreBoardController();

        Player player = new Player("Bob", "12", "Bob");
        player.setHighScore("Sliding Tiles 3x3", 5);
        Player p = slidingTilesScoreBoardController.setPlayer(player, 5, "Bob", "Sliding Tiles 3x3", loadedPlayers);

        assertTrue(p.getUsername().equals("Harry") && p.getHighScore("Sliding Tiles 3x3") == 10);
    }
}
